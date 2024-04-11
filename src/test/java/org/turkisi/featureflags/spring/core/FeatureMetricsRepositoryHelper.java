package org.turkisi.featureflags.spring.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureMetricsRepositoryHelper {

    @Autowired
    private FeatureMetricsRepository featureMetricsRepository;

    public void resetMetrics() {
        featureMetricsRepository.resetMetrics();
    }
}
