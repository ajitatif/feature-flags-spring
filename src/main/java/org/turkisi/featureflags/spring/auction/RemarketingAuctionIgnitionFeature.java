package org.turkisi.featureflags.spring.auction;

import org.turkisi.featureflags.spring.domain.Auction;
import org.turkisi.featureflags.spring.domain.CarLead;

public interface RemarketingAuctionIgnitionFeature {

    Auction checkoutCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException;
    Auction rerunCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException;
}
