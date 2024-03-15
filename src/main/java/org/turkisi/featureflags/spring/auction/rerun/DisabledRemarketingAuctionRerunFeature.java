package org.turkisi.featureflags.spring.auction.rerun;

import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.auction.AuctionIgnitionFailException;
import org.turkisi.featureflags.spring.domain.Auction;
import org.turkisi.featureflags.spring.domain.CarLead;

@Component
class DisabledRemarketingAuctionRerunFeature implements RemarketingAuctionRerunFeature {
    @Override
    public Auction rerunCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException {
        throw new AuctionIgnitionFailException("Cannot rerun the car. Auction reruns are disabled for this configuration");
    }
}
