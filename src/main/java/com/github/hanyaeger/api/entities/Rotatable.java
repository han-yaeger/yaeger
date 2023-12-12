package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.core.entities.GameNode;
import com.github.hanyaeger.core.entities.motion.InitializationBuffer;
import javafx.scene.Node;

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
     * Return the rotation of the {@link YaegerEntity}. This will be a value in degrees and will range
     * between 0 and 360.
     *
     * @return the rotation in degrees as a {@code double}
     */
    default double getRotation() {
        return absoluteAndModulo360(getNode().map(Node::getRotate).orElse(getInitializationBuffer().getRotation()));
    }

    private double absoluteAndModulo360(final double value) {
        return Math.abs(value % 360);
    }

    /**
     * Return an instance of {@link InitializationBuffer} to be used whenever a {@link javafx.scene.Node} is unavailable
     * to apply the rotation.
     *
     * @return An instance of {@link InitializationBuffer}.
     */
    InitializationBuffer getInitializationBuffer();
}
