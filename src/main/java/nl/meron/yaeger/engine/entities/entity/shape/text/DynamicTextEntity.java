package nl.meron.yaeger.engine.entities.entity.shape.text;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.geometry.Point2D;
import nl.meron.yaeger.engine.entities.entity.*;
import nl.meron.yaeger.engine.entities.entity.motion.DefaultMotionApplier;
import nl.meron.yaeger.engine.entities.entity.motion.Moveable;
import nl.meron.yaeger.engine.entities.entity.motion.MotionApplier;

/**
 * An {@link DynamicTextEntity} extends all behaviour of a {@link TextEntity}, but also implements the
 * {@link Updatable} Interface.
 */
public abstract class DynamicTextEntity extends TextEntity implements UpdateDelegator, Moveable, ContinuousRotatable {

    private DefaultMotionApplier motionApplier;
    private Updater updater;
    private double speed;
    private double direction;
    private double rotationAngle;

    /**
     * Instantiate a new {@link DynamicTextEntity} for the given {@link Location}.
     *
     * @param initialPosition the initial {@link Location} of this {@link DynamicTextEntity}
     */
    public DynamicTextEntity(final Location initialPosition) {
        this(initialPosition, "");
    }

    /**
     * Instantiate a new {@link DynamicTextEntity} for the given {@link Point2D} and textDelegate.
     *
     * @param initialPosition the initial {@link Location} of this {@link DynamicTextEntity}
     * @param text            a {@link String} containing the initial textDelegate to be displayed
     */
    public DynamicTextEntity(final Location initialPosition, final String text) {
        super(initialPosition, text);
    }

    @Override
    public MotionApplier getMotionApplier() {
        return motionApplier;
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
    public double getRotationSpeed() {
        return rotationAngle;
    }

    @Override
    public Updater getUpdater() {
        return updater;
    }

    @Override
    public MotionApplier setSpeedTo(final double newSpeed) {
        if (motionApplier != null) {
            return motionApplier.setSpeedTo(newSpeed);
        } else {
            this.speed = newSpeed;
            return null;
        }
    }

    @Override
    public MotionApplier setDirectionTo(final double newDirection) {
        if (motionApplier != null) {
            return motionApplier.setDirectionTo(newDirection);
        } else {
            this.direction = newDirection;
            return null;
        }
    }

    @Override
    public MotionApplier setMotionTo(final double speed, final double direction) {
        this.speed = speed;
        this.direction = direction;

        if (motionApplier != null) {
            return motionApplier.setMotionTo(speed, direction);
        } else {
            return null;
        }
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
