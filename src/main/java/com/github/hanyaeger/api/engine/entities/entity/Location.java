package com.github.hanyaeger.api.engine.entities.entity;

import javafx.geometry.Point2D;

/**
 * A {@link Location} is a 2D geometric point that represents a pair of coordinates.
 */
public class Location extends Point2D {

    /**
     * Creates a new instance of {@link Location}.
     *
     * @param x The x coordinate of the {@link Location}.
     * @param y The y coordinate of the {@link Location}.
     */
    public Location(final double x, final double y) {
        super(x, y);
    }

    /**
     * Creates a new instance of a {@link Location}.
     *
     * @param point A {@link Point2D} representation of this {@link Location}.
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
