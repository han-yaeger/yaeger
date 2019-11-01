package nl.meron.yaeger.engine.entities.entity;

import javafx.geometry.Bounds;

/**
 * Implementing this interface exposes the {@code getBounds} method, which returns the bounds, aka
 * Bounding Box, of this Entity.
 */
public interface Bounded extends NodeProvider {

    /**
     * Return the {@link Bounds}, aka Bounding Box.
     *
     * @return the {@link Bounds}
     */
    default Bounds getBounds() {
        return getGameNode().getBoundsInParent();
    }
}
