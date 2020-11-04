package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import javafx.geometry.Point2D;

import java.util.Optional;

/**
 * A {@link DefaultMotionApplier} is an implementation of {@link MotionApplier} that does not abide
 * the laws of Physics and only provides basis behaviour regarding speed and direction.
 */
public class DefaultMotionApplier implements MotionApplier {

    private static final Point2D ZERO_ANGLE_IDENTITY_MOTION = new Point2D(0, 1);
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
    public void setMotionTo(final double speed, final double direction) {
        setSpeedTo(speed);
        setDirectionTo(direction);
    }

    @Override
    public void setSpeedTo(final double newSpeed) {
        hasBeenHalted(newSpeed);

        if (motion.equals(new Coordinate2D(0, 0))) {
            motion = new Coordinate2D(0, newSpeed);
        } else {
            motion = new Coordinate2D((motion.normalize().multiply(newSpeed)));
        }
    }

    @Override
    public void setDirectionTo(final double angle) {
        final var angleInRadians = Math.toRadians(angle);
        final var x = Math.sin(angleInRadians);
        final var y = Math.cos(angleInRadians);

        motion = new Coordinate2D(new Coordinate2D(x, y).multiply(motion.magnitude()));
    }

    @Override
    public double getSpeed() {
        return motion.magnitude();
    }

    @Override
    public void alterSpeedBy(final double increment) {
        motion = motion.add(motion.normalize().multiply(increment));
    }

    @Override
    public void multiplySpeedWith(final double multiplication) {
        motion = new Coordinate2D(motion.multiply(multiplication));
    }

    @Override
    public void changeDirectionBy(final double rotation) {
        double currentAngle = getDirection();

        setDirectionTo(rotation + currentAngle);
    }

    @Override
    public double getDirection() {
        double currentAngle = motion.angle(ZERO_ANGLE_IDENTITY_MOTION);

        if (motion.getX() < 0) {
            currentAngle = 360 - currentAngle;
        }

        return currentAngle;
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
    public void setHalted(boolean halted) {
        this.halted = halted;
    }

    private void hasBeenHalted(double newSpeed) {
        halted = (newSpeed == 0 && motion.magnitude() != 0);
    }
}
