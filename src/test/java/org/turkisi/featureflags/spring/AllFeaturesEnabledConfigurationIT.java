package org.turkisi.featureflags.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.turkisi.featureflags.spring.auction.AuctionIgnitionFailException;
import org.turkisi.featureflags.spring.auction.RemarketingAuctionIgnitionFeatureDelegate;
import org.turkisi.featureflags.spring.auction.rerun.limit.CarAuctionRerunLimitExhaustedException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("all-enabled")
class AllFeaturesEnabledConfigurationIT {

    @Autowired
    private RemarketingAuctionIgnitionFeatureDelegate remarketingAuctionIgnitionFeature;

    @Test
    void shouldCheckoutAndRerunWhenCarIsNew() throws AuctionIgnitionFailException {
        assertNotNull(remarketingAuctionIgnitionFeature.checkoutCar(TestDataHelper.createCar(), 1));
        assertNotNull(remarketingAuctionIgnitionFeature.rerunCar(TestDataHelper.createCar(), 2));
    }

    @Test
    void shouldNotCheckoutWhenCarIsNotNew() {
        assertThrows(AuctionIgnitionFailException.class, () -> remarketingAuctionIgnitionFeature.checkoutCar(TestDataHelper.createCar(1), 1));
    }

    @Test
    void shouldNotRerunWhenLimitExhausted() {
        assertThrows(CarAuctionRerunLimitExhaustedException.class,
                () -> remarketingAuctionIgnitionFeature.rerunCar(TestDataHelper.createCar(7), 2));
    }
}
