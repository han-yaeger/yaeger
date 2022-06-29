package com.github.hanyaeger.core.entities.motion;

import com.github.hanyaeger.api.entities.Direction;
import com.github.hanyaeger.api.entities.YaegerEntity;

import java.util.Optional;

/**
 * A {@link BufferedMovable} extends the interface {@link Movable} and support the use of a buffer.
 * With dependency injection, a {@link MotionApplier} is injected into a {@link YaegerEntity}.
 * The main problem is, however, that this injection takes place after the constructor has been called.
 * Hence, it would not be possible to set the speed, direction and physics related properties of a {@link YaegerEntity}
 * from the constructor.
 * <p>
 * This problem is resolved by introduction of a {@link EntityMotionInitBuffer}, which buffers the values set before the
 * dependencies are injected.
 */
public interface BufferedMovable extends Movable {

    @Override
    default void setSpeed(final double newSpeed) {
        getBuffer().ifPresentOrElse(entityMotionInitBuffer -> entityMotionInitBuffer.setSpeed(newSpeed), () -> getMotionApplier().setSpeed(newSpeed));
    }

    @Override
    default void setDirection(final Direction newDirection) {
        setDirection(newDirection.getValue());
    }

    @Override
    default void setDirection(final double newDirection) {
        getBuffer().ifPresentOrElse(entityMotionInitBuffer -> entityMotionInitBuffer.setDirection(newDirection), () -> getMotionApplier().setDirection(newDirection));
    }

    @Override
    default void setMotion(final double speed, final double direction) {
        getBuffer().ifPresentOrElse(entityMotionInitBuffer -> entityMotionInitBuffer.setMotion(speed, direction), () -> getMotionApplier().setMotion(speed, direction));
    }

    @Override
    default void setMotion(final double speed, final Direction direction) {
        setMotion(speed, direction.getValue());
    }

    /**
     * Return the {@link EntityMotionInitBuffer} encapsulated in an {@link Optional} to
     * be used by this {@link BufferedMovable}.
     *
     * @return an {@link EntityMotionInitBuffer} encapsulated in an {@link Optional}
     */
    Optional<EntityMotionInitBuffer> getBuffer();

    /**
     * Return the {@link MotionApplier} to be used by this {@link BufferedMovable}.
     *
     * @return an instance of {@link MotionApplier}
     */
    MotionApplier getMotionApplier();
}
