
package org.turkisi.featureflags.spring.auction;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.domain.Auction;
import org.turkisi.featureflags.spring.domain.CarLead;

import java.util.Map;

@Component
public class RemarketingAuctionIgnitionFeatureDelegate implements RemarketingAuctionIgnitionFeature {

    private static final Logger logger = LoggerFactory.getLogger(RemarketingAuctionIgnitionFeatureDelegate.class);

    private final RemarketingAuctionIgnitionFeatureConfiguration configuration;

    private final Map<String, RemarketingAuctionIgnitionFeature> delegations;

    private RemarketingAuctionIgnitionFeature delegate;

    public RemarketingAuctionIgnitionFeatureDelegate(RemarketingAuctionIgnitionFeatureConfiguration configuration, Map<String, RemarketingAuctionIgnitionFeature> delegations) {
        this.configuration = configuration;
        this.delegations = delegations;
    }

    @PostConstruct
    void updateDelegation() {
        delegate = delegations.get(configuration.getDelegate());
        logger.debug("Delegate set to component {}", configuration.getDelegate());
    }
    
    @Override
    public Auction checkoutCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException {
        return delegate.checkoutCar(car, auctionTypeId);
    }

    @Override
    public Auction rerunCar(CarLead car, int auctionTypeId) throws AuctionIgnitionFailException {
        return delegate.rerunCar(car, auctionTypeId);
    }
}
