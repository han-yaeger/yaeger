package com.github.hanyaeger.api.engine.debug;

import com.github.hanyaeger.api.engine.entities.EntityCollectionStatistics;
import com.github.hanyaeger.api.engine.entities.EntityCollection;

/**
 * A {@link StatisticsObserver} will function as the {@code Observer} from the Observable-pattern, for changes in
 * the {@link EntityCollectionStatistics}.
 */
@FunctionalInterface
public interface StatisticsObserver {

    /**
     * Is called by the observed {@link EntityCollection}.
     *
     * @param statistics An instance of {@link EntityCollectionStatistics} that encapsulates the latest statistical
     *                   information regarding the observed {@link EntityCollection}.
     */
    void update(EntityCollectionStatistics statistics);
}
