package nl.meron.yaeger.engine.entities.entity.shapebased;

import com.google.inject.Injector;
import javafx.scene.shape.Shape;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.events.system.RemoveEntityEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ShapeBasedEntityTest {

    private static final Point POINT = new Point(37, 37);

    private Shape shape;
    private Injector injector;

    @BeforeEach
    void setup() {

        shape = mock(Shape.class, withSettings().withoutAnnotations());
        injector = mock(Injector.class);
    }

    @Test
    void settingTheDelegateSetsPositionOnDelegate() {
        // Setup
        var sut = new ShapeBasedEntityImpl(POINT);

        // Test
        sut.shape = shape;
        sut.init(injector);

        // Verify
        verify(shape).setLayoutX(POINT.getX());
        verify(shape).setLayoutY(POINT.getY());
    }

    @Test
    void callingRemoveCleansUpTheEntity() {
        // Setup
        var sut = new ShapeBasedEntityImpl(POINT);
        sut.shape = shape;
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
        var sut = new ShapeBasedEntityImpl(POINT);

        // Test
        sut.shape = shape;
        sut.init(injector);

        // Verify
        verify(shape).setVisible(true);
    }

    @Test
    void settingVisibillityDelagatesToShape() {
        // Setup
        var sut = new ShapeBasedEntityImpl(POINT);
        sut.shape = shape;
        sut.init(injector);

        // Test
        sut.setVisible(false);

        // Verify
        verify(shape).setVisible(false);
    }

    @Test
    void creatngANewEntityWithAShapeSetsTheShape(){
        // Setup
        var sut = new ShapeBasedEntityImpl(POINT, shape);

        // Test
        sut.init(injector);

        // Verify
        verify(shape).setLayoutX(POINT.getX());
        verify(shape).setLayoutY(POINT.getY());
    }

    private class ShapeBasedEntityImpl extends ShapeBasedEntity {

        public ShapeBasedEntityImpl(Point initialPosition) {
            super(initialPosition);
        }

        public ShapeBasedEntityImpl(final Point initialPosition, final Shape shape) {
            super(initialPosition, shape);
        }
    }
}
