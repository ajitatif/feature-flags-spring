package org.turkisi.featureflags.spring.core.experiment;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/** Annotation which marks a feature. Implies {@link Component}.
 * A feature implementation <b>must</b> have this annotation in order to be detectable
 * by the {@link org.turkisi.featureflags.spring.core.FeatureDelegationManager}
 */
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ExperimentedFeature {
}
