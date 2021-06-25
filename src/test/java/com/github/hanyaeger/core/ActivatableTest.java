package com.github.hanyaeger.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivatableTest {

    @Test
    void activateHasNoSideEffects() {
        // Arrange
        var sut = new ActivatableImpl();

        // Act & Assert
        assertDoesNotThrow(sut::activate);
    }

    @Test
    void isActivationCompleteReturnsFalseByDefault() {
        // Arrange
        var sut = new ActivatableImpl();

        // Act & Assert
        assertFalse(sut.isActivationComplete());
    }

    private static class ActivatableImpl implements Activatable {
    }
}
