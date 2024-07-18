package org.turkisi.featureflags.spring.example.auction;

import org.turkisi.featureflags.spring.core.Feature;
import org.turkisi.featureflags.spring.example.domain.Auction;
import org.turkisi.featureflags.spring.example.domain.CarLead;

public interface RemarketingAuctionIgnitionFeature extends Feature {

    Auction checkoutCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException;
    Auction rerunCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException;
}
