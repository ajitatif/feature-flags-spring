package org.turkisi.featureflags.spring.auction.rerun.limit;

import jakarta.annotation.Nonnull;
import org.turkisi.featureflags.spring.core.experiment.ExperimentedFeature;
import org.turkisi.featureflags.spring.domain.CarLead;

@ExperimentedFeature
class DisabledRemarketingAuctionRerunLimitFeature implements RemarketingAuctionRerunLimitFeature {
    @Override
    public void validateCar(@Nonnull CarLead carLead) {
        // intentionally empty because feature only limits the reruns
    }
}
