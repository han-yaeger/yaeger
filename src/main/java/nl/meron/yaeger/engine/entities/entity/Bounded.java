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
     * @return The {@link Bounds}.
     */
    default Bounds getBounds() {
        if (getGameNode().isPresent()) {
            return getGameNode().get().getBoundsInLocal();
        } else {
            return new BoundingBox(0, 0, 0, 0);
        }
    }

    /**
     * @return The width as a {@code double}.
     */
    default double getWidth() {
        return getBounds().getWidth();
    }

    /**
     * @return The height as a {@code double}.
     */
    default double getHeight() {
        return getBounds().getHeight();
    }

    /**
     * @return A {@code double} of the right side x value.
     */
    default double getRightX() {
        return getBounds().getMaxX();
    }

    /**
     * @return A {@code double} of the left x value.
     */
    default double getLeftX() {
        return getBounds().getMinX();
    }

    /**
     * @return A {@code double} of the center x value.
     */
    default double getCenterX() {
        return getBounds().getCenterX();
    }

    /**
     * @return A {@code double} of the bottom y value.
     */
    default double getBottomY() {
        return getBounds().getMaxY();
    }

    /**
     * @return A {@code double} of the top y value.
     */
    default double getTopY() {
        return getBounds().getMinY();
    }

    /**
     * @return A {@code double} of the center y value.
     */
    default double getCenterY() {
        return getBounds().getCenterY();
    }
}
