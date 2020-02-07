package nl.meron.yaeger.engine.entities.entity.shape.text;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.geometry.Point2D;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.Updatable;
import nl.meron.yaeger.engine.entities.entity.motion.DefaultMotionApplier;
import nl.meron.yaeger.engine.entities.entity.motion.Moveable;
import nl.meron.yaeger.engine.entities.entity.UpdateDelegator;
import nl.meron.yaeger.engine.entities.entity.Updater;
import nl.meron.yaeger.engine.entities.entity.motion.MotionApplier;

/**
 * An {@link DynamicTextEntity} extends all behaviour of a {@link TextEntity}, but also implements the
 * {@link Updatable} Interface.
 */
public abstract class DynamicTextEntity extends TextEntity implements UpdateDelegator, Moveable {

    private DefaultMotionApplier motionApplier;
    private Updater updater;
    private double speed;
    private double direction;

    /**
     * Instantiate a new {@link DynamicTextEntity}.
     */
    public DynamicTextEntity() {
        this(new Point(0, 0));
    }

    /**
     * Instantiate a new {@link DynamicTextEntity} for the given {@link Point}.
     *
     * @param initialPosition the initial {@link Point} of this {@link DynamicTextEntity}
     */
    public DynamicTextEntity(final Point initialPosition) {
        this(initialPosition, "");
    }

    /**
     * Instantiate a new {@link DynamicTextEntity} for the given {@link Point2D} and textDelegate.
     *
     * @param initialPosition the initial {@link Point} of this {@link DynamicTextEntity}
     * @param text            a {@link String} containing the initial textDelegate to be displayed
     */
    public DynamicTextEntity(final Point initialPosition, final String text) {
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
    public Updater getUpdater() {
        return updater;
    }

    @Override
    public MotionApplier setSpeedTo(double newSpeed) {
        if (motionApplier != null) {
            return motionApplier.setSpeedTo(newSpeed);
        } else {
            this.speed = newSpeed;
            return null;
        }
    }

    @Override
    public MotionApplier setDirectionTo(double newDirection) {
        if (motionApplier != null) {
            return motionApplier.setDirectionTo(newDirection);
        } else {
            this.direction = newDirection;
            return null;
        }
    }

    @Override
    public MotionApplier setMotionTo(double speed, double direction) {
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
