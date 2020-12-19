package com.github.hanyaeger.api.engine.entities.entity.motion;

import java.util.Optional;

/**
 * A {@link BufferedMoveable} extends the interface {@link Moveable} and support the use of a buffer.
 * With dependency injection, a {@link MotionApplier} is injected into a {@link com.github.hanyaeger.api.engine.entities.entity.YaegerEntity}.
 * The main problem is, however, that this injection takes place after the constructor has been called.
 * Hence it would not be possible to set the speed, direction and physics related properties of a {@link com.github.hanyaeger.api.engine.entities.entity.YaegerEntity}
 * from the constructor.
 * <p>
 * This problem is resolved by introduction of a {@link EntityMotionInitBuffer}, which buffers the values set before the
 * dependencies are injected.
 */
public interface BufferedMoveable extends Moveable {

    @Override
    default void setSpeed(final double newSpeed) {
        getBuffer().ifPresentOrElse(eMBuffer -> eMBuffer.setSpeed(newSpeed), () -> getMotionApplier().setSpeed(newSpeed));
    }

    @Override
    default void setDirection(final Direction newDirection) {
        setDirection(newDirection.getValue());
    }

    @Override
    default void setDirection(final double newDirection) {
        getBuffer().ifPresentOrElse(eMBuffer -> eMBuffer.setDirection(newDirection), () -> getMotionApplier().setDirection(newDirection));
    }

    @Override
    default void setMotion(final double speed, final double direction) {
        getBuffer().ifPresentOrElse(eMBuffer -> eMBuffer.setMotion(speed, direction), () -> getMotionApplier().setMotion(speed, direction));
    }

    @Override
    default void setMotion(final double speed, final Direction direction) {
        setMotion(speed, direction.getValue());
    }

    /**
     * Return the {@link EntityMotionInitBuffer} encapsulated in an {@link Optional} to
     * be used by this {@link BufferedMoveable}.
     *
     * @return an {@link EntityMotionInitBuffer} encapsulated in an {@link Optional}
     */
    Optional<EntityMotionInitBuffer> getBuffer();

    /**
     * Return the {@link MotionApplier} to be used by this {@link BufferedMoveable}.
     *
     * @return an instance of {@link MotionApplier}
     */
    MotionApplier getMotionApplier();
}
