package org.turkisi.featureflags.spring.auction;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.core.FeatureDelegateManager;
import org.turkisi.featureflags.spring.domain.Auction;
import org.turkisi.featureflags.spring.domain.CarLead;

@Component
@Primary
public class RemarketingAuctionIgnitionDelegate implements RemarketingAuctionIgnitionFeature {

    private final RemarketingAuctionIgnitionFeatureConfiguration configuration;
    private final FeatureDelegateManager featureDelegateManager;
    public RemarketingAuctionIgnitionDelegate(RemarketingAuctionIgnitionFeatureConfiguration configuration,
                                              FeatureDelegateManager featureDelegateManager) {
        this.configuration = configuration;
        this.featureDelegateManager = featureDelegateManager;
    }

    @Override
    public Auction checkoutCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException {
        return featureDelegateManager
                .getDelegateToRun(configuration, RemarketingAuctionIgnitionFeature.class, car, auctionTypeId)
                .checkoutCar(car, auctionTypeId);
    }

    @Override
    public Auction rerunCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException {
        return featureDelegateManager
                .getDelegateToRun(configuration, RemarketingAuctionIgnitionFeature.class, car, auctionTypeId)
                .rerunCar(car, auctionTypeId);
    }
}
