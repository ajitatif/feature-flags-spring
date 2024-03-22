package org.turkisi.featureflags.spring.auction;

import org.turkisi.featureflags.spring.core.experiment.ExperimentedFeature;
import org.turkisi.featureflags.spring.domain.Auction;
import org.turkisi.featureflags.spring.domain.CarLead;

@ExperimentedFeature
public class DisabledRemarketingAuctionIgnitionFeature implements RemarketingAuctionIgnitionFeature {
    @Override
    public Auction checkoutCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException{
        throw new AuctionIgnitionFailException("Cannot ignite the auction because feature is disabled in this configuration");
    }

    @Override
    public Auction rerunCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException {
        throw new AuctionIgnitionFailException("Cannot rerun the auction because feature is disabled in this configuration");
    }
}
