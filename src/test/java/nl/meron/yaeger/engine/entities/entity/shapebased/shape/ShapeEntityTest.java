package nl.meron.yaeger.engine.entities.entity.shapebased.shape;

import com.google.inject.Injector;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.entity.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

class ShapeEntityTest {

    private static final Point POINT = new Point(37, 37);
    private Injector injector;
    private Shape shape;

    @BeforeEach
    void setup() {
        shape = mock(Shape.class, withSettings().withoutAnnotations());
        injector = mock(Injector.class);
    }

    @Test
    void provideShapeIsCalledAtInit(){
        // Setup
        var shapeEntity = new ShapeEntityImpl(POINT);
        shapeEntity.setTempShape(shape);

        // Test
        shapeEntity.init(injector);

        // Verify
        Assertions.assertEquals(shape, shapeEntity.getGameNode());
    }

    private class ShapeEntityImpl extends ShapeEntity{

        private Shape tempShape;

        public ShapeEntityImpl(Point initialPosition) {
            super(initialPosition);
        }

        @Override
        protected Shape provideShape() {
            return tempShape;
        }

        public void setTempShape(Shape tempShape) {
            this.tempShape = tempShape;
        }
    }

}
