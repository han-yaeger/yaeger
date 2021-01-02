package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.annotations.UpdatableProvider;
import com.github.hanyaeger.api.engine.entities.entity.Placeable;

/**
 * When the Interface {@link Moveable} is implemented, an {@link com.github.hanyaeger.api.engine.entities.entity.YaegerEntity}
 * is able to move around the {@link com.github.hanyaeger.api.engine.scenes.YaegerScene} on each Game World Update.
 * <p>
 * This behaviour required a delegate object, a {@link MotionApplier} that performs all the actual computations
 * of the new location, based on the {@code speed} and {@code direction}.
 */
public interface Moveable extends Placeable, MotionModifier {

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

    @UpdatableProvider(asFirst = true)
    default Updatable updateLocation() {
        return timestamp -> {
            if (Double.compare(getSpeed(), 0D) == 0) {
                return;
            }

            var loc = getMotionApplier().updateLocation(getAnchorLocation());
            setAnchorLocation(loc);
        };
    }
}
