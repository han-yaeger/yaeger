package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.annotations.UpdatableProvider;

/**
 * A {@link Newtonian} is a special case of a {@link Moveable} that abides the basic laws of gravity
 * and friction. It should be used for Dynamic Entities that should also display some physics behaviour.
 * This physics is rather elemental and should not be compared for fully fledged physics.
 */
public interface Newtonian extends BufferedMoveable, NewtonianModifier {

    default boolean isGravitationalPull() {
        return getMotionApplier().isGravitationalPull();
    }

    @Override
    default void setFrictionConstant(final double frictionConstant) {
        getBuffer().ifPresentOrElse(eMBuffer -> eMBuffer.setFrictionConstant(frictionConstant), () -> getMotionApplier().setFrictionConstant(frictionConstant));
    }

    @Override
    default void setGravityConstant(final double gravityConstant) {
        getBuffer().ifPresentOrElse(eMBuffer -> eMBuffer.setGravityConstant(gravityConstant), () -> getMotionApplier().setGravityConstant(gravityConstant));
    }

    @Override
    default void setGravityDirection(final double gravityDirection) {
        getBuffer().ifPresentOrElse(eMBuffer -> eMBuffer.setGravityDirection(gravityDirection), () -> getMotionApplier().setGravityDirection(gravityDirection));
    }

    @Override
    default void setGravitationalPull(final boolean pull) {
        getBuffer().ifPresentOrElse(eMBuffer -> eMBuffer.setGravitationalPull(pull), () -> getMotionApplier().setGravitationalPull(pull));
    }

    @Override
    default double getGravityConstant() {
        return getMotionApplier().getGravityConstant();
    }


    @Override
    default double getGravityDirection() {
        return getMotionApplier().getGravityDirection();
    }

    @Override
    default double getFrictionConstant() {
        return getMotionApplier().getFrictionConstant();
    }

    @UpdatableProvider
    default Updatable addSimplePhysics() {
        return timestamp -> {
            if (getMotionApplier().isGravitationalPull()) {
                addToMotion(getMotionApplier().getGravityConstant(), getMotionApplier().getGravityDirection());
            }
            if (getSpeed() > 0) {
                incrementSpeed((-1 * getFrictionConstant()) * getSpeed());
            }
        };
    }
}
