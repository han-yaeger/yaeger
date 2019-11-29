package nl.meron.yaeger.engine.entities.entity.motion;

import java.util.HashMap;
import java.util.Map;

/**
 * Convenience enum for setting the {@link Direction}
 */
public enum Direction {

    UP(0d),
    RIGHT(90d),
    DOWN(180d),
    LEFT(270d);

    private double value;
    private static Map<Double, Direction> map = new HashMap();

    Direction(double value) {
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
    public static Direction valueOf(double direction) {
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
