package org.turkisi.featureflags.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.turkisi.featureflags.spring.auction.AuctionIgnitionFailException;
import org.turkisi.featureflags.spring.auction.RemarketingAuctionIgnitionFeature;
import org.turkisi.featureflags.spring.core.FeatureMetricsRepository;
import org.turkisi.featureflags.spring.core.FeatureMetricsRepositoryHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("rerun-flipflopped")
class FeatureMonitoringIT {

    @Autowired
    private FeatureMetricsRepository featureMetricsRepository;

    @Autowired
    private RemarketingAuctionIgnitionFeature remarketingAuctionIgnitionFeature;

    @Autowired
    private FeatureMetricsRepositoryHelper metricsRepositoryHelper;
    @BeforeEach
    void setUp() {
        metricsRepositoryHelper.resetMetrics();
    }

    @Test
    void shouldMonitorAllFeatures() throws AuctionIgnitionFailException {
        remarketingAuctionIgnitionFeature.rerunCar(TestDataHelper.createCar(), 1);
        assertEquals(1, featureMetricsRepository.getFeatureHits("DefaultRemarketingAuctionIgnitionFeature"));
        assertEquals(0, featureMetricsRepository.getFeatureHits("DisabledRemarketingAuctionIgnitionFeature"));
        assertEquals(1, featureMetricsRepository.getFeatureHits("DefaultRemarketingAuctionRerunFeature"));
        assertEquals(0, featureMetricsRepository.getFeatureHits("DisabledRemarketingAuctionRerunFeature"));
        assertEquals(1, featureMetricsRepository.getFeatureHits("DefaultRemarketingAuctionRerunLimitFeature"));
        assertEquals(0, featureMetricsRepository.getFeatureHits("DisabledRemarketingAuctionRerunLimitFeature"));
    }
}
