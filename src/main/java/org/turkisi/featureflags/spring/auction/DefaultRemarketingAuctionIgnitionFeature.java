package org.turkisi.featureflags.spring.auction;

import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.auction.rerun.RemarketingAuctionRerunFeatureDelegate;
import org.turkisi.featureflags.spring.common.ConfigurableFeature;
import org.turkisi.featureflags.spring.common.FeatureConfiguration;
import org.turkisi.featureflags.spring.domain.Auction;
import org.turkisi.featureflags.spring.domain.CarLead;
import org.turkisi.featureflags.spring.external.AuctionService;

@Component
class DefaultRemarketingAuctionIgnitionFeature implements RemarketingAuctionIgnitionFeature, ConfigurableFeature {

    private final RemarketingAuctionIgnitionFeatureConfiguration configuration;

    private final RemarketingAuctionRerunFeatureDelegate auctionRerunFeatureDelegate;

    private final AuctionService auctionService;

    protected DefaultRemarketingAuctionIgnitionFeature(RemarketingAuctionIgnitionFeatureConfiguration configuration,
                                                       RemarketingAuctionRerunFeatureDelegate auctionRerunFeatureDelegate,
                                                       AuctionService auctionService) {
        this.configuration = configuration;
        this.auctionRerunFeatureDelegate = auctionRerunFeatureDelegate;
        this.auctionService = auctionService;
    }

    @Override
    public FeatureConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public Auction checkoutCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException {
        validateCar(car);
        return auctionService.checkoutCar(car, auctionTypeId);
    }

    private void validateCar(CarLead car) throws AuctionIgnitionFailException{
        if (car.rerunCount() > 0) {
            throw new AuctionIgnitionFailException("The car has already been in auction. Rerun if you want to checkout this car");
        }
    }

    @Override
    public Auction rerunCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException {
        return auctionRerunFeatureDelegate.rerunCar(car, auctionTypeId);
    }
}
