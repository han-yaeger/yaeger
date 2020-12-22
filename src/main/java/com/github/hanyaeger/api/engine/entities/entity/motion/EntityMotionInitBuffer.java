package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.google.inject.Injector;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.Initializable;

import java.util.Optional;

/**
 * An {@link EntityMotionInitBuffer} is used to support the option to set the speed,
 * direction and physics related properties of an {@link YaegerEntity} from the {@code constructor}.
 *
 * <p>The motion of an {@link YaegerEntity} is controlled by and delegated to a
 * {@link MotionModifier}, which gets injected during
 * the initialization phase of an {@link YaegerEntity}. This phase comes after the {@code constructor}
 * is called.
 * </p>
 *
 * <p>It is, however required to set various properties from, or directory after the constructor.
 * This  {@link EntityMotionInitBuffer} therefore stores these properties and delegates them to
 * the {@link MotionModifier} when {@link #init(Injector)} gets called.
 * </p>
 */
public class EntityMotionInitBuffer implements Initializable {

    private Optional<Double> frictionConstant = Optional.empty();
    private Optional<Double> gravityConstant = Optional.empty();
    private Optional<Double> gravityDirection = Optional.empty();
    private Optional<Boolean> gravitationalPull = Optional.of(true);
    private double speed;
    private double direction;
    private MotionApplier motionApplier;

    void setGravityConstant(final double gravityConstant) {
        this.gravityConstant = Optional.of(gravityConstant);
    }

    void setGravityDirection(final double gravityDirection) {
        this.gravityDirection = Optional.of(gravityDirection);
    }

    void setGravitationalPull(final boolean gravitationalPull) {
        this.gravitationalPull = Optional.of(gravitationalPull);
    }

    void setFrictionConstant(final double frictionConstant) {
        this.frictionConstant = Optional.of(frictionConstant);
    }

    void setSpeed(final double newSpeed) {
        speed = newSpeed;
    }

    void setDirection(final double newDirection) {
        direction = newDirection;
    }

    void setMotion(final double newSpeed, final double newDirection) {

        this.speed = newSpeed;
        this.direction = newDirection;
    }

    @Override
    public void init(final Injector injector) {
        motionApplier.setMotion(speed, direction);
        gravitationalPull.ifPresent(pull -> motionApplier.setGravitationalPull(pull));
        gravityConstant.ifPresent(g -> motionApplier.setGravityConstant(g));
        gravityDirection.ifPresent(direction -> motionApplier.setGravityDirection(direction));
        frictionConstant.ifPresent(fC -> motionApplier.setFrictionConstant(fC));
    }

    public void setMotionApplier(final MotionApplier motionApplier) {
        this.motionApplier = motionApplier;
    }
}