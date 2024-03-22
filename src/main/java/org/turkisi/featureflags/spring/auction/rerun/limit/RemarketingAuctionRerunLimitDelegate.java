package org.turkisi.featureflags.spring.auction.rerun.limit;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.core.FeatureDelegateManager;
import org.turkisi.featureflags.spring.domain.CarLead;

@Component
@Primary
public class RemarketingAuctionRerunLimitDelegate implements RemarketingAuctionRerunLimitFeature {

    private final RemarketingAuctionRerunLimitFeatureConfiguration configuration;
    private final FeatureDelegateManager featureDelegateManager;
    public RemarketingAuctionRerunLimitDelegate(RemarketingAuctionRerunLimitFeatureConfiguration configuration,
                                                FeatureDelegateManager featureDelegateManager) {
        this.configuration = configuration;
        this.featureDelegateManager = featureDelegateManager;
    }

    @Override
    public void validateCar(@Nonnull CarLead carLead) throws CarAuctionRerunLimitExhaustedException {
        featureDelegateManager
                .getDelegateToRun(configuration, RemarketingAuctionRerunLimitFeature.class, carLead)
                .validateCar(carLead);
    }
}
