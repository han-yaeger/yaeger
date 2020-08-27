package com.github.hanyaeger.api.engine.entities.entity;

import javafx.geometry.Point2D;

/**
 * A {@link Coordinate2D} is a 2D geometric point that represents a pair of coordinates.
 */
public class Coordinate2D extends Point2D {

    /**
     * Creates a new instance of {@link Coordinate2D}.
     *
     * @param x The x coordinate of the {@link Coordinate2D}.
     * @param y The y coordinate of the {@link Coordinate2D}.
     */
    public Coordinate2D(final double x, final double y) {
        super(x, y);
    }

    /**
     * Creates a new instance of a {@link Coordinate2D}.
     *
     * @param point A {@link Point2D} representation of this {@link Coordinate2D}.
     */
    public Coordinate2D(final Point2D point) {
        super(point.getX(), point.getY());
    }

    @Override
    public Coordinate2D add(final Point2D point2D) {
        return new Coordinate2D(super.add(point2D));
    }

    public Coordinate2D add(final Coordinate2D location) {
        return new Coordinate2D(super.add(location));
    }
}
