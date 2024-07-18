package org.turkisi.featureflags.spring.example.auction.rerun.limit;

import jakarta.annotation.Nonnull;
import org.turkisi.featureflags.spring.core.Feature;
import org.turkisi.featureflags.spring.example.domain.CarLead;

public interface RemarketingAuctionRerunLimitFeature extends Feature {

    void validateCar(@Nonnull CarLead carLead) throws CarAuctionRerunLimitExhaustedException;

}
