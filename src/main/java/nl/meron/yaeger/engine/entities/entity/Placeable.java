package nl.meron.yaeger.engine.entities.entity;

import javafx.geometry.Point2D;

/**
 * A {@link Placeable} has a {@code Position} and can be placed at a different {@code Position}.
 */
public interface Placeable extends Bounded {

    /**
     * @return the position as a {@link Point2D}.
     */
    default Point2D getLocation() {
        return new Point2D(getLeftX(), getTopY());
    }

    /**
     * Set the Location to the given x and y-coordinate.
     *
     * @param x a {@code double} representing the x coordinate
     * @param y a {@code double} representing the y coordinate
     */
    void placeOnLocation(final double x, final double y);
}
