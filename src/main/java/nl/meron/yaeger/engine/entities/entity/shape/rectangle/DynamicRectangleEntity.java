package nl.meron.yaeger.engine.entities.entity.shape.rectangle;

import com.google.inject.Inject;
import com.google.inject.Injector;
import nl.meron.yaeger.engine.entities.entity.*;
import nl.meron.yaeger.engine.entities.entity.motion.DefaultMotionApplier;
import nl.meron.yaeger.engine.entities.entity.motion.MotionApplier;
import nl.meron.yaeger.engine.entities.entity.motion.Moveable;

/**
 * An {@link DynamicRectangleEntity} extends all behaviour of a {@link RectangleEntity}, but also implements the
 * {@link Updatable} Interface.
 */
public class DynamicRectangleEntity extends RectangleEntity implements UpdateDelegator, Moveable, ContinuousRotatable {

    private DefaultMotionApplier motionApplier;
    private Updater updater;
    private double speed;
    private double direction;
    private double rotationAngle;

    /**
     * Create a new {@link DynamicRectangleEntity} on the given {@code initialPosition}.
     *
     * @param initialPosition The initial position at which this {@link DynamicRectangleEntity} should be placed
     */
    public DynamicRectangleEntity(final Location initialPosition) {
        super(initialPosition);
    }


    @Override
    public MotionApplier getMotionApplier() {
        return motionApplier;
    }

    @Override
    public Updater getUpdater() {
        return updater;
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        if (speed != 0) {
            setMotionTo(speed, direction);
        }
    }

    @Override
    public void setRotationSpeed(final double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    @Override
    public void setSpeedTo(final double newSpeed) {
        var directionToSet = direction;

        if (motionApplier != null) {
            directionToSet = getDirection();
        }
        setMotionTo(newSpeed, directionToSet);
    }

    @Override
    public void setDirectionTo(final double newDirection) {
        var speedToSet = speed;

        if (motionApplier != null) {
            speedToSet = getSpeed();
        }
        setMotionTo(speedToSet, newDirection);
    }

    @Override
    public void setMotionTo(final double speed, final double direction) {
        if (motionApplier != null) {
            motionApplier.setMotionTo(speed, direction);
        } else {
            this.speed = speed;
            this.direction = direction;
        }
    }

    @Override
    public double getRotationSpeed() {
        return rotationAngle;
    }

    @Inject
    @Override
    public void setMotionApplier(final DefaultMotionApplier motionApplier) {
        this.motionApplier = motionApplier;
    }

    @Inject
    public void setUpdater(final Updater updater) {
        this.updater = updater;
    }
}
