package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.google.inject.Injector;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.Initializable;

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

    private double speed;
    private double direction;
    private MotionApplier motionApplier;

    /**
     * Set the speed to the desired value.
     *
     * @param newSpeed the speed as a {@code double}
     */
    public void setSpeedTo(final double newSpeed) {
        speed = newSpeed;
    }

    /**
     * Set the direction to the desired value.
     *
     * @param newDirection the direction as a {@code double}
     */
    public void setDirectionTo(final double newDirection) {
        direction = newDirection;
    }

    /**
     * Set the speed and direction to the desired values.
     *
     * @param newSpeed     the speed as a {@code double}
     * @param newDirection the direction as a {@code double}
     */
    public void setMotionTo(final double newSpeed, final double newDirection) {

        this.speed = newSpeed;
        this.direction = newDirection;
    }

    @Override
    public void init(Injector injector) {
        motionApplier.setMotion(speed, direction);
    }

    public void setMotionApplier(final MotionApplier motionApplier) {
        this.motionApplier = motionApplier;
    }
}
