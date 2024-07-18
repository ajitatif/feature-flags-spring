package org.turkisi.featureflags.spring.auction.rerun;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.auction.AuctionIgnitionFailException;
import org.turkisi.featureflags.spring.core.FeatureDelegateBase;
import org.turkisi.featureflags.spring.core.FeatureDelegationManager;
import org.turkisi.featureflags.spring.domain.Auction;
import org.turkisi.featureflags.spring.domain.CarLead;

@Component
@Primary
public class RemarketingAuctionRerunDelegate extends FeatureDelegateBase<RemarketingAuctionRerunFeature>
        implements RemarketingAuctionRerunFeature {

    public RemarketingAuctionRerunDelegate(RemarketingAuctionRerunFeatureConfiguration configuration,
                                           FeatureDelegationManager featureDelegationManager) {
        super(RemarketingAuctionRerunFeature.class, configuration, featureDelegationManager);
    }

    @Override
    public Auction rerunCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException {
        return getDelegateToRun(car, auctionTypeId)
                .rerunCar(car, auctionTypeId);
    }
}
