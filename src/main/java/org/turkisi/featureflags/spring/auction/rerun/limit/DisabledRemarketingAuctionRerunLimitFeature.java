package org.turkisi.featureflags.spring.auction.rerun.limit;

import jakarta.annotation.Nonnull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.domain.CarLead;

@Component
@ConditionalOnProperty(value = "org.turkisi.features.auction.rerun.rerun-limit.delegate", havingValue = "disabled")
class DisabledRemarketingAuctionRerunLimitFeature implements RemarketingAuctionRerunLimitFeature {
    @Override
    public void validateCar(@Nonnull CarLead carLead) {
        // intentionally empty because feature only limits the reruns
    }
}
