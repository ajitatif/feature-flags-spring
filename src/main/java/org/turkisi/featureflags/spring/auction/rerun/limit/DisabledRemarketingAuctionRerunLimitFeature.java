package org.turkisi.featureflags.spring.auction.rerun.limit;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.domain.CarLead;

@Component
class DisabledRemarketingAuctionRerunLimitFeature implements RemarketingAuctionRerunLimitFeature {
    @Override
    public void validateCar(@Nonnull CarLead carLead) {
        // intentionally empty because feature only limits the reruns
    }
}
