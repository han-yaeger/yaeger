package com.github.hanyaeger.core.exceptions;

import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.api.scenes.StaticScene;

/**
 * A {@link YaegerSceneNotAvailableException} is thrown when a  {@link YaegerScene} is
 * requested that is not available.
 */
public class YaegerSceneNotAvailableException extends RuntimeException {

    private final int id;

    /**
     * Instantiate a new {@code YaegerSceneNotAvailableException} with the given {@link Integer}.
     *
     * @param id the id of the {@link StaticScene}
     */
    public YaegerSceneNotAvailableException(final Integer id) {
        super("Scene " + id + " is not available. Ensure the scene is added to the game.");
        this.id = id;
    }

    /**
     * Return the {@code int} that was requested, but not available.
     *
     * @return the {@code int} that was requested, but not available
     */
    public int getId() {
        return id;
    }
}
