package com.github.hanyaeger.api.engine.entities.entity.motion;

import javafx.geometry.Point2D;

/**
 * A {@link DefaultMotionApplier} is an implementation of {@link MotionApplier} that does not abide
 * the laws of Physics and only provides basis behaviour regarding speed and direction.
 */
public class DefaultMotionApplier implements MotionApplier {

    private static final Point2D ZERO_ANGLE_IDENTITY_MOTION = new Point2D(0, 1);
    private Point2D transformation;
    private Point2D previousLocation;

    /**
     * Create a new instance of {@link DefaultMotionApplier}.
     */
    public DefaultMotionApplier() {
        transformation = new Point2D(0, 0);
    }

    @Override
    public void setMotionTo(final double speed, final double direction) {
        transformation = new Point2D(0, speed);
        setDirectionTo(direction);
    }

    @Override
    public void setSpeedTo(final double newSpeed) {
        transformation = transformation.normalize().multiply(newSpeed);
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
        transformation = transformation.multiply(multiplication);
    }

    @Override
    public void setDirectionTo(final double angle) {
        final var angleInRadians = Math.toRadians(angle);
        final var x = Math.sin(angleInRadians);
        final var y = Math.cos(angleInRadians);

        transformation = new Point2D(x, y).multiply(transformation.magnitude());
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
    public Point2D get() {
        return transformation;
    }

    @Override
    public Point2D updateLocation(final Point2D currentLocation) {
        previousLocation = new Point2D(currentLocation.getX(), currentLocation.getY());
        return currentLocation.add(transformation);
    }

    @Override
    public Point2D getPreviousLocation() {
        return previousLocation;
    }
}
