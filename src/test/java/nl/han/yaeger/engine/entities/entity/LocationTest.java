package nl.han.yaeger.engine.entities.entity;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void addingPointWorksAsExpected() {
        // Arrange
        var position = new Location(0d, 0d);
        var positionToAdd = new Location(1d, 1d);

        // Act
        var addedPosition = position.add(positionToAdd);

        // Assert
        assertEquals(1d, addedPosition.getX());
        assertEquals(1d, addedPosition.getY());
    }

    @Test
    void addingPoint2DWorksAsExpected() {
        // Arrange
        var position = new Location(0d, 0d);
        var positionToAdd = new Point2D(1d, 1d);

        // Act
        var addedPosition = position.add(positionToAdd);

        // Assert
        assertEquals(1d, addedPosition.getX());
        assertEquals(1d, addedPosition.getY());
    }
}
