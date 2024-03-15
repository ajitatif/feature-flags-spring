package org.turkisi.featureflags.spring.auction.rerun.limit;

import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.domain.CarLead;

import java.util.Map;

@Component
public class RemarketingAuctionRerunLimitFeatureDelegate implements RemarketingAuctionRerunLimitFeature {

    private static final Logger logger = LoggerFactory.getLogger(RemarketingAuctionRerunLimitFeatureDelegate.class);

    private final RemarketingAuctionRerunLimitFeatureConfiguration configuration;

    private final Map<String, RemarketingAuctionRerunLimitFeature> delegations;

    private RemarketingAuctionRerunLimitFeature delegate;

    public RemarketingAuctionRerunLimitFeatureDelegate(RemarketingAuctionRerunLimitFeatureConfiguration configuration, Map<String, RemarketingAuctionRerunLimitFeature> delegations) {
        this.configuration = configuration;
        this.delegations = delegations;
    }

    @PostConstruct
    void updateDelegation() {
        delegate = delegations.get(configuration.getDelegate());
        logger.debug("Delegate set to component {}", configuration.getDelegate());
    }

    @Override
    public void validateCar(@Nonnull CarLead carLead) throws CarAuctionRerunLimitExhaustedException{
        delegate.validateCar(carLead);
    }
}
