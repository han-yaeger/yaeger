package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.core.annotations.UpdatableProvider;

/**
 * When implementing this Interface, a {@link YaegerEntity} will acquire the behaviour to
 * perform a rotation on each Game World update, and thus give the illusion of a continuous
 * rotation.
 */
public interface ContinuousRotatable extends Rotatable {

    /**
     * Return the angle that should be added each update to the rotation of the {@link YaegerEntity}.
     *
     * @return the rotation angle in degrees as a {@code double}
     */
    double getRotationSpeed();

    /**
     * Set the angle at which this {@link YaegerEntity} will bew rotated
     * at each update. Note that a positive rotation value rotates the
     * {@link YaegerEntity} counter clockwise, and a negative value rotates
     * the {@link YaegerEntity} clockwise.
     *
     * @param angle the angle as a {@code double}
     */
    void setRotationSpeed(final double angle);

    /**
     * Return an {@link Updatable} that applies the rotation on each {@link Updatable#update(long)}.
     *
     * @return an {@link Updatable}
     */
    @UpdatableProvider
    default Updatable applyRotation() {
        return timestamp ->
                getNode().ifPresent(node -> {
                    if (Double.compare(getRotationSpeed(), 0d) != 0) {
                        setRotate(-node.getRotate() + getRotationSpeed());
                    }
                });
    }
}
