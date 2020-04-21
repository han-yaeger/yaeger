package nl.han.yaeger.engine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TimerTest {

    @Test
    void handleDoesNotCallOnAnimationUpdateIfTimestampIsLessThanInterval() {
        // Arrange
        var timer = new TimerImpl(1000);

        // Act
        timer.handle(500 * 1_000_000);

        // Assert
        Assertions.assertFalse(timer.updateCalled);
    }

    @Test
    void handleIsCalledOnAnimationUpdateIfTimestampIsMoreThanInterval() {
        // Arrange
        var timer = new TimerImpl(1000);

        // Act
        timer.handle(1);
        timer.handle(1500 * 1_000_000);

        // Assert
        Assertions.assertTrue(timer.updateCalled);
    }

    @Test
    void handleIsCalledOnAnimationUpdateIfTimestampIsSameAsInterval() {
        // Arrange
        var timer = new TimerImpl(1000);

        // Act
        timer.handle(1 * 1_000_000);
        timer.handle(1001 * 1_000_000);

        // Assert
        Assertions.assertTrue(timer.updateCalled);
    }

    private class TimerImpl extends Timer {

        private boolean updateCalled = false;

        /**
         * Create a new instance of {@link Timer} for the given interval in milliseconds.
         *
         * @param intervalInMs The interval in milleseconds.
         */
        public TimerImpl(int intervalInMs) {
            super(intervalInMs);
        }

        @Override
        public void onAnimationUpdate(long timestamp) {
            updateCalled = true;
        }
    }
}
