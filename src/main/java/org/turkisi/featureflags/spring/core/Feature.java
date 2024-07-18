package org.turkisi.featureflags.spring.core;

/**
 * Marker interface for a feature. A feature implementation <b>must</b> implement this interface in order to be picked
 * up by the {@link FeatureDelegationManager}. In most cases, an experimented feature implementation implements a
 * business feature interface which extends {@code Feature}. e.g.
 * <pre>{@code
 *  public class EnabledBusinessFeature implements BusinessFeature {
 *      ...
 *  }
 *
 *  public interface BusinessFeature extends Feature {
 *     ...
 *  }
 * }</pre>
 */
public interface Feature {
}
