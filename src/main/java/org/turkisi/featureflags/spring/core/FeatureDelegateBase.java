package org.turkisi.featureflags.spring.core;

import org.turkisi.featureflags.spring.core.experiment.ExperimentedFeatureConfiguration;

/**
 * Provides the base class for feature delegate classes.
 * <p>The feature delegates are responsible for relaying the feature call to the correct {@link Feature} implementation.
 * In order to achieve this, a delegation class can make use of {@link org.springframework.context.annotation.Primary}
 * annotation so that whenever a {@link Feature} implementation is autowired, the default will be the delegate. Generally,
 * a delegate calls {@link FeatureDelegationManager#getDelegateToRun(ExperimentedFeatureConfiguration, Class, Object...)}}
 * such as:</p>
 * <pre>{@code
 *     @Override
 *     public <returnType> implementedFeatureMethod(int param1, Object param2) {
 *         return getDelegateToRun(param1, param2).implementedFeatureMethod(param1, param2);
 *     }
 * }</pre>
 * @param <T> The interface class which this delegate is handling. Every feature delegate has to implement
 *           the interface class they pass here
 */
public abstract class FeatureDelegateBase<T extends Feature> {

    private final Class<T> featureBaseClazz;
    private final ExperimentedFeatureConfiguration featureConfiguration;
    private final FeatureDelegationManager featureDelegationManager;

    /**
     * Required constructor for any feature delegate
     * @param featureBaseClazz The class of base interface which this delegate is handling
     * @param featureConfiguration The feature configuration including experiment and strategy
     * @param featureDelegationManager The delegate manager
     */
    protected FeatureDelegateBase(Class<T> featureBaseClazz, ExperimentedFeatureConfiguration featureConfiguration,
                                  FeatureDelegationManager featureDelegationManager) {
        this.featureBaseClazz = featureBaseClazz;
        this.featureConfiguration = featureConfiguration;
        this.featureDelegationManager = featureDelegationManager;
    }

    /**
     * Returns the delegate to run from the FeatureDelegateManager state of the instance
     * @param params The parameters which the feature method is being called. The underlying
     * {@link FeatureDelegationManager#getDelegateToRun(ExperimentedFeatureConfiguration, Class, Object...)} call
     *               might use any of the parameters passed
     * @return The {@link Feature} instance to run according to the {@link ExperimentedFeatureConfiguration}
     * specified in the configuration
     */
    protected T getDelegateToRun(Object... params) {
        return featureDelegationManager.getDelegateToRun(featureConfiguration, featureBaseClazz, params);
    }
}
