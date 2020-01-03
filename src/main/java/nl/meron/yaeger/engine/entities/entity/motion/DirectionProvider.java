package nl.meron.yaeger.engine.entities.entity.motion;

/**
 * A {@link DirectionProvider} exposes a method to acquire the direction.
 */
public interface DirectionProvider {

    /**
     * Get the direction in which the {@link nl.meron.yaeger.engine.entities.entity.Entity} is moving,
     * in degrees. If the {@link nl.meron.yaeger.engine.entities.entity.Entity} is not moving, and therefore
     * has no direction, this method will return {@code NaN}.
     *
     * @return The direction in degrees as a {@code double} or {@code NaN}
     */
    double getDirection();
}
