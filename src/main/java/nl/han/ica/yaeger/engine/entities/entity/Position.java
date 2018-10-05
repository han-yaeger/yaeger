package nl.han.ica.yaeger.engine.entities.entity;

import javafx.geometry.Point2D;

/**
 * A 2D geometric point that represents the x, y coordinates.
 */
public class Position extends Point2D {

    /**
     * Creates a new instance of {@code Position}.
     *
     * @param x the x coordinate of the {@code Position}
     * @param y the y coordinate of the {@code Position}
     */
    public Position(double x, double y) {
        super(x, y);
    }
}
