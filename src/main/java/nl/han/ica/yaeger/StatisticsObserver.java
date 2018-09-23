package nl.han.ica.yaeger;

import nl.han.ica.yaeger.entities.EntityCollectionStatistics;

@FunctionalInterface
public interface StatisticsObserver {

    void update(EntityCollectionStatistics statistics);
}
