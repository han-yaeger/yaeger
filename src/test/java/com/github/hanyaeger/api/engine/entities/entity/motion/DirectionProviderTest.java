package com.github.hanyaeger.api.engine.entities.entity.motion;

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
        assertTrue(Double.compare(actual, 0d) == 0);

    }

    private class DirectionProviderImpl implements DirectionProvider {
    }

}
