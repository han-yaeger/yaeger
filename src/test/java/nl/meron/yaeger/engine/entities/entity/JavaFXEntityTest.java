package nl.meron.yaeger.engine.entities.entity;

import com.google.inject.Injector;
import javafx.scene.Node;
import javafx.stage.Stage;
import nl.meron.yaeger.engine.Timer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JavaFXEntityTest {

    private static final Location LOCATION = new Location(37, 37);
    private JavaFXEntityImpl sut;
    private Node node;
    private Stage stage;
    private Injector injector;

    @BeforeEach
    void setup() {
        sut = new JavaFXEntityImpl(LOCATION);
        injector = mock(Injector.class);
        node = mock(Node.class, withSettings().withoutAnnotations());
        stage = mock(Stage.class);
        sut.setNode(Optional.of(node));
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
    void placeOnSceneCallsSetXWithInitialLocation() {
        // Setup
        sut.init(injector);

        // Test
        sut.placeOnScene();

        // Verify
        Assertions.assertEquals(LOCATION.getX(), sut.getX());
    }

    @Test
    void placeOnSceneCallsSetYWithInitialLocation() {
        // Setup
        sut.init(injector);

        // Test
        sut.placeOnScene();

        // Verify
        Assertions.assertEquals(LOCATION.getY(), sut.getY());
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

        private Optional<Node> node;
        private double x;
        private double y;

        public JavaFXEntityImpl(Location initialPosition) {
            super(initialPosition);
        }

        @Override
        public Optional<Node> getGameNode() {
            return node;
        }

        public void setNode(Optional<Node> node) {
            this.node = node;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        @Override
        public void setX(double x) {
            this.x = x;
        }

        @Override
        public void setY(double y) {
            this.y = y;
        }
    }
}
