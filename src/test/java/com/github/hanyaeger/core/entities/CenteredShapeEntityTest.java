package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.entities.CenteredShapeEntity;
import com.google.inject.Injector;
import javafx.geometry.BoundingBox;
import javafx.scene.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class CenteredShapeEntityTest {

    private static final Coordinate2D LOCATION = new Coordinate2D(37, 37);
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

        public CenteredShapeEntityImpl(Coordinate2D initialPosition) {
            super(initialPosition);
        }

        @Override
        public void setAnchorLocation(Coordinate2D anchorLocation) {
            super.setAnchorLocation(anchorLocation);

            shape.ifPresent(shape -> {
                shape.setLayoutX(anchorLocation.getX());
                shape.setLayoutY(anchorLocation.getY());
            });
        }
    }

}
