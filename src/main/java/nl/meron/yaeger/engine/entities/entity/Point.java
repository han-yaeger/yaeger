package nl.meron.yaeger.engine.entities.entity;

import javafx.geometry.Point2D;

/**
 * A 2D geometric point that represents the x, y coordinates.
 */
public class Point extends Point2D {

    /**
     * Creates a new instance of {@code Position}.
     *
     * @param x the x coordinate of the {@code Position}
     * @param y the y coordinate of the {@code Position}
     */
    public Point(double x, double y) {
        super(x, y);
    }

    /**
     * Creates a new instance of {@code Position}.
     *
     * @param point a {@link Point2D respresentation of this {@code Position}
     */
    public Point(Point2D point) {
        super(point.getX(), point.getY());
    }

    @Override
    public Point add(Point2D point2D) {
        return new Point(super.add(point2D));
    }

    public Point add(Point point) {
        return new Point(super.add(point));
    }
}
