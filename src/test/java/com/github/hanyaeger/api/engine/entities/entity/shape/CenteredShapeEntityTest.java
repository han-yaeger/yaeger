package com.github.hanyaeger.api.engine.entities.entity.shape;

import com.github.hanyaeger.api.engine.entities.entity.AnchorPoint;
import com.github.hanyaeger.api.engine.entities.entity.Location;
import com.google.inject.Injector;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class CenteredShapeEntityTest {

    private static final Location LOCATION = new Location(37, 37);
    private static final double ENTITY_WIDTH = 200d;
    private static final double ENTITY_HEIGHT = 100d;
    private static final BoundingBox BOUNDING_BOX = new BoundingBox(0, 0, 10, 10);

    private Shape shape;
    private Injector injector;
    private CenteredShapeEntityImpl sut;
    private BoundingBox boundingBox;

    @BeforeEach
    void setup() {
        shape = mock(Shape.class, withSettings().withoutAnnotations());
        injector = mock(Injector.class);
        boundingBox = mock(BoundingBox.class);
        when(shape.getBoundsInLocal()).thenReturn(boundingBox);
        when(boundingBox.getWidth()).thenReturn(ENTITY_WIDTH);
        when(boundingBox.getHeight()).thenReturn(ENTITY_HEIGHT);

        sut = new CenteredShapeEntityImpl(LOCATION);
        sut.setShape(shape);
        sut.init(injector);
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForTOP_LEFT() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.TOP_LEFT);

        // Assert

        verify(shape).setTranslateX(ENTITY_WIDTH / 2);
        verify(shape).setTranslateY(ENTITY_HEIGHT / 2);
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForTOP_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.TOP_CENTER);

        // Assert
        verify(shape).setTranslateY(ENTITY_HEIGHT / 2);
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForTOP_RIGHT() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.TOP_RIGHT);

        // Assert
        verify(shape).setTranslateX(-(ENTITY_WIDTH / 2));
        verify(shape).setTranslateY(ENTITY_HEIGHT / 2);
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForLEFT_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.CENTER_LEFT);

        // Assert
        verify(shape).setTranslateX(ENTITY_WIDTH / 2);
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForCENTER_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.CENTER_CENTER);

        // Assert
        verify(shape, times(0)).setTranslateX(ENTITY_WIDTH / 2);
        verify(shape, times(0)).setTranslateY(ENTITY_HEIGHT / 2);
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForRIGHT_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.CENTER_RIGHT);

        // Assert
        verify(shape).setTranslateX(-(ENTITY_WIDTH / 2));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForBOTTOM_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.BOTTOM_CENTER);

        // Assert
        verify(shape).setTranslateY(-(ENTITY_HEIGHT / 2));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForBOTTOM_RIGHT() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.BOTTOM_RIGHT);

        // Assert
        verify(shape).setTranslateX(-(ENTITY_WIDTH / 2));
        verify(shape).setTranslateY(-(ENTITY_HEIGHT / 2));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForBOTTOM_LEFT() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.BOTTOM_LEFT);

        // Assert
        verify(shape).setTranslateX(ENTITY_WIDTH / 2);
        verify(shape).setTranslateY(-(ENTITY_HEIGHT / 2));
    }

    private class CenteredShapeEntityImpl extends CenteredShapeEntity<Shape> {

        public CenteredShapeEntityImpl(Location initialPosition) {
            super(initialPosition);
        }

        @Override
        public void setReferenceX(double x) {
            shape.ifPresentOrElse(shape -> shape.setLayoutX(x), () -> this.x = x);
        }

        @Override
        public void setReferenceY(double y) {
            shape.ifPresentOrElse(shape -> shape.setLayoutY(y), () -> this.y = y);
        }
    }

}
