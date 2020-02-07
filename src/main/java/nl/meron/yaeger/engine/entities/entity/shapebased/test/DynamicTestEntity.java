package nl.meron.yaeger.engine.entities.entity.shapebased.test;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.geometry.Point2D;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.UpdateDelegator;
import nl.meron.yaeger.engine.entities.entity.Updater;
import nl.meron.yaeger.engine.entities.entity.motion.DefaultMotionApplier;
import nl.meron.yaeger.engine.entities.entity.motion.MotionApplier;
import nl.meron.yaeger.engine.entities.entity.motion.Moveable;
import nl.meron.yaeger.engine.entities.entity.shapebased.text.TextEntity;

public abstract class DynamicTestEntity extends TestEntity implements UpdateDelegator, Moveable {

    private DefaultMotionApplier motionApplier;
    private Updater updater;
    private double speed;
    private double direction;

    /**
     * Instantiate a new {@link DynamicTestEntity} for the given {@link Point}.
     *
     * @param initialPosition the initial {@link Point} of this {@link DynamicTestEntity}
     */
    public DynamicTestEntity(final Point initialPosition) {
        this(initialPosition, "");
    }

    /**
     * Instantiate a new {@link DynamicTestEntity} for the given {@link Point2D} and textDelegate.
     *
     * @param initialPosition the initial {@link Point} of this {@link DynamicTestEntity}
     * @param text            a {@link String} containing the initial textDelegate to be displayed
     */
    public DynamicTestEntity(final Point initialPosition, final String text) {
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
