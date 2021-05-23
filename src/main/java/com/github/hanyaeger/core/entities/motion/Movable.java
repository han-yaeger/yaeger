package com.github.hanyaeger.core.entities.motion;

import com.github.hanyaeger.api.entities.Direction;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.core.annotations.UpdatableProvider;
import com.github.hanyaeger.core.entities.Placeable;

/**
 * When the Interface {@link Movable} is implemented, an {@link YaegerEntity}
 * is able to move around the {@link YaegerScene} on each Game World Update.
 * <p>
 * This behaviour required a delegate object, a {@link MotionApplier} that performs all the actual computations
 * of the new location, based on the {@code speed} and {@code direction}.
 */
public interface Movable extends Placeable, MotionModifier {

    /**
     * Set the {@link MotionApplier} that will be used.
     *
     * @param motionApplier an instance of {@link MotionApplier}
     */
    void setMotionApplier(final MotionApplier motionApplier);

    /**
     * Return the {@link MotionApplier} that should be used.
     *
     * @return an instance of {@link MotionApplier}
     */
    MotionApplier getMotionApplier();

    @Override
    default void setMotion(final double speed, final Direction direction) {
        setMotion(speed, direction.getValue());
    }

    @Override
    default void setMotion(final double speed, final double direction) {
        getMotionApplier().setMotion(speed, direction);
    }

    @Override
    default void addToMotion(final double speed, final Direction direction) {
        addToMotion(speed, direction.getValue());
    }

    @Override
    default void addToMotion(final double speed, final double direction) {
        getMotionApplier().addToMotion(speed, direction);
    }

    @Override
    default double getSpeedInDirection(final Direction direction) {
        return getMotionApplier().getSpeedInDirection(direction);
    }

    @Override
    default double getSpeedInDirection(final double direction) {
        return getMotionApplier().getSpeedInDirection(direction);
    }

    @Override
    default void maximizeMotionInDirection(final Direction direction, final double speed) {
        getMotionApplier().maximizeMotionInDirection(direction, speed);
    }

    @Override
    default void maximizeMotionInDirection(final double direction, final double speed) {
        getMotionApplier().maximizeMotionInDirection(direction, speed);
    }

    @Override
    default void nullifySpeedInDirection(final Direction direction) {
        getMotionApplier().nullifySpeedInDirection(direction);
    }

    @Override
    default void nullifySpeedInDirection(final double direction) {
        getMotionApplier().nullifySpeedInDirection(direction);
    }

    @Override
    default void invertSpeedInDirection(final Direction direction) {
        getMotionApplier().invertSpeedInDirection(direction);
    }

    @Override
    default void invertSpeedInDirection(final double direction) {
        getMotionApplier().invertSpeedInDirection(direction);
    }

    @Override
    default void multiplySpeed(final double multiplication) {
        getMotionApplier().multiplySpeed(multiplication);
    }

    @Override
    default void setSpeed(final double newSpeed) {
        getMotionApplier().setSpeed(newSpeed);
    }

    @Override
    default double getSpeed() {
        return getMotionApplier().getSpeed();
    }

    @Override
    default void setDirection(final double newDirection) {
        getMotionApplier().setDirection(newDirection);
    }

    @Override
    default void setDirection(final Direction newDirection) {
        setDirection(newDirection.getValue());
    }

    @Override
    default double getDirection() {
        return getMotionApplier().getDirection();
    }

    @Override
    default void incrementSpeed(final double increment) {
        getMotionApplier().incrementSpeed(increment);
    }

    @Override
    default void changeDirection(final double rotation) {
        getMotionApplier().changeDirection(rotation);
    }

    /**
     * Return the {@link Updatable} to be called on each Game World Update.
     *
     * @return an {@link Updatable} to be called
     */
    @UpdatableProvider(asFirst = true)
    default Updatable updateLocation() {
        return timestamp -> {
            if (Double.compare(getSpeed(), 0D) == 0) {
                return;
            }

            final var loc = getMotionApplier().updateLocation(getAnchorLocation());
            setAnchorLocation(loc);
        };
    }
}
