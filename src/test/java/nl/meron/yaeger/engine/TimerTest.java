package nl.meron.yaeger.engine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimerTest {


    @Test
    void handleDoesNotCallOnAnimationUpdateIfTimestampIsLessThanInterval() {
        // Setup
        var timer = new TimerImpl(1000);

        // Test
        timer.handle(500 * 1_000_000);

        // Verify
        Assertions.assertFalse(timer.updateCalled);
    }

    @Test
    void handleIsCalledOnAnimationUpdateIfTimestampIsMoreThanInterval() {
        // Setup
        var timer = new TimerImpl(1000);

        // Test
        timer.handle(1500 * 1_000_000);

        // Verify
        Assertions.assertTrue(timer.updateCalled);
    }

    @Test
    void handleIsCalledOnAnimationUpdateIfTimestampIsSameAsInterval() {
        // Setup
        var timer = new TimerImpl(1000);

        // Test
        timer.handle(1000 * 1_000_000);

        // Verify
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

        public boolean isUpdateCalled() {
            return updateCalled;
        }
    }
}
