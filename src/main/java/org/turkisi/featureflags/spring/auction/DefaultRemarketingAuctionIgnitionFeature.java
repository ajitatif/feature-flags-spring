package org.turkisi.featureflags.spring.auction;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.auction.rerun.RemarketingAuctionRerunFeature;
import org.turkisi.featureflags.spring.domain.Auction;
import org.turkisi.featureflags.spring.domain.CarLead;
import org.turkisi.featureflags.spring.external.AuctionService;

@Component
@ConditionalOnProperty(value = "org.turkisi.features.auction.delegate", havingValue = "default")
class DefaultRemarketingAuctionIgnitionFeature implements RemarketingAuctionIgnitionFeature {

    private final RemarketingAuctionIgnitionFeatureConfiguration configuration;

    private final RemarketingAuctionRerunFeature auctionRerunFeature;

    private final AuctionService auctionService;

    protected DefaultRemarketingAuctionIgnitionFeature(RemarketingAuctionIgnitionFeatureConfiguration configuration,
                                                       RemarketingAuctionRerunFeature auctionRerunFeature,
                                                       AuctionService auctionService) {
        this.configuration = configuration;
        this.auctionRerunFeature = auctionRerunFeature;
        this.auctionService = auctionService;
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
        return auctionRerunFeature.rerunCar(car, auctionTypeId);
    }
}
