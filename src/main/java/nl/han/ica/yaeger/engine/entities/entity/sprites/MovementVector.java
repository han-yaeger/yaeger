package nl.han.ica.yaeger.engine.entities.entity.sprites;

import javafx.beans.NamedArg;
import javafx.geometry.Point2D;

/**
 * A {@code Movement} encapsulates both a {@code direction} and a {@code speed}.
 */
public class Movement {

    private double direction;
    private double speed;

    /**
     * Creates a new instance of {@code Movement}.
     */
    public Movement() {
        this(0, 0);
    }

    /**
     * Creates a new instance of {@code Movement}.
     *
     * @param direction the direction of the {@code Movement}
     * @param speed     the speed of the {@code Movement}
     */
    public Movement(@NamedArg("direction") final double direction, @NamedArg("direction") final double speed) {
        this.direction = direction;
        this.speed = speed;
    }

    /**
     * Return the angle in radians.
     *
     * @return a {@code double} representing the angle in radians
     */
    public double getAngleInRadians() {
        return Math.toRadians(getDirection() - 90);
    }

    /**
     * Return a vector representation of the movement.
     *
     * @return a {@link Point2D} representing a vector of the movement
     */
    public Point2D getVector() {
        var directionVector = new Point2D(Math.cos(getAngleInRadians()), Math.sin(getAngleInRadians()));

        return directionVector.normalize().multiply(getSpeed());
    }

    /**
     * @return the direction of the {@code Movement}
     */
    public double getDirection() {
        return direction;
    }

    /**
     * Set the direction
     *
     * @param direction the direction
     */
    public void setDirection(double direction) {
        this.direction = direction;
    }


    /**
     * @return the speed of the {@code Movement}
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Set the speed.
     *
     * @param speed the speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * A {@code Direction} provides default values for the direction of a {@link Movement}.
     */
    public class Direction {

        private Direction() {
        }

        public static final double UP = 0d;
        public static final double DOWN = 180d;
        public static final double LEFT = 270d;
        public static final double RIGHT = 90d;
    }
}
