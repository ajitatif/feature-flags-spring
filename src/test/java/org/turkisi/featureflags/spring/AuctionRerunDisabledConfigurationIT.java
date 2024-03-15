package org.turkisi.featureflags.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.turkisi.featureflags.spring.auction.AuctionIgnitionFailException;
import org.turkisi.featureflags.spring.auction.RemarketingAuctionIgnitionFeatureDelegate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("rerun-disabled")
class AuctionRerunDisabledConfigurationIT {

    @Autowired
    private RemarketingAuctionIgnitionFeatureDelegate remarketingAuctionIgnitionFeature;

    @Test
    void shouldCheckoutWhenRerunDisabled() throws AuctionIgnitionFailException {
        assertNotNull(remarketingAuctionIgnitionFeature.checkoutCar(TestDataHelper.createCar(), 1));
    }

    @Test
    void shouldNotRerunWhenRerunDisabled() {
        assertThrows(AuctionIgnitionFailException.class, () -> remarketingAuctionIgnitionFeature.rerunCar(TestDataHelper.createCar(), 2));
    }
}
