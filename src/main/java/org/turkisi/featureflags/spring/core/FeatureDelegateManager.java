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

@Component
public class FeatureDelegateManager implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(FeatureDelegateManager.class);

    private final Map<String, ExperimentStrategyCheck> strategyChecks;

    // Map<FeatureInterfaceClass, Map<DelegateName, FeatureImplementation>>
    private final Map<Class<?>, Map<String, Object>> featureMapping = new HashMap<>();
    private ApplicationContext applicationContext;
    
    private final FeatureMetricsRepository metricsRepository;

    public FeatureDelegateManager(Map<String, ExperimentStrategyCheck> strategyChecks, FeatureMetricsRepository metricsRepository) {
        this.strategyChecks = strategyChecks;
        this.metricsRepository = metricsRepository;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

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
