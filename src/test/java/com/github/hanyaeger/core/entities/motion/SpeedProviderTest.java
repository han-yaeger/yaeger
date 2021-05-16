package com.github.hanyaeger.core.entities.motion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpeedProviderTest {

    @Test
    void defaultMethodReturnsZero() {
        // Arrange
        var speedProvider = new SpeedProviderImpl();

        // Act
        double actual = speedProvider.getSpeed();

        // Assert
        assertEquals(0, Double.compare(actual, 0d));
    }

    private class SpeedProviderImpl implements SpeedProvider {
    }
}
