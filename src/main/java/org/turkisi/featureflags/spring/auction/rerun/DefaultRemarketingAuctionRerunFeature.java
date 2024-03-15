package org.turkisi.featureflags.spring.auction.rerun;

import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.auction.AuctionIgnitionFailException;
import org.turkisi.featureflags.spring.auction.rerun.limit.RemarketingAuctionRerunLimitFeatureDelegate;
import org.turkisi.featureflags.spring.common.ConfigurableFeature;
import org.turkisi.featureflags.spring.common.FeatureConfiguration;
import org.turkisi.featureflags.spring.domain.Auction;
import org.turkisi.featureflags.spring.domain.CarLead;
import org.turkisi.featureflags.spring.external.AuctionService;

@Component
class DefaultRemarketingAuctionRerunFeature implements RemarketingAuctionRerunFeature, ConfigurableFeature {

    private final RemarketingAuctionRerunFeatureConfiguration configuration;
    private final RemarketingAuctionRerunLimitFeatureDelegate remarketingAuctionRerunLimitFeature;

    private final AuctionService auctionService;

    public DefaultRemarketingAuctionRerunFeature(RemarketingAuctionRerunFeatureConfiguration configuration,
                                                 RemarketingAuctionRerunLimitFeatureDelegate remarketingAuctionRerunLimitFeature,
                                                 AuctionService auctionService) {
        this.configuration = configuration;
        this.remarketingAuctionRerunLimitFeature = remarketingAuctionRerunLimitFeature;
        this.auctionService = auctionService;
    }

    @Override
    public FeatureConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public Auction rerunCar(CarLead carLead, int auctionTypeId) throws AuctionIgnitionFailException {
        remarketingAuctionRerunLimitFeature.validateCar(carLead);
        // TODO add +1 rerun count
        return auctionService.checkoutCar(carLead, auctionTypeId);
    }
}
