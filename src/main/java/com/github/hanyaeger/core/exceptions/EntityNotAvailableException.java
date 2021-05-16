package com.github.hanyaeger.core.exceptions;

import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.TileMap;

/**
 * A {@link EntityNotAvailableException} should be thrown if an {@link YaegerEntity}
 * is requested that is not available. Most likely place for this to occur is an {@link TileMap}.
 */
public class EntityNotAvailableException extends RuntimeException {

    /**
     * Create a new {@link EntityNotAvailableException} with the given message.
     *
     * @param message the message that should be shown when the exception is thrown
     */
    public EntityNotAvailableException(final String message) {
        super(message);
    }
}
