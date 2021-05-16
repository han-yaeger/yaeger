package com.github.hanyaeger.core.entities.motion;

import com.github.hanyaeger.api.entities.YaegerEntity;

/**
 * A {@link DirectionProvider} exposes a method to acquire the direction.
 */
public interface DirectionProvider {

    /**
     * Get the direction in which the {@link YaegerEntity} is moving,
     * in degrees. If the {@link YaegerEntity} is not moving, and therefore
     * has no direction, this method will return {@code NaN}.
     *
     * @return The direction in degrees as a {@code double} or {@code NaN}
     */
    default double getDirection(){
        return 0;
    }
}
