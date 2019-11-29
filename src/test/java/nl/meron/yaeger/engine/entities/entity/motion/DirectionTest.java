package nl.meron.yaeger.engine.entities.entity.motion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void valueOfForUp() {
        // Setup
        var directionNumeric = 0d;

        // Test
        var direction = Direction.valueOf(directionNumeric);

        // Verify
        assertEquals(Direction.UP, direction);
    }

    @Test
    void valueOfForRight() {
        // Setup
        var directionNumeric = 90d;

        // Test
        var direction = Direction.valueOf(directionNumeric);

        // Verify
        assertEquals(Direction.RIGHT, direction);
    }

    @Test
    void valueOfForDown() {
        // Setup
        var directionNumeric = 180d;

        // Test
        var direction = Direction.valueOf(directionNumeric);

        // Verify
        assertEquals(Direction.DOWN, direction);
    }

    @Test
    void valueOfForLeft() {
        // Setup
        var directionNumeric = 270d;

        // Test
        var direction = Direction.valueOf(directionNumeric);

        // Verify
        assertEquals(Direction.LEFT, direction);
    }

    @Test
    void getValueForUp() {
        // Setup
        var direction = Direction.UP;

        // Test
        var directionNumeric = direction.getValue();

        // Verify
        assertEquals(0d, directionNumeric);
    }

    @Test
    void getValueForRight() {
        // Setup
        var direction = Direction.RIGHT;

        // Test
        var directionNumeric = direction.getValue();

        // Verify
        assertEquals(90d, directionNumeric);
    }

    @Test
    void getValueForDown() {
        // Setup
        var direction = Direction.DOWN;

        // Test
        var directionNumeric = direction.getValue();

        // Verify
        assertEquals(180d, directionNumeric);
    }

    @Test
    void getValueForLeft() {
        // Setup
        var direction = Direction.LEFT;

        // Test
        var directionNumeric = direction.getValue();

        // Verify
        assertEquals(270d, directionNumeric);
    }
}
