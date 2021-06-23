package com.github.hanyaeger.api;

import javafx.geometry.Point2D;

/**
 * A {@link Coordinate2D} is a 2D geometric point that represents a pair of coordinates.
 */
public class Coordinate2D extends Point2D {

    /**
     * Creates a new instance of {@link Coordinate2D} at (0,0).
     */
    public Coordinate2D() {
        super(0, 0);
    }

    /**
     * Creates a new instance of {@link Coordinate2D}.
     *
     * @param x the x coordinate of the {@link Coordinate2D}
     * @param y the y coordinate of the {@link Coordinate2D}
     */
    public Coordinate2D(final double x, final double y) {
        super(x, y);
    }

    /**
     * Creates a new instance of a {@link Coordinate2D}.
     *
     * @param point a {@link Point2D} representation of this {@link Coordinate2D}
     */
    public Coordinate2D(final Point2D point) {
        super(point.getX(), point.getY());
    }

    @Override
    public Coordinate2D add(final Point2D point2D) {
        return new Coordinate2D(super.add(point2D));
    }

    /**
     * Returns a {@code Coordinate2D} with the coordinates of the specified point added to the coordinates of this
     * {@code Coordinate2D}.
     *
     * @param location the {@code Coordinate2D} those coordinates are to be added
     * @return the {@code Coordinate2D} with the added coordinates
     * throws {@link NullPointerException} if the specified {@code Coordinate2D} is null
     */
    public Coordinate2D add(final Coordinate2D location) {
        return new Coordinate2D(super.add(location));
    }
}
