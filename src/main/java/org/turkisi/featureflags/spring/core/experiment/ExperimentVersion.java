package org.turkisi.featureflags.spring.core.experiment;

/**
 * A version is a match of minimum check for a feature implementation, and the name of that feature implementation.
 * @param threshold The threshold to run this feature implementation (the minimum check required)
 * @param delegate The bean name of the {@link org.turkisi.featureflags.spring.core.Feature} implementation
 */
public record ExperimentVersion(double threshold, String delegate) {
}
