package org.turkisi.featureflags.spring.auction.rerun;

import org.turkisi.featureflags.spring.auction.AuctionIgnitionFailException;
import org.turkisi.featureflags.spring.auction.rerun.limit.RemarketingAuctionRerunLimitFeature;
import org.turkisi.featureflags.spring.core.experiment.ExperimentedFeature;
import org.turkisi.featureflags.spring.domain.Auction;
import org.turkisi.featureflags.spring.domain.CarLead;
import org.turkisi.featureflags.spring.external.AuctionService;

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
