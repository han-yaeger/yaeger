package com.github.hanyaeger.api.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void valueOfForUp() {
        // Arrange
        var directionNumeric = 180d;

        // Act
        var direction = Direction.valueOf(directionNumeric);

        // Assert
        assertEquals(Direction.UP, direction);
    }

    @Test
    void valueOfForRight() {
        // Arrange
        var directionNumeric = 90d;

        // Act
        var direction = Direction.valueOf(directionNumeric);

        // Assert
        assertEquals(Direction.RIGHT, direction);
    }

    @Test
    void valueOfForDown() {
        // Arrange
        var directionNumeric = 0d;

        // Act
        var direction = Direction.valueOf(directionNumeric);

        // Assert
        assertEquals(Direction.DOWN, direction);
    }

    @Test
    void valueOfForLeft() {
        // Arrange
        var directionNumeric = 270d;

        // Act
        var direction = Direction.valueOf(directionNumeric);

        // Assert
        assertEquals(Direction.LEFT, direction);
    }

    @Test
    void valueOfForDownRight() {
    	// Arrange
        var directionNumeric = 45d;

        // Act
        var direction = Direction.valueOf(directionNumeric);

        // Assert
        assertEquals(Direction.DOWN_RIGHT, direction);
    }
    
    @Test
    void valueOfForUpRight() {
    	// Arrange
        var directionNumeric = 135d;

        // Act
        var direction = Direction.valueOf(directionNumeric);

        // Assert
        assertEquals(Direction.UP_RIGHT, direction);
    }
    
    @Test
    void valueOfForUpLeft() {
    	// Arrange
        var directionNumeric = 225d;

        // Act
        var direction = Direction.valueOf(directionNumeric);

        // Assert
        assertEquals(Direction.UP_LEFT, direction);
    }
    
    @Test
    void valueOfForDownLeft() {
    	// Arrange
        var directionNumeric = 315d;

        // Act
        var direction = Direction.valueOf(directionNumeric);

        // Assert
        assertEquals(Direction.DOWN_LEFT, direction);
    }
    
    @Test
    void getValueForUp() {
        // Arrange
        var direction = Direction.UP;

        // Act
        var directionNumeric = direction.getValue();

        // Assert
        assertEquals(180d, directionNumeric);
    }

    @Test
    void getValueForRight() {
        // Arrange
        var direction = Direction.RIGHT;

        // Act
        var directionNumeric = direction.getValue();

        // Assert
        assertEquals(90d, directionNumeric);
    }

    @Test
    void getValueForDown() {
        // Arrange
        var direction = Direction.DOWN;

        // Act
        var directionNumeric = direction.getValue();

        // Assert
        assertEquals(0d, directionNumeric);
    }

    @Test
    void getValueForLeft() {
        // Arrange
        var direction = Direction.LEFT;

        // Act
        var directionNumeric = direction.getValue();

        // Assert
        assertEquals(270d, directionNumeric);
    }
    
    @Test
    void getValueForDownRight() {
        // Arrange
        var direction = Direction.DOWN_RIGHT;

        // Act
        var directionNumeric = direction.getValue();

        // Assert
        assertEquals(45d, directionNumeric);
    }
    
    @Test
    void getValueForUpRight() {
        // Arrange
        var direction = Direction.UP_RIGHT;

        // Act
        var directionNumeric = direction.getValue();

        // Assert
        assertEquals(135d, directionNumeric);
    }
    
    @Test
    void getValueForUpLeft() {
        // Arrange
        var direction = Direction.UP_LEFT;

        // Act
        var directionNumeric = direction.getValue();

        // Assert
        assertEquals(225d, directionNumeric);
    }
    
    @Test
    void getValueForDownLeft() {
        // Arrange
        var direction = Direction.DOWN_LEFT;

        // Act
        var directionNumeric = direction.getValue();

        // Assert
        assertEquals(315d, directionNumeric);
    }
}
