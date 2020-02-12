package nl.meron.yaeger.engine.entities.entity.motion;

import java.util.Optional;

public interface BufferedMoveable extends Moveable {

    @Override
    default void setSpeedTo(final double newSpeed) {
        getBuffer().ifPresentOrElse(eMBuffer -> eMBuffer.setSpeedTo(newSpeed), () -> getMotionApplier().setSpeedTo(newSpeed));
    }

    @Override
    default void setDirectionTo(final double newDirection) {
        getBuffer().ifPresentOrElse(eMBuffer -> eMBuffer.setDirectionTo(newDirection), () -> getMotionApplier().setDirectionTo(newDirection));
    }

    @Override
    default void setMotionTo(final double speed, final double direction) {
        getBuffer().ifPresentOrElse(eMBuffer -> eMBuffer.setMotionTo(speed, direction), () -> getMotionApplier().setMotionTo(speed, direction));
    }

    Optional<EntityMotionInitBuffer> getBuffer();

    MotionApplier getMotionApplier();
}
