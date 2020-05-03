package nl.han.yaeger.engine;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class UpdateDelegatorTest {

    private final long TIMESTAMP = 0l;

    @Test
    void updateGetsDelegated() {
        // Arrange
        var updateDelegator = new UpdateDelegatorImpl();
        var updater = mock(Updater.class);
        updateDelegator.setUpdater(updater);

        // Act
        updateDelegator.update(TIMESTAMP);

        // Assert
        verify(updater).update(TIMESTAMP);
    }

    private class UpdateDelegatorImpl implements UpdateDelegator {

        private Updater updater;

        @Override
        public Updater getUpdater() {
            return updater;
        }

        public void setUpdater(Updater updater) {
            this.updater = updater;
        }
    }
}


