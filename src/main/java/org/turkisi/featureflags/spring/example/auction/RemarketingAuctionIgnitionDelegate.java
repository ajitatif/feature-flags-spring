package org.turkisi.featureflags.spring.example.auction;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.core.FeatureDelegateBase;
import org.turkisi.featureflags.spring.core.FeatureDelegationManager;
import org.turkisi.featureflags.spring.example.domain.Auction;
import org.turkisi.featureflags.spring.example.domain.CarLead;

@Component
@Primary
public class RemarketingAuctionIgnitionDelegate extends FeatureDelegateBase<RemarketingAuctionIgnitionFeature>
        implements RemarketingAuctionIgnitionFeature {

    public RemarketingAuctionIgnitionDelegate(RemarketingAuctionIgnitionFeatureConfiguration configuration,
                                              FeatureDelegationManager featureDelegationManager) {
        super(RemarketingAuctionIgnitionFeature.class, configuration, featureDelegationManager);
    }

    @Override
    public Auction checkoutCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException {
        return getDelegateToRun(car, auctionTypeId)
                .checkoutCar(car, auctionTypeId);
    }

    @Override
    public Auction rerunCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException {
        return getDelegateToRun(car, auctionTypeId)
                .rerunCar(car, auctionTypeId);
    }
}
