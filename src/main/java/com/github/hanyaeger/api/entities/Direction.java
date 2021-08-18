package com.github.hanyaeger.api.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Convenience enum for setting the {@link Direction}.
 */
public enum Direction {

    /**
     * The downward direction, of which the numeric value is 0 degrees.
     */
    DOWN(0d),
    /**
     * The direction to the right, of which the numeric value is 90 degrees.
     */
    RIGHT(90d),
    /**
     * The upward direction, of which the numeric value is 180 degrees.
     */
    UP(180d),
    /**
     * The direction to the left, of which the numeric value is 270 degrees.
     */
    LEFT(270d);

    private final double value;
    private static final Map<Double, Direction> map = new HashMap<>();

    Direction(final double value) {
        this.value = value;
    }

    static {
        for (final var direction : Direction.values()) {
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
