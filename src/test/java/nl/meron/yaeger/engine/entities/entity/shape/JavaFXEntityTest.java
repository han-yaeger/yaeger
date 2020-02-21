package nl.meron.yaeger.engine.entities.entity.shape;

import com.google.inject.Injector;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import nl.meron.yaeger.engine.entities.entity.JavaFXEntity;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.events.system.RemoveEntityEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JavaFXEntityTest {

    private static final Location LOCATION = new Location(37, 37);

    private Node node;
    private Injector injector;
    private JavaFXEntityImpl sut;

    @BeforeEach
    void setup() {
        node = mock(Node.class, withSettings().withoutAnnotations());
        injector = mock(Injector.class);

        sut = new JavaFXEntityImpl(LOCATION);
        sut.setNode(node);
    }

    @Test
    void callingRemoveCleansUpTheEntity() {
        // Setup

        // Test
        sut.remove();

        // Verify
        verify(node, times(1)).setVisible(false);
        verify(node).fireEvent(any(RemoveEntityEvent.class));
    }

    @Test
    void settingDelegateSetsVisibleOnDelegate() {
        // Setup

        // Test
        sut.init(injector);

        // Verify
        verify(node).setVisible(true);
    }

    @Test
    void settingVisibillityDelagatesToShape() {
        // Setup
        sut.init(injector);

        // Test
        sut.setVisible(false);

        // Verify
        verify(node).setVisible(false);
    }

    private class JavaFXEntityImpl extends JavaFXEntity {

        private Node node;

        public JavaFXEntityImpl(Location initialPosition) {
            super(initialPosition);
        }

        @Override
        public Optional<Node> getGameNode() {
            return Optional.of(node);
        }

        public void setNode(Node node) {
            this.node = node;
        }

        @Override
        public void setX(double x) {
            // Not required here
        }

        @Override
        public void setY(double y) {
            // Not required here
        }
    }
}
