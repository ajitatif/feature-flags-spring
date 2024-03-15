package org.turkisi.featureflags.spring.auction.rerun.limit;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.common.ConfigurableFeature;
import org.turkisi.featureflags.spring.common.FeatureConfiguration;
import org.turkisi.featureflags.spring.domain.CarLead;

@Component("defaultAuctionRerunLimitFeature")
class DefaultRemarketingAuctionRerunLimitFeature implements RemarketingAuctionRerunLimitFeature, ConfigurableFeature {

    private final RemarketingAuctionRerunLimitFeatureConfiguration configuration;

    public DefaultRemarketingAuctionRerunLimitFeature(RemarketingAuctionRerunLimitFeatureConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public FeatureConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public void validateCar(@Nonnull CarLead carLead) throws CarAuctionRerunLimitExhaustedException {
        final Integer rerunLimit = configuration.getLimits().getOrDefault(carLead.country(), Integer.MIN_VALUE);
        if (carLead.rerunCount() >= rerunLimit) {
            throw new CarAuctionRerunLimitExhaustedException(carLead, rerunLimit);
        }
    }
}
