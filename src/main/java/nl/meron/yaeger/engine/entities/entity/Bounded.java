package nl.meron.yaeger.engine.entities.entity;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Implementing this interface exposes the {@link Bounded#getBounds()} method, which returns the bounds, aka
 * Bounding Box, of this Entity.
 */
public interface Bounded extends NodeProvider {

    /**
     * Return the {@link Bounds}, aka Bounding Box.
     *
     * @return the {@link Bounds}
     */
    default Bounds getBounds() {
        if (getGameNode().isPresent()) {
            return getGameNode().get().getBoundsInLocal();
        } else {
            return new BoundingBox(0, 0, 0, 0);
        }
    }

    /**
     * @return a {@code double} of the right side x value
     */
    default double getRightX() {
        return getBounds().getMaxX();
    }

    /**
     * @return a {@code double} of the left x value
     */
    default double getLeftX() {
        return getBounds().getMinX();
    }

    /**
     * @return a {@code double} of the bottom y value
     */
    default double getBottomY() {
        return getBounds().getMaxY();
    }

    /**
     * @return a {@code double} of the top y value
     */
    default double getTopY() {
        return getBounds().getMinY();
    }
}
