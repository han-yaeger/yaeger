package com.github.hanyaeger.core.entities.motion;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Direction;
import com.github.hanyaeger.api.entities.YaegerEntity;
import javafx.geometry.Point2D;

import java.util.Optional;

/**
 * A {@link MotionApplier} provides basis behaviour regarding speed and direction of a
 * {@link YaegerEntity}
 */
public class MotionApplier implements MotionModifier, NewtonianModifier, LocationUpdater {

    private static final Point2D IDENTITY_MOTION = new Point2D(0, 1);
    private static final Point2D NON_MOTION = new Point2D(0, 0);
    private Optional<Double> direction = Optional.empty();
    private Coordinate2D motion;

    /**
     * The default value to be used for the Gravitational constant, if none are set.
     * This value is set at 0.2.
     */
    public static final double DEFAULT_GRAVITATIONAL_CONSTANT = 0.2d;
    /**
     * The default value to be used for the Friction constant, if none are set.
     * This value is set at 0.01.
     */
    public static final double DEFAULT_FRICTION_CONSTANT = 0.01d;
    /**
     * The default value to be used for the Gravitational direction, if none are set.
     * This value is set to {@link Direction#DOWN}.
     */
    public static final double DEFAULT_GRAVITATIONAL_DIRECTION = Direction.DOWN.getValue();

    private double frictionConstant = DEFAULT_FRICTION_CONSTANT;
    private double gravityConstant = DEFAULT_GRAVITATIONAL_CONSTANT;
    private double gravityDirection = DEFAULT_GRAVITATIONAL_DIRECTION;

    /**
     * Create a new instance of {@link MotionApplier}.
     */
    public MotionApplier() {
        motion = new Coordinate2D();
    }

    @Override
    public void setMotion(final double speed, final double direction) {
        setSpeed(speed);
        setDirection(direction);
    }

    @Override
    public void setMotion(final double speed, final Direction direction) {
        setMotion(speed, direction.getValue());
    }

    @Override
    public void addToMotion(final double speed, final Direction direction) {
        addToMotion(speed, direction.getValue());
    }

    @Override
    public double getSpeedInDirection(final Direction direction) {
        return getSpeedInDirection(direction.getValue());
    }

    @Override
    public double getSpeedInDirection(final double direction) {
        var speed = 0D;
        if (Double.compare(getDirection(), direction) != 0) {
            final var normalizedVector = createVector(1, direction);
            final var dotProduct = normalizedVector.dotProduct(motion);

            if (dotProduct > 0) {
                speed = calculateDenormalizedVector(normalizedVector, motion).magnitude();
            }
        }
        return speed;
    }

    @Override
    public void invertSpeedInDirection(final Direction direction) {
        invertSpeedInDirection(direction.getValue());
    }

    @Override
    public void invertSpeedInDirection(final double direction) {
        if (Double.compare(getDirection(), direction) == 0) {
            changeDirection(180);

        } else {
            final var normalizedVector = createVector(1, direction);
            final var dotProduct = normalizedVector.dotProduct(motion);

            if (dotProduct > 0) {
                // An actual situation in which the speed should be inverted in the given direction
                final var vectorForDirection = calculateDenormalizedVector(normalizedVector, motion);
                final var newMotion = motion.subtract(vectorForDirection).subtract(vectorForDirection);
                setMotion(newMotion.magnitude(), convertVectorToAngle(newMotion));
            }
        }
    }

    @Override
    public void maximizeMotionInDirection(final Direction direction, final double speed) {
        maximizeMotionInDirection(direction.getValue(), speed);
    }

    @Override
    public void maximizeMotionInDirection(final double direction, final double speed) {
        if (Double.compare(motion.magnitude(), 0) == 0 ||
                Double.compare(getDirection() % 180, direction % 180) == 0) {
            setMotion(speed, direction);
        } else {
            final var maximizedMotion = createVector(speed, direction);
            final var fraction = maximizedMotion.multiply(motion.dotProduct(maximizedMotion) / maximizedMotion.dotProduct(maximizedMotion));
            final var newMotion = motion.subtract(fraction).add(maximizedMotion);
            setMotion(newMotion.magnitude(), convertVectorToAngle(newMotion));
        }
    }

    @Override
    public void nullifySpeedInDirection(final Direction direction) {
        nullifySpeedInDirection(direction.getValue());
    }

