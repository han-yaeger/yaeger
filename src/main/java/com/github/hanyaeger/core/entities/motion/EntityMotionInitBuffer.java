package com.github.hanyaeger.core.entities.motion;

import com.google.inject.Injector;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.Initializable;

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
 * <p>It is, however, required to set various properties from, or directly after the constructor has been called.
 * This  {@link EntityMotionInitBuffer} therefore stores these properties and delegates them to
 * the {@link MotionModifier} when {@link #init(Injector)} gets called.
 * </p>
 */
public class EntityMotionInitBuffer implements Initializable {

    private Optional<Double> frictionConstant = Optional.empty();
    private Optional<Double> gravityConstant = Optional.empty();
    private Optional<Double> gravityDirection = Optional.empty();
    private double speed;
    private double direction;
    private MotionApplier motionApplier;

    /**
     * Set the gravity constant to be used.
     *
     * @param gravityConstant the gravity contant as a {@code double}
     */
    public void setGravityConstant(final double gravityConstant) {
        this.gravityConstant = Optional.of(gravityConstant);
    }

    /**
     * Set the gravity direction to be used.
     *
     * @param gravityDirection the gravity direction as a {@code double}
     */
    public void setGravityDirection(final double gravityDirection) {
        this.gravityDirection = Optional.of(gravityDirection);
    }

    /**
     * Set the friction constant to be used.
     *
     * @param frictionConstant the friction contant as a {@code double}
     */
    public void setFrictionConstant(final double frictionConstant) {
        this.frictionConstant = Optional.of(frictionConstant);
    }

    /**
     * Set the speed to be used.
     *
     * @param newSpeed the speed as a {@code double}
     */
    void setSpeed(final double newSpeed) {
        speed = newSpeed;
    }

    /**
     * Set the direction to be used.
     *
     * @param newDirection the direction as a {@code double}
     */
    void setDirection(final double newDirection) {
        direction = newDirection;
    }

    /**
     * Set the motion (speed and direction) to be used.
     *
     * @param newSpeed     the speed as a {@code double}
     * @param newDirection the direction as a {@code double}
     */
    void setMotion(final double newSpeed, final double newDirection) {

        this.speed = newSpeed;
        this.direction = newDirection;
    }

    @Override
    public void init(final Injector injector) {
        motionApplier.setMotion(speed, direction);
        gravityConstant.ifPresent(g -> motionApplier.setGravityConstant(g));
        gravityDirection.ifPresent(d -> motionApplier.setGravityDirection(d));
        frictionConstant.ifPresent(fC -> motionApplier.setFrictionConstant(fC));
    }

    /**
     * Set the {@link MotionApplier} to be used when this buffer is initialized.
     *
     * @param motionApplier the {@link MotionApplier} to be used.
     */
    public void setMotionApplier(final MotionApplier motionApplier) {
        this.motionApplier = motionApplier;
    }
}
