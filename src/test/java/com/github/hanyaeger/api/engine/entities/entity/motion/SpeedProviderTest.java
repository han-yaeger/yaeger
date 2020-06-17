package com.github.hanyaeger.api.engine.entities.entity.motion;

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
        assertTrue(Double.compare(actual, 0d) == 0);
    }

    private class SpeedProviderImpl implements SpeedProvider {
    }
}
