package org.turkisi.featureflags.spring.core.experiment;

import java.io.Serializable;

/**
 * A feature configuration which includes an {@link ExperimentConfiguration}. Each feature <b>must</b> have an
 * {@link ExperimentedFeatureConfiguration} to make use of A/B testing by an {@link ExperimentStrategyCheck}
 */
public interface ExperimentedFeatureConfiguration extends Serializable {

    ExperimentConfiguration getExperiment();
}
