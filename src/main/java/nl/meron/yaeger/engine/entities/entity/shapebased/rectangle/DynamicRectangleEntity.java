package nl.meron.yaeger.engine.entities.entity.shapebased.rectangle;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.SceneBorderTouchingWatcher;
import nl.meron.yaeger.engine.entities.entity.UpdateDelegator;
import nl.meron.yaeger.engine.entities.entity.Updater;
import nl.meron.yaeger.engine.entities.entity.motion.DefaultMotionApplier;
import nl.meron.yaeger.engine.entities.entity.motion.MotionApplier;
import nl.meron.yaeger.engine.entities.entity.motion.Moveable;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class DynamicRectangleEntity extends RectangleEntity implements UpdateDelegator, Moveable {

    private DefaultMotionApplier motionApplier;
    private Updater updater;
    private double speed;
    private double direction;

    public DynamicRectangleEntity(Point initialPosition) {
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
