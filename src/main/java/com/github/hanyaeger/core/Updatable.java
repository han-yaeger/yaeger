package com.github.hanyaeger.core;

/**
 * Implement this interface to be updated every cycle of the game loop.
 */
@FunctionalInterface
public interface Updatable {

    /**
     * The update() method is called each frame.
     *
     * <p>
     * Use this method to init frame-based behaviour to the game-object.
     * </p>
     *
     * @param timestamp the timestamp of this update
     */
    void update(final long timestamp);
}
