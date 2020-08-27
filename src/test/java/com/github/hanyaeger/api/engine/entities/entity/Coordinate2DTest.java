package com.github.hanyaeger.api.engine.entities.entity;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Coordinate2DTest {

    @Test
    void addingPointWorksAsExpected() {
        // Arrange
        var position = new Coordinate2D(0d, 0d);
        var positionToAdd = new Coordinate2D(1d, 1d);

        // Act
        var addedPosition = position.add(positionToAdd);

        // Assert
        assertEquals(1d, addedPosition.getX());
        assertEquals(1d, addedPosition.getY());
    }

    @Test
    void addingPoint2DWorksAsExpected() {
        // Arrange
        var position = new Coordinate2D(0d, 0d);
        var positionToAdd = new Point2D(1d, 1d);

        // Act
        var addedPosition = position.add(positionToAdd);

        // Assert
        assertEquals(1d, addedPosition.getX());
        assertEquals(1d, addedPosition.getY());
    }
}
