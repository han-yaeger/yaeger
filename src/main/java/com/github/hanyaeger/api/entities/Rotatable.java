package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.core.entities.GameNode;
import com.github.hanyaeger.core.entities.motion.InitializationBuffer;

/**
 * Implementing this interface will result in de availability of the {@link Rotatable#setRotate(double)}
 * method.
 */
public interface Rotatable extends GameNode {

    /**
     * Set the rotation of the {@link YaegerEntity} to the specified value. Note that a positive rotation value
     * rotates the {@link YaegerEntity} counterclockwise, and a negative value rotates the {@link YaegerEntity}
     * clockwise.
     *
     * @param degrees The rotation in degrees as a {@code double}.
     */
    default void setRotate(final double degrees) {
        getNode().ifPresentOrElse(node -> node.setRotate(-degrees), () -> getInitializationBuffer().setRotation(degrees));
    }

    /**
     * Return an instance of {@link InitializationBuffer} to be used whenever a {@link javafx.scene.Node} is unavailable
     * to apply the rotation.
     *
     * @return An instance of {@link InitializationBuffer}.
     */
    InitializationBuffer getInitializationBuffer();
}
