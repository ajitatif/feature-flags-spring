package org.turkisi.featureflags.spring.auction.rerun;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.auction.AuctionIgnitionFailException;
import org.turkisi.featureflags.spring.domain.Auction;
import org.turkisi.featureflags.spring.domain.CarLead;

import java.util.Map;

@Component
public class RemarketingAuctionRerunFeatureDelegate implements RemarketingAuctionRerunFeature {

    private static final Logger logger = LoggerFactory.getLogger(RemarketingAuctionRerunFeatureDelegate.class);

    private final RemarketingAuctionRerunFeatureConfiguration configuration;

    private final Map<String, RemarketingAuctionRerunFeature> delegations;

    private RemarketingAuctionRerunFeature delegate;

    public RemarketingAuctionRerunFeatureDelegate(RemarketingAuctionRerunFeatureConfiguration configuration, Map<String, RemarketingAuctionRerunFeature> delegations) {
        this.configuration = configuration;
        this.delegations = delegations;
    }

    @PostConstruct
    void updateDelegation() {
        delegate = delegations.get(configuration.getDelegate());
        logger.debug("Delegate set to component {}", configuration.getDelegate());
    }

    @Override
    public Auction rerunCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException {
        return delegate.rerunCar(car, auctionTypeId);
    }
}
