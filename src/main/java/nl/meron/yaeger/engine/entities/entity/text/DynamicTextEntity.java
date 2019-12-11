package nl.meron.yaeger.engine.entities.entity.text;

import com.google.inject.Inject;
import nl.meron.yaeger.engine.entities.entity.Moveable;
import nl.meron.yaeger.engine.entities.entity.UpdateDelegator;
import nl.meron.yaeger.engine.entities.entity.Updater;
import nl.meron.yaeger.engine.entities.entity.motion.MotionApplier;

public class DynamicTextEntity extends TextEntity implements UpdateDelegator, Moveable {

    private MotionApplier motionApplier;
    private Updater updater;

    @Override
    public void setMotionApplier(MotionApplier motionApplier) {
        this.motionApplier = motionApplier;
    }

    @Override
    public MotionApplier getMotionApplier() {
        return motionApplier;
    }

    @Override
    public MotionApplier multiplySpeed(double multiplication) {
        return null;
    }

    @Override
    public MotionApplier alterSpeed(double increment) {
        return null;
    }

    @Override
    public MotionApplier setSpeed(double newSpeed) {
        return null;
    }

    @Override
    public MotionApplier setDirection(double newDirection) {
        return null;
    }

    @Override
    public MotionApplier changeDirection(double rotation) {
        return null;
    }

    @Override
    public Updater getUpdater() {
        return updater;
    }

    @Inject
    public void setUpdater(Updater updater) {
        this.updater = updater;
    }
}
