package nl.meron.yaeger.engine.entities.entity.motion;

import javafx.geometry.Point2D;
import nl.meron.yaeger.engine.entities.entity.Placeable;
import nl.meron.yaeger.engine.Updatable;
import nl.meron.yaeger.engine.annotations.UpdatableProvider;

public interface Moveable extends Placeable, MotionModifier {

    /**
     * Set the {@link DefaultMotionApplier} that will be used.
     *
     * @param motionApplier an instance of {@link DefaultMotionApplier}
     */
    void setMotionApplier(final DefaultMotionApplier motionApplier);

    /**
     * Return the {@link MotionApplier} that should be used.
     *
     * @return an instance of {@link MotionApplier}
     */
    MotionApplier getMotionApplier();

    @Override
    default void setMotionTo(final double speed, final double direction) {
        getMotionApplier().setMotionTo(speed, direction);
    }

    @Override
    default void multiplySpeedWith(final double multiplication) {
        getMotionApplier().multiplySpeedWith(multiplication);
    }

    @Override
    default void setSpeedTo(final double newSpeed) {
        getMotionApplier().setSpeedTo(newSpeed);
    }

    @Override
    default double getSpeed() {
        return getMotionApplier().getSpeed();
    }

    @Override
    default void setDirectionTo(final double newDirection) {
        getMotionApplier().setDirectionTo(newDirection);
    }

    @Override
    default double getDirection() {
        return getMotionApplier().getDirection();
    }

    @Override
    default void alterSpeedBy(final double increment) {
        getMotionApplier().alterSpeedBy(increment);
    }

    @Override
    default void changeDirectionBy(final double rotation) {
        getMotionApplier().changeDirectionBy(rotation);
    }

    @UpdatableProvider(asFirst = true)
    default Updatable updateLocation() {
        return timestamp -> {
            if (Double.compare(getSpeed(), 0d) == 0) {
                return;
            }
            var currentPosition = new Point2D(getX(), getY());
            var updatedPosition = getMotionApplier().updateLocation(currentPosition);
            setX(updatedPosition.getX());
            setY(updatedPosition.getY());
        };
    }
}
