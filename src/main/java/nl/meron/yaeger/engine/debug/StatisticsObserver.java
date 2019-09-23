package nl.meron.yaeger.engine.debug;

import nl.meron.yaeger.engine.entities.EntityCollectionStatistics;
import nl.meron.yaeger.engine.entities.EntityCollection;

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
