package nl.meron.yaeger.engine.entities.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void addingPositionsWorksAsExpected() {
        // Setup
        var position = new Point(0d, 0d);
        var positionToAdd = new Point(1d, 1d);

        // Test
        var addedPosition = position.add(positionToAdd);

        // Verify
        assertEquals(1d, addedPosition.getX());
        assertEquals(1d, addedPosition.getY());
    }
}
