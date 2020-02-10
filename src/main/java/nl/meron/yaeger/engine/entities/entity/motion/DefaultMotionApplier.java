package nl.meron.yaeger.engine.entities.entity.motion;

import javafx.geometry.Point2D;

/**
 * A {@link DefaultMotionApplier} is an implementation of {@link MotionApplier} that does not abide
 * the laws of Physics and only provides basis behaviour regarding speed and direction.
 */
public class DefaultMotionApplier implements MotionApplier {

    private static final Point2D ZERO_ANGLE_IDENTITY_MOTION = new Point2D(0, 1);
    private Point2D transformation;

    /**
     * Create a new instance of {@link DefaultMotionApplier}.
     */
    public DefaultMotionApplier() {
        transformation = new Point2D(0, 0);
    }

    @Override
    public MotionApplier setMotionTo(final double speed, final double direction) {
        transformation = new Point2D(0, speed);
        return setDirectionTo(direction);
    }

    @Override
    public MotionApplier setSpeedTo(final double newSpeed) {
        transformation = transformation.normalize().multiply(newSpeed);
        return this;
    }

    @Override
    public double getSpeed() {
        return transformation.magnitude();
    }

    @Override
    public MotionApplier alterSpeedBy(final double increment) {
        transformation = transformation.add(transformation.normalize().multiply(increment));
        return this;
    }

    @Override
    public MotionApplier multiplySpeedWith(final double multiplication) {
        transformation = transformation.multiply(multiplication);
        return this;
    }

    @Override
    public MotionApplier setDirectionTo(final double angle) {
        final var angleInRadians = Math.toRadians(angle);
        final var x = Math.sin(angleInRadians);
        final var y = Math.cos(angleInRadians);

        transformation = new Point2D(x, y).multiply(transformation.magnitude());
        return this;
    }

    @Override
    public MotionApplier changeDirectionBy(final double rotation) {
        double currentAngle = getDirection();

        return setDirectionTo(rotation + currentAngle);
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
        return currentLocation.add(transformation);
    }
}
