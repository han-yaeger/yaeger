package nl.meron.yaeger.engine.entities.entity;

import nl.meron.yaeger.engine.annotations.UpdatableProvider;

public interface ContinuousRotatable extends Rotatable {

    /**
     * Return the angle that should be added each update to the rotation of the {@link Entity}.
     *
     * @return the rotation angle in degrees as a {@code double}
     */
    double getRotationSpeed();

    /**
     * Set the angle at which this {@link nl.meron.yaeger.engine.entities.entity.Entity} will bew rotated
     * at each update.
     *
     * @param angle the angle as a {@code double}
     */
    void setRotationSpeed(final double angle);

    @UpdatableProvider
    default Updatable applyRotation() {
        return timestamp -> {
            if (Double.compare(getRotationSpeed(), 0d) != 0) {
                setRotate(getGameNode().getRotate() + getRotationSpeed());
            }

        };
    }

}
