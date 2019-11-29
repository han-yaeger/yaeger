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
    public void alterSpeed(double increment) {
        transformation = transformation.add(transformation.normalize().multiply(increment));
    }

    @Override
    public void multiplySpeed(double multiplication) {
        transformation = transformation.multiply(multiplication);
    }

    @Override
    public void setSpeed(double newSpeed) {
        transformation = transformation.normalize().multiply(newSpeed);
    }

    @Override
    public void setDirection(double angle) {
        var angleInRadians = Math.toRadians(angle);
        var x = Math.sin(angleInRadians);
        var y = Math.cos(angleInRadians);

        transformation = new Point2D(x, y).multiply(transformation.magnitude());
    }

    @Override
    public void changeDirection(double rotation) {

    }

    @Override
    public Point2D updateLocation(Point2D currentLocation) {
        return currentLocation.add(transformation);
    }
}
