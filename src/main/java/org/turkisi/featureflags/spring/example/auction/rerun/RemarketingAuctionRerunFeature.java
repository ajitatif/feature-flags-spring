package org.turkisi.featureflags.spring.example.auction.rerun;

import org.turkisi.featureflags.spring.core.Feature;
import org.turkisi.featureflags.spring.example.auction.AuctionIgnitionFailException;
import org.turkisi.featureflags.spring.example.domain.Auction;
import org.turkisi.featureflags.spring.example.domain.CarLead;

public interface RemarketingAuctionRerunFeature extends Feature {

    /**
     * re-introduces a CarLead to an auction
     * @param car The car lead to put into auction
     * @param auctionTypeId Auction type to put the car into
     * @return Auction that is created - the auction may have started or not, depending on the auction configuration
     * @throws AuctionIgnitionFailException When the auction could not be started. See the message of the exception
     */
    Auction rerunCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException;
}
