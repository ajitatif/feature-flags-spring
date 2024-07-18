package org.turkisi.featureflags.spring.example.auction.rerun;

import org.turkisi.featureflags.spring.core.experiment.ExperimentedFeature;
import org.turkisi.featureflags.spring.example.auction.AuctionIgnitionFailException;
import org.turkisi.featureflags.spring.example.auction.rerun.limit.RemarketingAuctionRerunLimitFeature;
import org.turkisi.featureflags.spring.example.domain.Auction;
import org.turkisi.featureflags.spring.example.domain.CarLead;
import org.turkisi.featureflags.spring.example.external.AuctionService;

@ExperimentedFeature
class DefaultRemarketingAuctionRerunFeature implements RemarketingAuctionRerunFeature {

    private final RemarketingAuctionRerunLimitFeature remarketingAuctionRerunLimitFeature;

    private final AuctionService auctionService;

    public DefaultRemarketingAuctionRerunFeature(RemarketingAuctionRerunLimitFeature remarketingAuctionRerunLimitFeature,
                                                 AuctionService auctionService) {
        this.remarketingAuctionRerunLimitFeature = remarketingAuctionRerunLimitFeature;
        this.auctionService = auctionService;
    }

    @Override
    public Auction rerunCar(CarLead carLead, int auctionTypeId) throws AuctionIgnitionFailException {
        remarketingAuctionRerunLimitFeature.validateCar(carLead);
        // TODO add +1 rerun count
        return auctionService.checkoutCar(carLead, auctionTypeId);
    }
}
