package org.turkisi.featureflags.spring.core.experiment;

import java.io.Serializable;

public interface ExperimentedFeatureConfiguration extends Serializable {

    ExperimentConfiguration getExperiment();
}
