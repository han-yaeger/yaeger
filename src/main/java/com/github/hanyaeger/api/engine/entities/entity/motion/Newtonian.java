package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.annotations.UpdatableProvider;

/**
 * A {@link Newtonian} is a special case of a {@link Moveable} that abides the basic laws of gravity
 * and friction. It should be used for DynamicEntities that should also display some physics behaviour.
 * This physics is rather elemental and should not be compared for fully fledged physics.
 */
public interface Newtonian extends Moveable {

    /**
     * Return the {@link MotionApplierType} to be used by this {@link Moveable}. In case of a
     * {@link Newtonian} {@link Moveable}, the {@link MotionApplier} to used is of type
     * {@link MotionApplierType#NEWTONIAN}.
     *
     * @return an instance of {@link MotionApplierType}
     */
    default MotionApplierType getMotionModifierType() {
        return MotionApplierType.NEWTONIAN;
    }

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
