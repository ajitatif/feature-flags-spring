package org.turkisi.featureflags.spring.auction.rerun.limit;

import jakarta.annotation.Nonnull;
import org.turkisi.featureflags.spring.domain.CarLead;

public interface RemarketingAuctionRerunLimitFeature {

    void validateCar(@Nonnull CarLead carLead) throws CarAuctionRerunLimitExhaustedException;

}
