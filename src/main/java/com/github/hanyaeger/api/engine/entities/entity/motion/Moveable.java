package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.annotations.UpdatableProvider;
import com.github.hanyaeger.api.engine.entities.entity.Placeable;
import javafx.geometry.Point2D;

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
            var currentPosition = new Point2D(getOriginX(), getOriginY());
            var updatedPosition = getMotionApplier().updateLocation(currentPosition);
            setOriginX(updatedPosition.getX());
            setOriginY(updatedPosition.getY());
        };
    }
}
