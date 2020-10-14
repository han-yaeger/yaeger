package com.github.hanyaeger.api.engine.entities.entity.motion;

/**
 * A {@link RotationBuffer} is a simple data container, only used for storing the value of rotate, in
 * case it is set before a {@link javafx.scene.Node} is available.
 */
public class RotationBuffer {
    private double rotation;

    /**
     * Return the value of {@code rotation}.
     *
     * @return The value of {@code rotation} as a {@code double}.
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * Set the value of {@code rotation}.
     *
     * @param rotation The value of {@code rotation} as a {@code double}.
     */
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
