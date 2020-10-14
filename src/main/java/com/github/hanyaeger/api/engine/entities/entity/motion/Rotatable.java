package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.entities.entity.GameNode;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;

/**
 * Implementing the interface {@link Rotatable} will result in de availability of the {@link Rotatable#setRotate(double)}
 * method.
 */
public interface Rotatable extends GameNode {

    /**
     * Set the rotation of the {@link YaegerEntity} to the specified value. Note that a positive rotation value
     * rotates the {@link YaegerEntity} counter clockwise, and a negative value rotates the {@link YaegerEntity}
     * clockwise.
     *
     * @param degrees The rotation in degrees as a {@code double}.
     */
    default void setRotate(final double degrees) {
        getNode().ifPresentOrElse(node -> node.setRotate(-degrees), () -> getRotationBuffer().setRotation(degrees));
    }

    /**
     * Return an instance of {@link RotationBuffer} to be used whenever a {@link javafx.scene.Node} is unavailable
     * to apply the rotation.
     *
     * @return An instance of {@link RotationBuffer}.
     */
    RotationBuffer getRotationBuffer();
}
