package com.github.hanyaeger.api.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimerTest {

    @Test
    void handleDoesNotCallOnAnimationUpdateIfTimestampIsLessThanInterval() {
        // Arrange
        var timer = new TimerImpl(1000);

        // Act
        timer.handle(500 * 1_000_000);

        // Assert
        assertFalse(timer.updateCalled);
    }

    @Test
    void handleIsCalledOnAnimationUpdateIfTimestampIsMoreThanInterval() {
        // Arrange
        var timer = new TimerImpl(1000);

        // Act
        timer.handle(1);
        timer.handle(1500 * 1_000_000);

        // Assert
        assertTrue(timer.updateCalled);
    }

    @Test
    void handleIsCalledOnAnimationUpdateIfTimestampIsSameAsInterval() {
        // Arrange
        var timer = new TimerImpl(1000);

        // Act
        timer.handle(1 * 1_000_000);
        timer.handle(1001 * 1_000_000);

        // Assert
        assertTrue(timer.updateCalled);
    }

    @Test
    void handleDoesNotCallOnAnimationUpdateIfTimerIsPaused() {
        // Arrange
        var timer = new TimerTest.TimerImpl(1000);

        // Act
        timer.handle(1);
        timer.pause();
        timer.handle(1500 * 1_000_000);

        // Assert
        assertFalse(timer.updateCalled);
    }

    @Test
    void handleCallsOnAnimationUpdateIfTimerIsResumed() {
        // Arrange
        var timer = new TimerTest.TimerImpl(1000);

        // Act
        timer.handle(1);
        timer.pause();
        timer.resume();
        timer.handle(1500 * 1_000_000);

        // Assert
        assertTrue(timer.updateCalled);
    }

    private class TimerImpl extends Timer {

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
