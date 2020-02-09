package nl.meron.yaeger.engine.entities.entity.motion;

import nl.meron.yaeger.engine.entities.entity.Placeable;
import nl.meron.yaeger.engine.entities.entity.Updatable;
import nl.meron.yaeger.engine.entities.entity.UpdatableProvider;

public interface Moveable extends Placeable, MotionModifier {

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
    default MotionApplier setMotionTo(double speed, double direction) {
        return getMotionApplier().setMotionTo(speed, direction);
    }

    @Override
    default MotionApplier multiplySpeedWith(double multiplication) {
        return getMotionApplier().multiplySpeedWith(multiplication);
    }

    @Override
    default MotionApplier setSpeedTo(double newSpeed) {
        return getMotionApplier().setSpeedTo(newSpeed);
    }

    @Override
    default double getSpeed() {
        return getMotionApplier().getSpeed();
    }

    @Override
    default MotionApplier setDirectionTo(double newDirection) {
        return getMotionApplier().setDirectionTo(newDirection);
    }

    @Override
    default double getDirection() {
        return getMotionApplier().getDirection();
    }

    @Override
    default MotionApplier alterSpeedBy(double increment) {
        return getMotionApplier().alterSpeedBy(increment);
    }

    @Override
    default MotionApplier changeDirectionBy(double rotation) {
        return getMotionApplier().changeDirectionBy(rotation);
    }

    @UpdatableProvider(asFirst = true)
    default Updatable updateLocation() {
        return timestamp -> {
            if (Double.compare(getSpeed(), 0d) == 0) {
                return;
            }
            var currentPosition = getLocation();
            var updatedPosition = getMotionApplier().updateLocation(currentPosition);
            placeOnLocation(updatedPosition.getX(), updatedPosition.getY());
        };
    }
}
