package org.turkisi.featureflags.spring.example.auction.rerun.limit;

import jakarta.annotation.Nonnull;
import org.turkisi.featureflags.spring.core.experiment.ExperimentedFeature;
import org.turkisi.featureflags.spring.example.domain.CarLead;

@ExperimentedFeature
class DefaultRemarketingAuctionRerunLimitFeature implements RemarketingAuctionRerunLimitFeature {

    private final RemarketingAuctionRerunLimitFeatureConfiguration configuration;

    public DefaultRemarketingAuctionRerunLimitFeature(RemarketingAuctionRerunLimitFeatureConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void validateCar(@Nonnull CarLead carLead) throws CarAuctionRerunLimitExhaustedException {
        final Integer rerunLimit = configuration.getLimits().getOrDefault(carLead.country(), Integer.MIN_VALUE);
        if (carLead.rerunCount() >= rerunLimit) {
            throw new CarAuctionRerunLimitExhaustedException(carLead, rerunLimit);
        }
    }
}
