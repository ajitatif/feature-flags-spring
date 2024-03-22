package org.turkisi.featureflags.spring.core.experiment;

public interface ExperimentStrategyCheck {

    /**
     * Calculate the check
     * @param params The parameters to take into consideration
     * @return any value in [0, 1) range
     */
    double get(Object... params);
}
