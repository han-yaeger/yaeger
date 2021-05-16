package com.github.hanyaeger.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateExposerTest {

    @Test
    void callingUpdatableFromUpdateProviderCallsExplicitUpdate() {
        // Arrange
        var sut = new UpdateExposerImpl();

        // Act
        var updatable = sut.exposedUpdate();
        updatable.update(0L);

        // Assert
        assertTrue(sut.called);
    }

    private static class UpdateExposerImpl implements UpdateExposer {

        boolean called = false;

        @Override
        public void explicitUpdate(long timestamp) {
            called = true;
        }
    }
}
