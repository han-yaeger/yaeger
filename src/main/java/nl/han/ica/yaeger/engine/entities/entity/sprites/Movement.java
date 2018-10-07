package nl.han.ica.yaeger.engine.entities.entity.sprites;

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
    public Movement(final double direction, final double speed) {
        this.direction = direction;
        this.speed = speed;
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
