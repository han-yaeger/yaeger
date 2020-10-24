package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.annotations.UpdatableProvider;
import com.github.hanyaeger.api.engine.entities.entity.motion.Rotatable;

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

    @UpdatableProvider
    default Updatable applyRotation() {
        return timestamp -> {
            if (Double.compare(getRotationSpeed(), 0d) != 0) {
                if (getNode().isPresent()) {
                    setRotate(-getNode().get().getRotate() + getRotationSpeed());
                }
            }
        };
    }
}
