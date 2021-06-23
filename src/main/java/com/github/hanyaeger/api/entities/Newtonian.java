package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.core.entities.motion.BufferedMovable;
import com.github.hanyaeger.core.entities.motion.Movable;
import com.github.hanyaeger.core.entities.motion.NewtonianModifier;
import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.core.annotations.UpdatableProvider;

/**
 * A {@link Newtonian} is a special case of a {@link Movable} that abides the basic laws of gravity
 * and friction. It should be used for Dynamic Entities that should also display some physics behaviour.
 * This physics is rather elemental and should not be compared for fully fledged physics.
 */
public interface Newtonian extends BufferedMovable, NewtonianModifier {

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

    /**
     * Return an {@link Updatable} that applies simple rules of physics on each call
     * to {@link Updatable#update(long)}.
     *
     * @return an instance of {@link Updatable}
     */
    @UpdatableProvider
    default Updatable addSimplePhysics() {
        return timestamp -> {
            addToMotion(getMotionApplier().getGravityConstant(), getMotionApplier().getGravityDirection());
            if (getSpeed() > 0) {
                incrementSpeed((-1 * getFrictionConstant()) * getSpeed());
            }
        };
    }
}
