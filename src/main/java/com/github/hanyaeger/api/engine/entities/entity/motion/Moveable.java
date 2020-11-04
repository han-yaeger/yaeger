package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.annotations.UpdatableProvider;
import com.github.hanyaeger.api.engine.entities.entity.Placeable;

/**
 * When the Interface {@link Moveable} is implemented, an {@link com.github.hanyaeger.api.engine.entities.entity.YaegerEntity}
 * is able to move around the {@link com.github.hanyaeger.api.engine.scenes.YaegerScene} on each Game World Update.
 * <p>
 * This behaviour required a delegate object, a {@link DefaultMotionApplier} that performs all the actual computations
 * of the new location, based on the {@code speed} and {@code direction}.
 */
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
            getMotionApplier().setHalted(false); // TODO unittest
            if (Double.compare(getSpeed(), 0d) == 0) {
                return;
            }
            setAnchorLocation(getMotionApplier().updateLocation(getAnchorLocation()));
        };
    }

    default void undoUpdate() {
        if (getMotionApplier().isHalted() && Double.compare(getSpeed(), 0) == 0 && getMotionApplier().getPreviousLocation().isPresent()) {
            setAnchorLocation(getMotionApplier().getPreviousLocation().get());
        }
    }
}
