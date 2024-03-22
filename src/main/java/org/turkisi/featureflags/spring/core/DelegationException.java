package org.turkisi.featureflags.spring.core;

import java.text.MessageFormat;

public class DelegationException extends RuntimeException {

    private final String featureName;
    private final double rollResult;

    public DelegationException(String featureName, double rollResult) {
        super(MessageFormat.format("Cannot find a suitable delegate for feature [{0}] and get [{1}]",
                featureName, rollResult));
        this.featureName = featureName;
        this.rollResult = rollResult;
    }

    public String getFeatureName() {
        return featureName;
    }

    public double getRollResult() {
        return rollResult;
    }
}
