package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.annotations.UpdatableProvider;

public interface ContinuousRotatable extends Rotatable {

    /**
     * Return the angle that should be added each update to the rotation of the {@link YaegerEntity}.
     *
     * @return the rotation angle in degrees as a {@code double}
     */
    double getRotationSpeed();

    /**
     * Set the angle at which this {@link YaegerEntity} will bew rotated
     * at each update.
     *
     * @param angle the angle as a {@code double}
     */
    void setRotationSpeed(final double angle);

    @UpdatableProvider
    default Updatable applyRotation() {
        return timestamp -> {
            if (Double.compare(getRotationSpeed(), 0d) != 0) {
                if (getGameNode().isPresent()) {
                    setRotate(getGameNode().get().getRotate() + getRotationSpeed());
                }
            }
        };
    }
}
