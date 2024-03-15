package org.turkisi.featureflags.spring.auction.rerun.limit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.common.FeatureConfiguration;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "org.turkisi.features.auction.rerun.rerun-limit")
public class RemarketingAuctionRerunLimitFeatureConfiguration implements FeatureConfiguration {

    private String delegate;
    private Map<String, Integer> limits;

    public Map<String, Integer> getLimits() {
        return limits;
    }

    public void setLimits(Map<String, Integer> limits) {
        this.limits = limits;
    }

    public String getDelegate() {
        return delegate;
    }

    public void setDelegate(String delegate) {
        this.delegate = delegate;
    }
}
