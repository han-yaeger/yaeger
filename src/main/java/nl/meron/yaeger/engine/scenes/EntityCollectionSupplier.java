package nl.meron.yaeger.engine.scenes;

import nl.meron.yaeger.engine.entities.EntityCollection;

/**
 * Provide a {@link EntityCollectionSupplier#getEntityCollection()} that returns an instance of {@link EntityCollection \}.
 */
public interface EntityCollectionSupplier {

    /**
     * Return the {@link EntityCollection} that is provided by this {@link EntityCollectionSupplier#getEntityCollection()}.
     *
     * @return The {@link EntityCollection} that is provided by this {@link EntityCollectionSupplier#getEntityCollection()}.
     */
    EntityCollection getEntityCollection();
}
