package com.github.hanyaeger.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimerTest {

    private TimerImpl sut;

    @BeforeEach
    void setup() {
        sut = new TimerImpl(1000);
    }

    @Test
    void handleDoesNotCallOnAnimationUpdateIfTimestampIsLessThanInterval() {
        // Arrange

        // Act
        sut.handle(500 * 1_000_000);

        // Assert
        assertFalse(sut.updateCalled);
    }

    @Test
    void handleIsCalledOnAnimationUpdateIfTimestampIsMoreThanInterval() {
        // Arrange

        // Act
        sut.handle(1);
        sut.handle(1500 * 1_000_000);

        // Assert
        assertTrue(sut.updateCalled);
    }

    @Test
    void handleIsCalledOnAnimationUpdateIfTimestampIsSameAsInterval() {
        // Arrange

        // Act
        sut.handle(1_000_000);
        sut.handle(1001 * 1_000_000);

        // Assert
        assertTrue(sut.updateCalled);
    }

    @Test
    void handleDoesNotCallOnAnimationUpdateIfTimerIsPaused() {
        // Arrange

        // Act
        sut.handle(1);
        sut.pause();
        sut.handle(1500 * 1_000_000);

        // Assert
        assertFalse(sut.updateCalled);
    }

    @Test
    void handleCallsOnAnimationUpdateIfTimerIsResumed() {
        // Arrange

        // Act
        sut.handle(1);
        sut.pause();
        sut.resume();
        sut.handle(1500 * 1_000_000);

        // Assert
        assertTrue(sut.updateCalled);
    }

    @Test
    void removeMarksTimerAsGarbage() {
        // Arrange

        // Act
        sut.remove();

        // Assert
        assertTrue(sut.isGarbage());
    }

    @Test
    void runningTimerIsActive() {
        // Arrange

        // Act & Assert
        assertTrue(sut.isActive());
    }

    @Test
    void pausedTimerIsNotActive() {
        // Arrange

        // Act
        sut.pause();

        // Assert
        assertFalse(sut.isActive());
    }

    @Test
    void resumedTimerIsActive() {
        // Arrange

        // Act
        sut.pause();
        sut.resume();

        // Assert
        assertTrue(sut.isActive());
    }

    private static class TimerImpl extends Timer {

        private boolean updateCalled = false;

        public TimerImpl(int intervalInMs) {
            super(intervalInMs);
        }

        @Override
        public void onAnimationUpdate(long timestamp) {
            updateCalled = true;
        }
    }
}
