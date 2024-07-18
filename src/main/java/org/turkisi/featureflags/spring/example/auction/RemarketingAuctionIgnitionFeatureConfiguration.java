package org.turkisi.featureflags.spring.example.auction;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.core.experiment.ExperimentConfiguration;
import org.turkisi.featureflags.spring.core.experiment.ExperimentedFeatureConfiguration;

@Component
@ConfigurationProperties(prefix = "org.turkisi.features.auction")
public class RemarketingAuctionIgnitionFeatureConfiguration implements ExperimentedFeatureConfiguration {

    private ExperimentConfiguration experiment;

    public ExperimentConfiguration getExperiment() {
        return experiment;
    }

    public void setExperiment(ExperimentConfiguration experiment) {
        this.experiment = experiment;
    }
}
