package nl.meron.yaeger.engine.entities.entity.shapebased.shape;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.shape.Shape;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.UpdateDelegator;
import nl.meron.yaeger.engine.entities.entity.Updater;
import nl.meron.yaeger.engine.entities.entity.motion.DefaultMotionApplier;
import nl.meron.yaeger.engine.entities.entity.motion.MotionApplier;
import nl.meron.yaeger.engine.entities.entity.motion.Moveable;

public abstract class DynamicShapeEntity extends ShapeEntity implements UpdateDelegator, Moveable {

    private DefaultMotionApplier motionApplier;
    private Updater updater;
    private double speed;
    private double direction;

    /**
     * Create a new {@link DynamicShapeEntity} with a given initial position. The actual {@link Shape}
     * that this {@link Entity} is based on should be provided
     * in the {@link DynamicShapeEntity#provideShape()} method.
     *
     * @param initialPosition The initial position at which this {@link Entity}
     *                        should be placed.
     */
    public DynamicShapeEntity(Point initialPosition) {
        super(initialPosition);
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        if (motionApplier != null && speed != 0 && direction != 0) {
            motionApplier.setMotionTo(speed, direction);
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

    @Override
    public MotionApplier getMotionApplier() {
        return motionApplier;
    }

    @Override
    public Updater getUpdater() {
        return updater;
    }

    @Inject
    public void setUpdater(final Updater updater) {
        this.updater = updater;
    }

    @Inject
    @Override
    public void setMotionApplier(DefaultMotionApplier motionApplier) {
        this.motionApplier = motionApplier;
    }

}
