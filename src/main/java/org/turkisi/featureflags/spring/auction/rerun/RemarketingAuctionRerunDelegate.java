package org.turkisi.featureflags.spring.auction.rerun;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.auction.AuctionIgnitionFailException;
import org.turkisi.featureflags.spring.core.FeatureDelegateManager;
import org.turkisi.featureflags.spring.domain.Auction;
import org.turkisi.featureflags.spring.domain.CarLead;

@Component
@Primary
public class RemarketingAuctionRerunDelegate implements RemarketingAuctionRerunFeature {

    private final RemarketingAuctionRerunFeatureConfiguration configuration;
    private final FeatureDelegateManager featureDelegateManager;
    public RemarketingAuctionRerunDelegate(RemarketingAuctionRerunFeatureConfiguration configuration,
                                           FeatureDelegateManager featureDelegateManager) {
        this.configuration = configuration;
        this.featureDelegateManager = featureDelegateManager;
    }

    @Override
    public Auction rerunCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException {
        return featureDelegateManager
                .getDelegateToRun(configuration, RemarketingAuctionRerunFeature.class, car, auctionTypeId)
                .rerunCar(car, auctionTypeId);
    }
}
