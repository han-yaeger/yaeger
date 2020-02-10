package nl.meron.yaeger.engine.entities.entity;

import javafx.geometry.Point2D;

/**
 * A {@link Location} is a 2D geometric point that represents a pair of coordinates.
 */
public class Location extends Point2D {

    /**
     * Creates a new instance of {@link Location}.
     *
     * @param x the x coordinate of the {@link Location}
     * @param y the y coordinate of the {@link Location}it
     */
    public Location(final double x, final double y) {
        super(x, y);
    }

    /**
     * Creates a new instance of a {@link Location}.
     *
     * @param point a {@link Point2D representation of this {@link Location}
     */
    public Location(final Point2D point) {
        super(point.getX(), point.getY());
    }

    @Override
    public Location add(final Point2D point2D) {
        return new Location(super.add(point2D));
    }

    public Location add(final Location location) {
        return new Location(super.add(location));
    }
}
