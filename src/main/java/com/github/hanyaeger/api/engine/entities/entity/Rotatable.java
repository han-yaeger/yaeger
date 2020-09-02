package com.github.hanyaeger.api.engine.entities.entity;

/**
 * Implementing the interface {@link Rotatable} will result in de availability of the {@link Rotatable#setRotate(double)}
 * method.
 */
public interface Rotatable extends NodeProvider {

    /**
     * Set the rotation of the {@link YaegerEntity} to the specified value. Note that a positive rotation value
     * rotates the {@link YaegerEntity} counter clockwise, and a negative value rotates the {@link YaegerEntity}
     * clockwise.
     *
     * @param degrees The rotation in degrees as a {@code double}.
     */
    default void setRotate(final double degrees) {
        getGameNode().ifPresent(node -> node.setRotate(-degrees));
    }
}
