package nl.meron.yaeger.engine.entities.entity.motion;

import nl.meron.yaeger.engine.Configurable;
import nl.meron.yaeger.engine.entities.entity.Placeable;
import nl.meron.yaeger.engine.entities.entity.Updatable;
import nl.meron.yaeger.engine.entities.entity.UpdatableProvider;

public interface Moveable extends Placeable, MotionModifier, Configurable {

    /**
     * Set the {@link DefaultMotionApplier} that will be used.
     *
     * @param motionApplier an instance of {@link DefaultMotionApplier}
     */
    void setMotionApplier(DefaultMotionApplier motionApplier);

    /**
     * Return the {@link MotionApplier} that should be used.
     *
     * @return an instance of {@link MotionApplier}
     */
    MotionApplier getMotionApplier();

    @Override
    default MotionApplier setMotion(double speed, double direction) {
        return getMotionApplier().setMotion(speed, direction);
    }

    @Override
    default MotionApplier multiplySpeed(double multiplication) {
        return getMotionApplier().multiplySpeed(multiplication);
    }

    @Override
    default MotionApplier setSpeed(double newSpeed) {
        return getMotionApplier().setSpeed(newSpeed);
    }

    @Override
    default double getSpeed() {
        return getMotionApplier().getSpeed();
    }

    @Override
    default MotionApplier setDirection(double newDirection) {
        return getMotionApplier().setDirection(newDirection);
    }

    @Override
    default double getDirection() {
        return getMotionApplier().getDirection();
    }

    @Override
    default MotionApplier alterSpeed(double increment) {
        return getMotionApplier().alterSpeed(increment);
    }

    @Override
    default MotionApplier changeDirection(double rotation) {
        return getMotionApplier().changeDirection(rotation);
    }

    @UpdatableProvider(asFirst = true)
    default Updatable updateLocation() {
        return timestamp -> {
            var currentPosition = getPosition();
            var updatedPosition = getMotionApplier().updateLocation(currentPosition);
            placeOnPosition(updatedPosition);
        };
    }
}
