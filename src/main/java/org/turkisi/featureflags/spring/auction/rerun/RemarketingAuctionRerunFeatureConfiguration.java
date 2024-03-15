package org.turkisi.featureflags.spring.auction.rerun;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.common.FeatureConfiguration;

import java.util.Optional;

@Component
@ConfigurationProperties(prefix = "org.turkisi.features.auction.rerun")
public class RemarketingAuctionRerunFeatureConfiguration implements FeatureConfiguration {

    private String delegate;

    public String getDelegate() {
        return delegate;
    }

    public void setDelegate(String delegate) {
        this.delegate = delegate;
    }
}
