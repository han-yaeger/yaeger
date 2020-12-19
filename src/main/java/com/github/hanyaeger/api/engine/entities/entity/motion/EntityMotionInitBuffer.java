package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.google.inject.Injector;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.Initializable;

import java.util.Optional;

/**
 * An {@link EntityMotionInitBuffer} is used to support the option to set the speed and
 * direction of an {@link YaegerEntity} from the {@code constructor}.
 *
 * <p>The motion of an {@link YaegerEntity} is controlled by and delegated to a
 * {@link MotionModifier} and this
 * {@link MotionModifier} is injected during
 * the initialization phase of an {@link YaegerEntity}. This phase comes after the {@code constructor}
 * is called.
 * </p>
 *
 * <p>An {@link EntityMotionInitBuffer} therefor stores the speed and direction of an {@link YaegerEntity}
 * and delegates the value to the {@link MotionModifier}
 *
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

    /**
     * TODO UNITTEST
     *
     * @param gravityConstant
     */
    void setGravityConstant(final double gravityConstant) {
        this.frictionConstant = Optional.of(gravityConstant);
    }

    /**
     * TODO UNITTEST
     *
     * @param gravityDirection
     */
    void setGravityDirection(final double gravityDirection) {
        this.gravityDirection = Optional.of(gravityDirection);
    }

    /**
     * TODO UNITTEST
     *
     * @param gravitationalPull
     */
    void setGravitationalPull(final boolean gravitationalPull) {
        this.gravitationalPull = Optional.of(gravitationalPull);
    }

    /**
     * TODO UNITTEST
     *
     * @param frictionConstant
     */
    void setFrictionConstant(final double frictionConstant) {
        this.frictionConstant = Optional.of(frictionConstant);
    }

    /**
     * Set the speed to the desired value.
     *
     * @param newSpeed the speed as a {@code double}
     */
    public void setSpeed(final double newSpeed) {
        speed = newSpeed;
    }

    /**
     * Set the direction to the desired value.
     *
     * @param newDirection the direction as a {@code double}
     */
    public void setDirection(final double newDirection) {
        direction = newDirection;
    }

    /**
     * Set the speed and direction to the desired values.
     *
     * @param newSpeed     the speed as a {@code double}
     * @param newDirection the direction as a {@code double}
     */
    public void setMotion(final double newSpeed, final double newDirection) {

        this.speed = newSpeed;
        this.direction = newDirection;
    }

    @Override
    public void init(Injector injector) {
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
