package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A {@link DefaultMotionApplier} is an implementation of {@link MotionApplier} that does not abide
 * the laws of Physics and only provides basis behaviour regarding speed and direction.
 */
public class DefaultMotionApplier implements MotionApplier {

    private static final Point2D ZERO_ANGLE_IDENTITY_MOTION = new Point2D(0, 1);
    private Optional<Double> direction = Optional.empty();
    private Coordinate2D motion;
    private Optional<Coordinate2D> previousLocation = Optional.empty();
    private boolean halted = false;

    /**
     * Create a new instance of {@link DefaultMotionApplier}.
     */
    public DefaultMotionApplier() {
        motion = new Coordinate2D();
    }

    @Override
    public void setMotion(final double speed, final double direction) {
        setSpeed(speed);
        setDirection(direction);
    }

    @Override
    public void setMotion(double speed, Direction direction) {
        setMotion(speed, direction.getValue());
    }

    @Override
    public void setSpeed(final double newSpeed) {
        hasBeenHalted(newSpeed);

        if (Double.compare(newSpeed, 0d) == 0) {
            this.direction = Optional.of(motion.angle(new Point2D(0, 1)));
        }

        if (motion.equals(new Coordinate2D(0, 0))) {
            motion = new Coordinate2D(0, newSpeed);
        } else {
            motion = new Coordinate2D((motion.normalize().multiply(newSpeed)));
        }

        direction.ifPresent(this::setDirection);
    }

    @Override
    public void setDirection(Direction direction) {
        setDirection(direction.getValue());
    }

    @Override
    public void setDirection(final double direction) {
        if (Double.compare(0, motion.magnitude()) == 0) {
            this.direction = Optional.of(direction);
        } else {
            final var angleInRadians = Math.toRadians(direction);
            final var x = Math.sin(angleInRadians);
            final var y = Math.cos(angleInRadians);

            motion = new Coordinate2D(new Coordinate2D(x, y).multiply(motion.magnitude()));
            this.direction = Optional.empty();
        }
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
            double currentAngle = motion.angle(ZERO_ANGLE_IDENTITY_MOTION);

            if (motion.getX() < 0) {
                currentAngle = 360 - currentAngle;
            }

            return currentAngle;
        }
    }

    @Override
    public Coordinate2D get() {
        return motion;
    }

    @Override
    public Coordinate2D updateLocation(final Point2D currentLocation) {
        previousLocation = Optional.of(new Coordinate2D(currentLocation.getX(), currentLocation.getY()));
        return new Coordinate2D(currentLocation.add(motion));
    }

    @Override
    public Optional<Coordinate2D> getPreviousLocation() {
        return previousLocation;
    }

    @Override
    public boolean isHalted() {
        return halted;
    }

    @Override
    public void setHalted(final boolean halted) {
        this.halted = halted;
    }

    private void hasBeenHalted(final double newSpeed) {
        halted = (newSpeed == 0 && motion.magnitude() != 0);
    }
}
