package com.github.hanyaeger.core.entities;

/**
 * A {@code StatisticsObserver} will function as the {@code Observer} from the Observable-pattern, for changes in
 * the {@link EntityCollectionStatistics}.
 */
@FunctionalInterface
public interface StatisticsObserver {

    /**
     * Is called by the observed {@link EntityCollection}.
     *
     * @param statistics an instance of {@link EntityCollectionStatistics} that encapsulates the latest statistical
     *                   information regarding the observed {@link EntityCollection}
     * @param timestamp  the timestamp of the update
     */
    void update(final EntityCollectionStatistics statistics, final long timestamp);
}