    @Override
    public void nullifySpeedInDirection(final double direction) {
        // Direction is same as current direction, so direction can be set to 0
        if (Double.compare(getDirection(), direction) == 0) {
            setSpeed(0D);
        } else {
            final var normalizedVector = createVector(1, direction);
            final var dotProduct = normalizedVector.dotProduct(motion);

            if (dotProduct > 0) {
                // An actual situation in which the motion should be nullified in the given direction
                final var vectorForDirection = calculateDenormalizedVector(normalizedVector, motion);
                final var newMotion = motion.subtract(vectorForDirection);
                setMotion(newMotion.magnitude(), convertVectorToAngle(newMotion));
            }
        }
    }

    private Point2D calculateDenormalizedVector(final Coordinate2D normalizedVector,
                                                final Coordinate2D currentMotion) {
        final var numerator = currentMotion.dotProduct(normalizedVector);
        final var denominator = normalizedVector.dotProduct(normalizedVector);
        return normalizedVector.multiply(numerator / denominator);
    }

    @Override
    public void addToMotion(final double speed, final double direction) {
        motion = motion.add(createVector(speed, direction));
    }


    @Override
    public void setSpeed(final double newSpeed) {
        if (Double.compare(newSpeed, 0d) == 0) {
            if (NON_MOTION.equals(this.motion)) {
                this.direction = Optional.of(Direction.DOWN.getValue());
            } else {
                this.direction = Optional.of(this.motion.angle(IDENTITY_MOTION));
            }
        }

        if (this.motion.equals(new Coordinate2D(0, 0))) {
            this.motion = new Coordinate2D(0, newSpeed);
        } else {
            this.motion = new Coordinate2D((this.motion.normalize().multiply(newSpeed)));
        }

        this.direction.ifPresent(this::setDirection);
    }

    @Override
    public void setDirection(final Direction direction) {
        setDirection(direction.getValue());
    }

    @Override
    public void setDirection(final double direction) {
        if (Double.compare(0, motion.magnitude()) == 0) {
            this.direction = Optional.of(direction);
        } else {
            motion = createVector(motion.magnitude(), direction);
            this.direction = Optional.empty();
        }
    }

    @Override
    public void setFrictionConstant(final double frictionConstant) {
        this.frictionConstant = frictionConstant;
    }

    @Override
    public double getFrictionConstant() {
        return frictionConstant;
    }

    @Override
    public void setGravityConstant(final double gravityConstant) {
        this.gravityConstant = gravityConstant;
    }

    @Override
    public double getGravityConstant() {
        return gravityConstant;
    }

    @Override
    public void setGravityDirection(final double gravityDirection) {
        this.gravityDirection = gravityDirection;
    }

    @Override
    public double getGravityDirection() {
        return gravityDirection;
    }

    @Override
    public double getSpeed() {
        return motion.magnitude();
    }

    @Override
    public void incrementSpeed(final double increment) {
        motion = motion.add(motion.normalize().multiply(increment));
    }

    @Override
    public void multiplySpeed(final double multiplication) {
        motion = new Coordinate2D(motion.multiply(multiplication));
    }

    @Override
    public void changeDirection(final double rotation) {
        final double currentAngle = getDirection();

        setDirection(rotation + currentAngle);
    }

    @Override
    public double getDirection() {
        return direction.orElseGet(() -> convertVectorToAngle(motion));
    }

    private double convertVectorToAngle(final Point2D vector) {
        var currentAngle = vector.angle(IDENTITY_MOTION);

        if (vector.getX() < 0) {
            currentAngle = 360 - currentAngle;
        }

        return currentAngle % 360;
    }

    /**
     * Return the current transformation.
     *
     * @return a {@link Coordinate2D} representing the transformation applied on {@link LocationUpdater#updateLocation(Point2D)}
     */
    public Coordinate2D get() {
        return motion;
    }

    @Override
    public Coordinate2D updateLocation(final Point2D currentLocation) {
        return new Coordinate2D(currentLocation.add(motion));
    }

    private Coordinate2D createVector(final double speed, final double direction) {
        final var angleInRadians = Math.toRadians(direction);
        final var x = Math.sin(angleInRadians);
        final var y = Math.cos(angleInRadians);

        return new Coordinate2D(new Coordinate2D(x, y).multiply(speed));
    }
}
