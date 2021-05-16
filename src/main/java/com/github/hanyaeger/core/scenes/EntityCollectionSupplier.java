package com.github.hanyaeger.core.scenes;

import com.github.hanyaeger.core.entities.EntityCollection;

/**
 * Provide a {@link EntityCollectionSupplier#getEntityCollection()} that returns an instance of {@link EntityCollection \}.
 */
public interface EntityCollectionSupplier {

    /**
     * Return the {@link EntityCollection} that is provided by this {@code EntityCollectionSupplier}.
     *
     * @return the {@link EntityCollection} that is provided by this {@code EntityCollectionSupplier}
     */
    EntityCollection getEntityCollection();
}
