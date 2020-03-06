package nl.meron.yaeger.engine.entities.entitymap;

import java.util.List;

/**
 * Implementing the {@link WithEntityMapList} interface guarantees that a {@link List} of
 * instances of {@link EntityMap} is available.
 */
public interface WithEntityMapList {

    /**
     * Return the {@link List} of {@link EntityMap} instances.
     *
     * @return the {@link List} of {@link EntityMap} instances
     */
    List<EntityMap> getEntityMaps();
}
