package nl.han.yaeger.engine.scenes;

import nl.han.yaeger.engine.entities.EntitySpawner;

import java.util.List;

/**
 * Implementing the {@link EntitySpawnerListProvider} interface guarantees that a {@link List} of
 * instances of {@link EntitySpawner} is available.
 */
public interface EntitySpawnerListProvider {

    /**
     * Return the {@link List} of {@link EntitySpawner} instances.
     *
     * @return the {@link List} of {@link EntitySpawner} instances
     */
    List<EntitySpawner> getSpawners();
}
