package nl.meron.yaeger.engine.entities.entity;

import com.google.inject.Injector;
import javafx.scene.Node;
import nl.meron.yaeger.engine.Timer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JavaFXEntityTest {

    private static final Location LOCATION = new Location(37, 37);
    private JavaFXEntityImpl sut;
    private Node node;
    private Injector injector;

    @BeforeEach
    void setup() {
        sut = new JavaFXEntityImpl(LOCATION);
        injector = mock(Injector.class);
        node = mock(Node.class, withSettings().withoutAnnotations());
        sut.setNode(node);
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

    @Test
    void initCallsSetVisible() {
        // Setup

        // Test
        sut.init(injector);

        // Verify
        verify(node).setVisible(true);
    }

    @Test
    void initCallsPlaceOnLocation() {
        // Setup

        // Test
        sut.init(injector);

        // Verify
        Assertions.assertEquals(0, Double.compare(LOCATION.getX(), sut.getX()));
        Assertions.assertEquals(0, Double.compare(LOCATION.getY(), sut.getY()));
    }

    @Test
    void setVisibleDelegatesToNode() {
        // Setup

        // Test
        sut.setVisible(false);

        // Verify
        verify(node).setVisible(false);
    }

    @Test
    void removeCallsSetVisibleFalseOnGameNode() {
        // Setup

        // Test
        sut.remove();

        // Verify
        verify(node).setVisible(false);
    }

    private class JavaFXEntityImpl extends JavaFXEntity {

        private Node node;
        private double x;
        private double y;

        public JavaFXEntityImpl(Location initialPosition) {
            super(initialPosition);
        }

        @Override
        public void placeOnLocation(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public Node getGameNode() {
            return node;
        }

        public void setNode(Node node) {
            this.node = node;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}
