package com.github.hanyaeger.api.engine.entities.entity.motion;

import java.util.Optional;

public interface BufferedMoveable extends Moveable {

    @Override
    default void setSpeed(final double newSpeed) {
        getBuffer().ifPresentOrElse(eMBuffer -> eMBuffer.setSpeedTo(newSpeed), () -> getMotionApplier().setSpeed(newSpeed));
    }

    @Override
    default void setDirection(final Direction newDirection) {
        setDirection(newDirection.getValue());
    }

    @Override
    default void setDirection(final double newDirection) {
        getBuffer().ifPresentOrElse(eMBuffer -> eMBuffer.setDirectionTo(newDirection), () -> getMotionApplier().setDirection(newDirection));
    }

    @Override
    default void setMotion(final double speed, final double direction) {
        getBuffer().ifPresentOrElse(eMBuffer -> eMBuffer.setMotionTo(speed, direction), () -> getMotionApplier().setMotion(speed, direction));
    }

    @Override
    default void setMotion(final double speed, final Direction direction) {
        setMotion(speed, direction.getValue());
    }

    Optional<EntityMotionInitBuffer> getBuffer();

    MotionApplier getMotionApplier();
}
