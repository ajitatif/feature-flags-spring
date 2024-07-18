package org.turkisi.featureflags.spring.example.auction.rerun.limit;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.core.FeatureDelegateBase;
import org.turkisi.featureflags.spring.core.FeatureDelegationManager;
import org.turkisi.featureflags.spring.example.domain.CarLead;

@Component
@Primary
public class RemarketingAuctionRerunLimitDelegate extends FeatureDelegateBase<RemarketingAuctionRerunLimitFeature>
        implements RemarketingAuctionRerunLimitFeature {

    public RemarketingAuctionRerunLimitDelegate(RemarketingAuctionRerunLimitFeatureConfiguration configuration,
                                                FeatureDelegationManager featureDelegationManager) {
        super(RemarketingAuctionRerunLimitFeature.class, configuration, featureDelegationManager);
    }

    @Override
    public void validateCar(@Nonnull CarLead carLead) throws CarAuctionRerunLimitExhaustedException {
        getDelegateToRun(carLead)
                .validateCar(carLead);
    }
}
