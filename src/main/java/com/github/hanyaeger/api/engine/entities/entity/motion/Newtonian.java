package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.annotations.UpdatableProvider;

/**
 * A {@link Newtonian} is a special case of a {@link Moveable} that abides the basic laws of gravity
 * and friction. It should be used for DynamicEntities that should also display some physics behaviour.
 * This physics is rather elemental and should not be compared for fully fledged physics.
 */
public interface Newtonian extends Moveable {

    default void setGravitationalPull(final boolean pull) {
        getMotionApplier().setGravitationalPull(pull);
    }

    default boolean isGravitationalPull() {
        return getMotionApplier().isGravitationalPull();
    }

    @UpdatableProvider
    default Updatable addGravitationalPull() {
        return timestamp -> {
            if (getMotionApplier().isGravitationalPull()) {
                getMotionApplier().addToMotion(NewtonianMotionApplier.DEFAULT_GRAVITATIONAL_CONSTANT, NewtonianMotionApplier.DEFAULT_GRAVITATIONAL_DIRECTION);
            }
        };
    }

}
