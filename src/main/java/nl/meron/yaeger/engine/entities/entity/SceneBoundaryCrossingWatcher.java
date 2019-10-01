package nl.meron.yaeger.engine.entities.entity;

import nl.meron.yaeger.engine.scenes.SceneBorder;

/**
 * Implement this interface to be notified if the {@link Entity} completely crosses
 * the boundary of the scene.
 */
@FunctionalInterface
public interface SceneBoundaryCrossingWatcher {

    /**
     * This method is being called when this {@link SceneBoundaryCrossingWatcher} crosses a boundary of the {@link javafx.scene.Scene}.
     *
     * @param border The border at which the {@link javafx.scene.Scene} is being crossed
     */
    void notifyBoundaryCrossing(SceneBorder border);
}
