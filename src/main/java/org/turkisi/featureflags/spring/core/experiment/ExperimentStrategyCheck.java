package org.turkisi.featureflags.spring.core.experiment;

/**
 * Base interface to implement an A/B testing. The general idea of an A/B test is returning a number within [0, 1) range
 * based on parameters passed on. The check implementation may use all, any or none of the passed parameters.
 * Please refer to the class documentation of {@link org.turkisi.featureflags.spring.core.FeatureDelegationManager} on how the choice
 * is made among different implementations of the same feature.
 * <p>Implementors of this interface are usually Spring {@code @Component}s</p>
 */
public interface ExperimentStrategyCheck {

    /**
     * Calculate the check
     * @param params The parameters to take into consideration
     * @return any value in [0, 1) range
     */
    double get(Object... params);
}
