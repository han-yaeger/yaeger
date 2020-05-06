package nl.meron.yaeger.engine.entities.entity;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import nl.meron.yaeger.engine.scenes.DimensionsProvider;

/**
 * Implementing this interface exposes the {@link Bounded#getNonTransformedBounds()} method, which returns the bounds, aka
 * Bounding Box, of this Entity.
 */
public interface Bounded extends DimensionsProvider, NodeProvider {

    /**
     * Return the {@link Bounds}, aka Bounding Box after all transformations have been
     * applied.
     *
     * @return The {@link Bounds}.
     */
    default Bounds getTransformedBounds() {
        if (getGameNode().isPresent()) {
            return getGameNode().get().getBoundsInParent();
        } else {
            return new BoundingBox(0, 0, 0, 0);
        }
    }

    /**
     * Return the {@link Bounds}, aka Bounding Box before all transformations have been applied.
     *
     * @return The {@link Bounds}.
     */
    default Bounds getNonTransformedBounds() {
        if (getGameNode().isPresent()) {
            return getGameNode().get().getBoundsInLocal();
        } else {
            return new BoundingBox(0, 0, 0, 0);
        }
    }

    @Override
    default double getWidth() {
        return getNonTransformedBounds().getWidth();
    }

    @Override
    default double getHeight() {
        return getNonTransformedBounds().getHeight();
    }

    /**
     * @return A {@code double} of the right side x value.
     */
    default double getRightX() {
        return getNonTransformedBounds().getMaxX();
    }

    /**
     * @return A {@code double} of the left x value.
     */
    default double getLeftX() {
        return getNonTransformedBounds().getMinX();
    }

    /**
     * @return A {@code double} of the center x value.
     */


    default double getCenterX() {
        return getNonTransformedBounds().getCenterX();
    }


     // @return A {@code double} of the bottom y value.

    default double getBottomY() {
        return getNonTransformedBounds().getMaxY();
    }

    /**
     * @return A {@code double} of the top y value.
     */
    default double getTopY() {
        return getNonTransformedBounds().getMinY();
    }

    /**
     * @return A {@code double} of the center y value.
     */

    default double getCenterY() {

        return getNonTransformedBounds().getCenterY();
    }
}
