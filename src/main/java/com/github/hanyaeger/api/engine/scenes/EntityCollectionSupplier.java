package com.github.hanyaeger.api.engine.scenes;

import com.github.hanyaeger.api.engine.entities.EntityCollection;

/**
 * Provide a {@link EntityCollectionSupplier#getEntityCollection()} that returns an instance of {@link EntityCollection \}.
 */
public interface EntityCollectionSupplier {

    /**
     * Return the {@link EntityCollection} that is provided by this {@link EntityCollectionSupplier#getEntityCollection()}.
     *
     * @return the {@link EntityCollection} that is provided by this {@link EntityCollectionSupplier#getEntityCollection()}
     */
    EntityCollection getEntityCollection();
}
