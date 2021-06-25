package com.github.hanyaeger.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ActivatableTest {

    @Test
    void activateHasNoSideEffects() {
        // Arrange
        var sut = new ActivatableImpl();

        // Act & Assert
        Assertions.assertDoesNotThrow(sut::activate);
    }

    private static class ActivatableImpl implements Activatable {

        @Override
        public boolean isActivationComplete() {
            return false;
        }
    }
}
