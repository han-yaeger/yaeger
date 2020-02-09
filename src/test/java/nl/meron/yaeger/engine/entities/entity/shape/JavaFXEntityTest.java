package nl.meron.yaeger.engine.entities.entity.shape;

import com.google.inject.Injector;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import nl.meron.yaeger.engine.entities.entity.JavaFXEntity;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.events.system.RemoveEntityEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JavaFXEntityTest {

    private static final Location LOCATION = new Location(37, 37);

    private Shape shape;
    private Injector injector;

    @BeforeEach
    void setup() {

        shape = mock(Shape.class, withSettings().withoutAnnotations());
        injector = mock(Injector.class);
    }

    @Test
    void callingRemoveCleansUpTheEntity() {
        // Setup
        var sut = new JavaFXEntityImpl(LOCATION);
        sut.setShape(shape);
        sut.init(injector);

        // Test
        sut.remove();

        // Verify
        verify(shape, times(1)).setVisible(false);
        verify(shape).fireEvent(any(RemoveEntityEvent.class));
    }

    @Test
    void settingDelegateSetsVisibleOnDelegate() {
        // Setup
        var sut = new JavaFXEntityImpl(LOCATION);
        sut.setShape(shape);

        // Test
        sut.init(injector);

        // Verify
        verify(shape).setVisible(true);
    }

    @Test
    void settingVisibillityDelagatesToShape() {
        // Setup
        var sut = new JavaFXEntityImpl(LOCATION);
        sut.init(injector);
        sut.setShape(shape);

        // Test
        sut.setVisible(false);

        // Verify
        verify(shape).setVisible(false);
    }

    private class JavaFXEntityImpl extends JavaFXEntity {

        private Shape shape;

        public JavaFXEntityImpl(Location initialPosition) {
            super(initialPosition);
        }

        @Override
        public void placeOnLocation(double x, double y) {

        }

        public JavaFXEntityImpl(final Location initialPosition, final Shape shape) {
            super(initialPosition);
        }

        @Override
        public Node getGameNode() {
            return shape;
        }

        public void setShape(Shape shape) {
            this.shape = shape;
        }
    }
}
