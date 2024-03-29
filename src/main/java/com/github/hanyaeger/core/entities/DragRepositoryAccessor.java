package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.core.repositories.DragNDropRepository;

/**
 * A {@code DragRepositoryAccessor} needs access to a {@link DragNDropRepository} to either set or get information
 * about the object that is currently being dragged/dropped.
 */
public interface DragRepositoryAccessor {

    /**
     * Set the {@link DragNDropRepository}.
     * <p>
     * Note: This method is part of the internal API, and should not be used when implementing a Yaeger game.
     *
     * @param dragNDropRepository the {@link DragNDropRepository} to be used
     */
    void setDragNDropRepository(DragNDropRepository dragNDropRepository);

    /**
     * Return the {@link DragNDropRepository} that was set on this {@code DragRepositoryAccessor}.
     * <p>
     * Note: This method is part of the internal API, and should not be used when implementing a Yaeger game.
     *
     * @return the instance of {@link DragNDropRepository}
     */
    DragNDropRepository getDragNDropRepository();
}
