package org.turkisi.featureflags.spring.core.rolls;

import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.core.experiment.ExperimentStrategyCheck;

@Component("flip-flop")
public class FlipFlopCheck implements ExperimentStrategyCheck {

    boolean flip = true;

    @Override
    public double get(Object... params) {
        final double d = flip ? 0 : 1;
        flip = !flip;
        return d;
    }
}
