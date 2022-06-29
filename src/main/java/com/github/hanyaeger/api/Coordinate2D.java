package com.github.hanyaeger.api;

import javafx.geometry.Point2D;

/**
 * A {@code Coordinate2D} is a 2D geometric point that represents a pair of coordinates.
 */
public class Coordinate2D extends Point2D {

    /**
     * The message as to be shown in case a {@link NullPointerException} occurs as the result of
     * some methods from this class.
     */
    public static final String NULLPOINTER_LOCATION = "'location' was null";

    /**
     * The message as to be shown in case a {@link IllegalArgumentException} occurs as the result of
     * some methods from this class.
     */
    public static final String EXCEPTION_X_Y_IS_0 = "An X,Y component of either coordinate is 0";

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
     * Creates a new instance of a {@code Coordinate2D}.
     *
     * @param point a {@link Point2D} representation of this {@code Coordinate2D}
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

    /**
     * Return a {@code Coordinate2D} with the coordinates of the specified point subtracted from the coordinates of this
     * {@code Coordinate2D}.
     *
     * @param location the {@code Coordinate2D} with the subtracted coordinates
     * @return the {@code Coordinate2D} with the subtracted coordinates
     * throws {@link NullPointerException} if the specified {@code Coordinate2D} is null
     */
    public Coordinate2D subtract(final Coordinate2D location) {
        return new Coordinate2D(super.subtract(location));
    }

    /**
     * Return a {@code Coordinate2D} with the coordinates of the specified point multiplied by the coordinates of this
     * {@code Coordinate2D}.
     *
     * @param location the {@code Coordinate2D} with the multiplied coordinates
     * @return the {@code Coordinate2D} with the multiplied coordinates
     * throws {@link NullPointerException} if the specified {@code Coordinate2D} is null
     */
    public Coordinate2D multiply(final Coordinate2D location) throws NullPointerException {
        if (location == null) {
            throw new NullPointerException(NULLPOINTER_LOCATION);
        }

        return new Coordinate2D(this.getX() * location.getX(), this.getY() * location.getY());
    }

    /**
     * Return a {@code Coordinate2D} with the coordinates of the specified point divided by the coordinates of this
     * {@code Coordinate2D}.
     *
     * @param location the {@code Coordinate2D} with the divided coordinates
     * @return the {@code Coordinate2D} with the divided coordinates
     * throws {@link NullPointerException} if the specified {@code Coordinate2D} is null
     * throws {@link IllegalArgumentException} if an X,Y component of either coordinate is 0
     */
    public Coordinate2D divide(final Coordinate2D location) throws NullPointerException, IllegalArgumentException {
        if (location == null) {
            throw new NullPointerException(NULLPOINTER_LOCATION);
        }

        if (location.getX() == 0.D || location.getY() == 0.D) {
            throw new IllegalArgumentException(EXCEPTION_X_Y_IS_0);
        }

        return new Coordinate2D(this.getX() / location.getX(), this.getY() / location.getY());
    }

    /**
     * Returns a {@code Coordinate2D} which lies in the middle between this {@code Coordinate2D} and the specified
     * {@code Coordinate2D}.
     *
     * @param location is the other endpoint as a {@code Coordinate2D}
     * @return the coordinate in the middle.
     * throws {@link NullPointerException} if the specified {@code Coordinate2D} is null
     */
    public Coordinate2D middlePoint(final Coordinate2D location) throws NullPointerException {
        if (location == null) {
            throw new NullPointerException(NULLPOINTER_LOCATION);
        }

        return new Coordinate2D(super.midpoint(location));
    }

    /***
     * Return a normalized {@code Coordinate2D}.
     * @return the normalized variant of 'this' vector
     */
    @Override
    public Coordinate2D normalize() {
        return new Coordinate2D(super.normalize());
    }

    /**
     * Return a {@code Coordinate2D} which is the inverse of this coordinate
     *
     * @return the {@code Coordinate2D} with the location of this coordinate, inverted
     */
    public Coordinate2D invert() {
        return new Coordinate2D(this.getX() * -1, this.getY() * -1);
    }

    /**
     * Returns a {@code double} with the angle to the specified point of this {@code Coordinate2D}
     *
     * @param coordinate the {@code Coordinate2D} where the angle is going to be calculated to
     * @return the {@code double} of the angle between coordinates
     */
    public double angleTo(final Coordinate2D coordinate) {

        if (this.equals(coordinate)) {
            return 0D;
        }

        final var delta = coordinate.subtract(this);
        final var normalizedDelta = delta.normalize();
        var angle = new Point2D(0, 1).angle(normalizedDelta);

        if (delta.getX() < 0) {
            angle = 360 - angle;
        }
        return angle;
    }
}
