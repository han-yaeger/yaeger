package nl.meron.yaeger.engine.entities.entity;

import javafx.geometry.Point2D;

/**
 * A {@link Placeable} has a {@code Position} and can be placed at a different {@code Position}.
 */
public interface Placeable extends Bounded {

    /**
     * @return the position as a {@link Point2D}.
     */
    default Point2D getPosition() {
        return new Point2D(getLeftSideX(), getTopY());
    }

    /**
     * Set the position of this {@code TextEntity}.
     *
     * @param position a {@link Point2D} encapsulating the x and y coordinate
     */
    void placeOnPosition(Point2D position);
}
