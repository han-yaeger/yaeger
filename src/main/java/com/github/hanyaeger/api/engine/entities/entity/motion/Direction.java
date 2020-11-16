package com.github.hanyaeger.api.engine.entities.entity.motion;

import java.util.HashMap;
import java.util.Map;

/**
 * Convenience enum for setting the {@link Direction}.
 */
public enum Direction {

    DOWN(0d),
    RIGHT(90d),
    UP(180d),
    LEFT(270d);

    private final double value;
    private static final Map<Double, Direction> map = new HashMap<>();

    Direction(final double value) {
        this.value = value;
    }

    static {
        for (Direction direction : Direction.values()) {
            map.put(direction.value, direction);
        }
    }

    /**
     * Return the enumeration of type {@link Direction} for the given numeric value.
     *
     * @param direction the {@code double} value of the {@link Direction}
     * @return The {@link Direction} that represents the numeric value
     */
    public static Direction valueOf(final double direction) {
        return map.get(direction);
    }

    /**
     * Return the numeric value of this enumeration.
     *
     * @return the numeric value of this {@link Direction}
     */
    public double getValue() {
        return value;
    }
}
