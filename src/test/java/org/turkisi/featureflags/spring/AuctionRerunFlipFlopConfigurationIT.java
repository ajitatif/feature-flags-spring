package org.turkisi.featureflags.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.turkisi.featureflags.spring.auction.AuctionIgnitionFailException;
import org.turkisi.featureflags.spring.auction.RemarketingAuctionIgnitionFeature;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("rerun-flipflopped")
class AuctionRerunFlipFlopConfigurationIT {

    @Autowired
    private RemarketingAuctionIgnitionFeature remarketingAuctionIgnitionFeature;

    @Test
    void shouldCheckoutWhenRerunDisabled() throws AuctionIgnitionFailException {
        assertNotNull(remarketingAuctionIgnitionFeature.checkoutCar(TestDataHelper.createCar(), 1));
    }

    @Test
    void shouldFlipFlopRerunWhenRerunFlipFlopped() throws AuctionIgnitionFailException {
        assertNotNull(remarketingAuctionIgnitionFeature.rerunCar(TestDataHelper.createCar(1), 2));
        assertThrows(AuctionIgnitionFailException.class, () -> remarketingAuctionIgnitionFeature.rerunCar(TestDataHelper.createCar(), 2));
    }
}
