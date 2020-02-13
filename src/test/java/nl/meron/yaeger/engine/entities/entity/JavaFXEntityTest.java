package nl.meron.yaeger.engine.entities.entity;

import javafx.scene.Node;
import nl.meron.yaeger.engine.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JavaFXEntityTest {

    private static final Location LOCATION = new Location(37, 37);
    private JavaFXEntity sut;

    @BeforeEach
    void setup() {
        sut = new JavaFXEntityImpl(LOCATION);
    }

    @Test
    void getTimersReturnsAnEmptyCollection() {
        // Setup

        // Test
        List<Timer> timers = sut.getTimers();

        // Verify
        assertNotNull(timers);
        assertTrue(timers.isEmpty());
    }

    private class JavaFXEntityImpl extends JavaFXEntity {

        /**
         * Instantiate a new {@link JavaFXEntity} for the given {@link Location} and textDelegate.
         *
         * @param initialPosition the initial {@link Location} of this {@link JavaFXEntity}
         */
        public JavaFXEntityImpl(Location initialPosition) {
            super(initialPosition);
        }

        @Override
        public void placeOnLocation(double x, double y) {

        }

        @Override
        public Node getGameNode() {
            return null;
        }
    }

}
