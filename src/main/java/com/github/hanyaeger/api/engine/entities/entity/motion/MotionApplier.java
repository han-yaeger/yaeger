package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import javafx.geometry.Point2D;

import java.util.Optional;

/**
 * A {@link MotionApplier} is an implementation of {@link MotionApplier} that does not abide
 * the laws of Physics and only provides basis behaviour regarding speed and direction.
 */
public class MotionApplier implements MotionModifier, NewtonianModifier, LocationUpdater {

    private static final Point2D IDENTITY_MOTION = new Point2D(0, 1);
    private static final Point2D NON_MOTION = new Point2D(0, 0);
    private Optional<Double> direction = Optional.empty();
    private Coordinate2D motion;

    public static final double DEFAULT_GRAVITATIONAL_CONSTANT = 0.2d;
    public static final double DEFAULT_FRICTION_CONSTANT = 0.01d;
    public static final double DEFAULT_GRAVITATIONAL_DIRECTION = Direction.DOWN.getValue();

    private double frictionConstant = DEFAULT_FRICTION_CONSTANT;
    private double gravityConstant = DEFAULT_GRAVITATIONAL_CONSTANT;
    private double gravityDirection = DEFAULT_GRAVITATIONAL_DIRECTION;

    private boolean gravitationalPull = false;

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
    public void addToMotion(final double speed, final double direction) {
        motion = motion.add(createVector(speed, direction));
    }

    @Override
    public void setSpeed(final double newSpeed) {
        if (Double.compare(newSpeed, 0d) == 0) {
            if (NON_MOTION.equals(this.motion)) {
                this.direction = Optional.of(Direction.DOWN.getValue());
            } else {
                this.direction = Optional.of(this.motion.angle(new Point2D(0, 1)));
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
    public void setGravityConstant(double gravityConstant) {
        this.gravityConstant = gravityConstant;
    }

    @Override
    public double getGravityConstant() {
        return gravityConstant;
    }

    @Override
    public void setGravityDirection(double gravityDirection) {
        this.gravityDirection = gravityDirection;
    }

    @Override
    public double getGravityDirection() {
        return gravityDirection;
    }

    @Override
    public void setGravitationalPull(final boolean pull) {
        this.gravitationalPull = pull;
    }

    @Override
    public boolean isGravitationalPull() {
        return gravitationalPull;
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
        double currentAngle = getDirection();

        setDirection(rotation + currentAngle);
    }

    @Override
    public double getDirection() {
        if (direction.isPresent()) {
            return direction.get();
        } else {
            double currentAngle = motion.angle(IDENTITY_MOTION);

            if (motion.getX() < 0) {
                currentAngle = 360 - currentAngle;
            }

            return currentAngle;
        }
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
