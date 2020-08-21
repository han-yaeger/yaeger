package com.github.hanyaeger.api.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateExposerTest {

    @Test
    void callingUpdatableFromUpdateProviderCallsExpliciteUpdate() {
        // Arrange
        var sut = new UpdateExposerImpl();

        // Act
        var updatable = sut.exposedUpdate();
        updatable.update(0l);

        // Assert
        assertTrue(sut.called);
    }

    private class UpdateExposerImpl implements UpdateExposer {

        boolean called = false;

        @Override
        public void explicitUpdate(long timestamp) {
            called = true;
        }
    }
}
