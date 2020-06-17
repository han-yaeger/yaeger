package com.github.hanyaeger.api.engine.entities.entity.motion;

/**
 * A {@link SpeedProvider} exposes a method to acquire the speed.
 */
public interface SpeedProvider {

    /**
     * Return the current speed as a {@code double}.
     *
     * @return The speed as a  {@code double}
     */
    default double getSpeed() {
        return 0;
    }
}
