package org.turkisi.featureflags.spring.auction.rerun.limit;

import jakarta.annotation.Nonnull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.domain.CarLead;

@Component
@ConditionalOnProperty(value = "org.turkisi.features.auction.rerun.rerun-limit.delegate", havingValue = "default")
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
