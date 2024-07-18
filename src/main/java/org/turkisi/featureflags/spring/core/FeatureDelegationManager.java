package org.turkisi.featureflags.spring.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.core.experiment.ExperimentStrategyCheck;
import org.turkisi.featureflags.spring.core.experiment.ExperimentVersion;
import org.turkisi.featureflags.spring.core.experiment.ExperimentedFeature;
import org.turkisi.featureflags.spring.core.experiment.ExperimentedFeatureConfiguration;

import java.util.*;
import java.util.function.Predicate;

/**
 * Component where the magic of delegation -> implementation happens. Each feature delegate (a class extending
 * {@link FeatureDelegateBase} class) gets help from this delegate manager to pick the feature to run, according to the
 * configuration defined in {@link org.turkisi.featureflags.spring.core.experiment.ExperimentConfiguration}
 * <p>The implementation to run is determined using {@link ExperimentStrategyCheck}, and {@link ExperimentVersion}
 * defined in the same configuration. FeatureDelegateManager is responsible for getting the check result from
 * {@link ExperimentStrategyCheck} and pick the correct {@link Feature} implementation based on the range defined in the
 * configuration.
 * <p>The feature implementation to be picked is the first one within the calculated check range. e.g. for ranges</p>
 * <pre>
 *     0.0 -> Feature Implementation A
 *     0.5 -> Feature Implementation B
 * </pre>
 * <p>A check of 0.49 will result in running {@code Feature Implementation A}, whereas a check of 0.5 will result in
 * running {@code Feature Implementation B}</p>
 * <p>FeatureDelegateManager is an {@link ApplicationContextAware} Spring bean and relies on reflection to detect the
 * feature implementations. In order for a feature implementation to be picked up, it has to fulfill these conditions:
 * <ul>
 *  <li>Implement an interface which extends {@link Feature}</li>
 *  <li>Marked {@link ExperimentedFeature} on the class-level</li>
 *  <li>Configured range within the application configuration</li>
 * </ul>
 * </p>
 */
@Component
public class FeatureDelegationManager implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(FeatureDelegationManager.class);

    private final Map<String, ExperimentStrategyCheck> strategyChecks;

    // Map<FeatureInterfaceClass, Map<DelegateName, FeatureImplementation>>
    private final Map<Class<?>, Map<String, Object>> featureMapping = new HashMap<>();
    private ApplicationContext applicationContext;
    
    private final FeatureMetricsRepository metricsRepository;

    public FeatureDelegationManager(Map<String, ExperimentStrategyCheck> strategyChecks, FeatureMetricsRepository metricsRepository) {
        this.strategyChecks = strategyChecks;
        this.metricsRepository = metricsRepository;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Rolls the strategy check, and determines which implementation should run. Please refer to the class documentation
     * on how the choice is made
     * @param configuration The experimented feature configuration which contains strategy and ranges
     * @param featureBase The base interface of the feature to run. Required because {@code FeatureDelegateManager}
     *                    builds a {@code Map<String, ExperimentStrategyCheck>} to look up the implementations of the
     *                    same feature
     * @param params The parameters that {@code featureBase} requires to operate - These may or may not be used by the
     *               strategy to decide
     * @return The delegate picked according to {@code params} and {@code configuration} passed on
     * @param <T> The feature base interface.
     * @throws DelegationException When the roll did not result in picking any implementation
     */
    @SuppressWarnings("unchecked")
    public <T> T getDelegateToRun(ExperimentedFeatureConfiguration configuration, Class<T> featureBase, Object... params) {
        if (featureMapping.isEmpty()) {
            logger.debug("FeatureMapping is empty, populating");
            buildFeatureMapping();
            logger.debug("FeatureMapping initialized");
        }
        final RollResult result = getRollResult(configuration, params);
        final Optional<ExperimentVersion> version = result.version();
        if (version.isPresent()) {
            final Object delegate = featureMapping.get(featureBase).get(version.get().delegate());
            incrementDelegateUsage(delegate);
            return (T) delegate;
        } else {
            throw new DelegationException(featureBase.getName(), result.roll());
        }
    }

    private RollResult getRollResult(ExperimentedFeatureConfiguration configuration, Object... params) {
        final ExperimentStrategyCheck strategyCheck = strategyChecks.get(configuration.getExperiment().strategy());
        final double check = strategyCheck.get(params);
        final Optional<ExperimentVersion> version = configuration.getExperiment().versions()
                .stream()
                .sorted(Comparator.comparing(ExperimentVersion::threshold))
                .filter(experimentVersion -> Double.compare(experimentVersion.threshold(), check) >= 0)
                .findFirst();
        return new RollResult(check, version);
    }

    private void buildFeatureMapping() {
        final Map<String, Object> experimentedFeatures = applicationContext.getBeansWithAnnotation(ExperimentedFeature.class);
        for (Map.Entry<String, Object> featureImplementationEntry : experimentedFeatures.entrySet()) {
            logger.debug("Configuring feature bean [{}]", featureImplementationEntry.getKey());
            final Optional<Class<?>> featureInterface = Arrays.stream(
                            featureImplementationEntry.getValue().getClass().getInterfaces())
                    .filter(clazz -> Arrays.stream(clazz.getInterfaces()).anyMatch(Predicate.isEqual(Feature.class)))
                    .findAny();
            if (featureInterface.isPresent()) {
                logger.debug("Found feature interface [{}]", featureInterface.get().getName());
                Map<String, Object> implementationMap = featureMapping.getOrDefault(featureInterface.get(), new HashMap<>());
                implementationMap.put(featureImplementationEntry.getKey(), featureImplementationEntry.getValue());
                featureMapping.putIfAbsent(featureInterface.get(), implementationMap);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Feature mapping: {}", featureMapping);
        }
    }

    private void incrementDelegateUsage(Object delegate) {
        final String className = delegate.getClass().getSimpleName();
        String featureName = className.substring(className.lastIndexOf('.') + 1);
        metricsRepository.incrementFeatureHits(featureName);
    }

    private record RollResult(double roll, Optional<ExperimentVersion> version) {
    }

}
