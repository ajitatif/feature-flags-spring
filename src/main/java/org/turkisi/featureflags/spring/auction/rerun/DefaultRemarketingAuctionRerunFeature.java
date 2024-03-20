package org.turkisi.featureflags.spring.auction.rerun;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.auction.AuctionIgnitionFailException;
import org.turkisi.featureflags.spring.auction.rerun.limit.RemarketingAuctionRerunLimitFeature;
import org.turkisi.featureflags.spring.domain.Auction;
import org.turkisi.featureflags.spring.domain.CarLead;
import org.turkisi.featureflags.spring.external.AuctionService;

@Component
@ConditionalOnProperty(value = "org.turkisi.features.auction.rerun.delegate", havingValue = "default")
class DefaultRemarketingAuctionRerunFeature implements RemarketingAuctionRerunFeature {

    private final RemarketingAuctionRerunFeatureConfiguration configuration;
    private final RemarketingAuctionRerunLimitFeature remarketingAuctionRerunLimitFeature;

    private final AuctionService auctionService;

    public DefaultRemarketingAuctionRerunFeature(RemarketingAuctionRerunFeatureConfiguration configuration,
                                                 RemarketingAuctionRerunLimitFeature remarketingAuctionRerunLimitFeature,
                                                 AuctionService auctionService) {
        this.configuration = configuration;
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
