package org.turkisi.featureflags.spring.example.auction;

import org.turkisi.featureflags.spring.core.experiment.ExperimentedFeature;
import org.turkisi.featureflags.spring.example.auction.rerun.RemarketingAuctionRerunFeature;
import org.turkisi.featureflags.spring.example.domain.Auction;
import org.turkisi.featureflags.spring.example.domain.CarLead;
import org.turkisi.featureflags.spring.example.external.AuctionService;

@ExperimentedFeature
class DefaultRemarketingAuctionIgnitionFeature implements RemarketingAuctionIgnitionFeature {

    private final RemarketingAuctionRerunFeature auctionRerunFeature;

    private final AuctionService auctionService;

    protected DefaultRemarketingAuctionIgnitionFeature(RemarketingAuctionRerunFeature auctionRerunFeature,
                                                       AuctionService auctionService) {
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
