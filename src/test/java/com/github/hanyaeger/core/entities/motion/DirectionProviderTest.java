package com.github.hanyaeger.core.entities.motion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionProviderTest {

    @Test
    void defaultMethodReturnsZero() {
        // Arrange
        var directionProvider = new DirectionProviderImpl();

        // Act
        double actual = directionProvider.getDirection();

        // Assert
        assertEquals(0, Double.compare(actual, 0d));

    }

    private class DirectionProviderImpl implements DirectionProvider {
    }

}
