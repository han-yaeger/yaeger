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
    private Coordinate2D transformation;
    private Optional<Coordinate2D> previousLocation = Optional.empty();

    /**
     * Create a new instance of {@link DefaultMotionApplier}.
     */
    public DefaultMotionApplier() {
        transformation = new Coordinate2D(0, 0);
    }

    @Override
    public void setMotionTo(final double speed, final double direction) {
        transformation = new Coordinate2D(0, speed);
        setDirectionTo(direction);
    }

    @Override
    public void setSpeedTo(final double newSpeed) {
        transformation = new Coordinate2D((transformation.normalize().multiply(newSpeed)));
    }

    @Override
    public double getSpeed() {
        return transformation.magnitude();
    }

    @Override
    public void alterSpeedBy(final double increment) {
        transformation = transformation.add(transformation.normalize().multiply(increment));
    }

    @Override
    public void multiplySpeedWith(final double multiplication) {
        transformation = new Coordinate2D(transformation.multiply(multiplication));
    }

    @Override
    public void setDirectionTo(final double angle) {
        final var angleInRadians = Math.toRadians(angle);
        final var x = Math.sin(angleInRadians);
        final var y = Math.cos(angleInRadians);

        transformation = new Coordinate2D(new Coordinate2D(x, y).multiply(transformation.magnitude()));
    }

    @Override
    public void changeDirectionBy(final double rotation) {
        double currentAngle = getDirection();

        setDirectionTo(rotation + currentAngle);
    }

    @Override
    public double getDirection() {
        double currentAngle = transformation.angle(ZERO_ANGLE_IDENTITY_MOTION);

        if (transformation.getX() < 0) {
            currentAngle = 360 - currentAngle;
        }

        return currentAngle;
    }

    @Override
    public Coordinate2D get() {
        return transformation;
    }

    @Override
    public Coordinate2D updateLocation(final Point2D currentLocation) {
        previousLocation = Optional.of(new Coordinate2D(currentLocation.getX(), currentLocation.getY()));
        return new Coordinate2D(currentLocation.add(transformation));
    }

    @Override
    public Optional<Coordinate2D> getPreviousLocation() {
        return previousLocation;
    }
}
