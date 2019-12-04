package nl.meron.yaeger.engine.entities.entity.motion.impl;

import javafx.geometry.Point2D;
import nl.meron.yaeger.engine.entities.entity.motion.MotionApplier;

/**
 * A {@link DefaultMotionApplier} is an implementation of {@link MotionApplier} that does not abide
 * the laws of Physics and only provides basis behaviour regarding speed and direction.
 */
public class DefaultMotionApplier implements MotionApplier {

    private Point2D transformation;

    /**
     * Create a new instance of {@link DefaultMotionApplier} for the given transformation.
     *
     * @param transformation a {@link Point2D} representing the transformation that should be applied
     *                       each update.
     */
    public DefaultMotionApplier(Point2D transformation) {
        this.transformation = transformation;
    }

    @Override
    public MotionApplier alterSpeed(double increment) {
        transformation = transformation.add(transformation.normalize().multiply(increment));
        return this;
    }

    @Override
    public MotionApplier multiplySpeed(double multiplication) {
        transformation = transformation.multiply(multiplication);
        return this;
    }

    @Override
    public MotionApplier setSpeed(double newSpeed) {
        transformation = transformation.normalize().multiply(newSpeed);
        return this;
    }

    @Override
    public MotionApplier setDirection(double angle) {
        var angleInRadians = Math.toRadians(angle);
        var x = Math.sin(angleInRadians);
        var y = Math.cos(angleInRadians);

        transformation = new Point2D(x, y).multiply(transformation.magnitude());
        return this;
    }

    @Override
    public MotionApplier changeDirection(double rotation) {

        double currentAngle = transformation.angle(new Point2D(0, 1));

        return setDirection(rotation + currentAngle);
    }

    @Override
    public Point2D get() {
        return transformation;
    }

    @Override
    public Point2D updateLocation(Point2D currentLocation) {
        return currentLocation.add(transformation);
    }
}
