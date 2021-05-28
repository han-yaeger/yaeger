package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.core.repositories.DragNDropRepository;

/**
 * A {@code DragRepositoryAccessor} needs access to a {@link DragNDropRepository} to either set or get information
 * about the object that is currently being dragged/dropped.
 */
public interface DragRepositoryAccessor {

    /**
     * Set the {@link DragNDropRepository}
     * @param dragNDropRepository
     */
    void setDragNDropRepository(DragNDropRepository dragNDropRepository);

    DragNDropRepository getDragNDropRepository();
}
