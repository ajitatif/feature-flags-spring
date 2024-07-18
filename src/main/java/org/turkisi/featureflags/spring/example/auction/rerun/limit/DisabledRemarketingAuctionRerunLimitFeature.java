package org.turkisi.featureflags.spring.example.auction.rerun.limit;

import jakarta.annotation.Nonnull;
import org.turkisi.featureflags.spring.core.experiment.ExperimentedFeature;
import org.turkisi.featureflags.spring.example.domain.CarLead;

@ExperimentedFeature
class DisabledRemarketingAuctionRerunLimitFeature implements RemarketingAuctionRerunLimitFeature {
    @Override
    public void validateCar(@Nonnull CarLead carLead) {
        // intentionally empty because feature only limits the reruns
    }
}
