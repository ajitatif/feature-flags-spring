package org.turkisi.featureflags.spring.core.experiment;

import java.io.Serializable;
import java.util.List;

public record ExperimentConfiguration(String strategy, List<ExperimentVersion> versions) implements Serializable {
}
