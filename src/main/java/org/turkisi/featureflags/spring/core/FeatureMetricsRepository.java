package org.turkisi.featureflags.spring.core;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * A basic repository to keep the number of hits per feature implementation
 */
@Component
public class FeatureMetricsRepository {

    private final Map<String, Long> featureUsage = new HashMap<>();

    void incrementFeatureHits(String featureName) {
        featureUsage.compute(featureName, (k, v) -> v == null ? 1 : v + 1);
    }

    void resetMetrics() {
        featureUsage.clear();
    }
    public Long getFeatureHits(String featureName) {
        return featureUsage.getOrDefault(featureName, 0L);
    }
}
