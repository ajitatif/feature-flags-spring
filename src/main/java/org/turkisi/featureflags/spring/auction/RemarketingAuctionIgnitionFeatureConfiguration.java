package org.turkisi.featureflags.spring.auction;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.common.FeatureConfiguration;

@Component
@ConfigurationProperties(prefix = "org.turkisi.features.auction")
public class RemarketingAuctionIgnitionFeatureConfiguration implements FeatureConfiguration {

    private String delegate;

    public String getDelegate() {
        return delegate;
    }

    public void setDelegate(String delegate) {
        this.delegate = delegate;
    }
}
