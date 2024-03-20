package org.turkisi.featureflags.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.turkisi.featureflags.spring.auction.AuctionIgnitionFailException;
import org.turkisi.featureflags.spring.auction.RemarketingAuctionIgnitionFeature;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("rerun-limit-disabled")
class AuctionRerunLimitsDisabledConfigurationIT {

    @Autowired
    private RemarketingAuctionIgnitionFeature remarketingAuctionIgnitionFeature;

    @Test
    void shouldCheckoutAndRerunWhenRerunLimitDisabled() throws AuctionIgnitionFailException {
        assertNotNull(remarketingAuctionIgnitionFeature.checkoutCar(TestDataHelper.createCar(), 1));
        assertNotNull(remarketingAuctionIgnitionFeature.rerunCar(TestDataHelper.createCar(100), 2));
    }
}
