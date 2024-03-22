package org.turkisi.featureflags.spring.auction.rerun.limit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.core.experiment.ExperimentConfiguration;
import org.turkisi.featureflags.spring.core.experiment.ExperimentedFeatureConfiguration;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "org.turkisi.features.auction.rerun.rerun-limit")
public class RemarketingAuctionRerunLimitFeatureConfiguration implements ExperimentedFeatureConfiguration {

    private ExperimentConfiguration experiment;

    private Map<String, Integer> limits;

    public Map<String, Integer> getLimits() {
        return limits;
    }

    public void setLimits(Map<String, Integer> limits) {
        this.limits = limits;
    }

    public ExperimentConfiguration getExperiment() {
        return experiment;
    }

    public void setExperiment(ExperimentConfiguration experiment) {
        this.experiment = experiment;
    }
}
