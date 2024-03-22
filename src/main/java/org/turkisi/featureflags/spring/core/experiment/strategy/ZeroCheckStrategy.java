package org.turkisi.featureflags.spring.core.experiment.strategy;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.core.experiment.ExperimentStrategyCheck;

/**
 * A check strategy that always returns 0. Useful for feature with single experiments
 * This is the default strategy check in autowiring
 */
@Component("zero-check")
@Primary
public class ZeroCheckStrategy implements ExperimentStrategyCheck {
    @Override
    public double get(Object... params) {
        return 0;
    }
}
