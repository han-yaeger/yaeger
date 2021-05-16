package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.entities.ShapeEntity;
import com.google.inject.Injector;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class ShapeEntityTest {

    private static final Coordinate2D LOCATION = new Coordinate2D(37, 37);
    private static final Color FILL_COLOR = Color.DARKBLUE;
    private static final Color STROKE_COLOR = Color.LIGHTBLUE;
    public static final double STROKE_WIDTH = 4d;

    private Shape shape;
    private Injector injector;
    private ShapeEntityImpl sut;

    @BeforeEach
    void setup() {
        shape = mock(Shape.class, withSettings().withoutAnnotations());
        injector = mock(Injector.class);

        sut = new ShapeEntityImpl(LOCATION);
    }

    @Test
    void getFillWithoutNodeOrBufferedFillReturnsNull() {
        // Arrange

        // Act
        var actual = sut.getFill();

        // Assert
        assertNull(actual);
    }

    @Test
    void getFillBeforeNodeIsSetUsesBufferedFill() {
        // Arrange
        sut.setFill(FILL_COLOR);

        // Act
        var actual = sut.getFill();

        // Assert
        assertEquals(FILL_COLOR, actual);
    }

    @Test
    void getFillAfterNodeIsSetDelegatesTheFill() {
        // Arrange
        sut.setShape(shape);
        sut.init(injector);

        when(shape.getFill()).thenReturn(FILL_COLOR);

        // Act
        var actual = sut.getFill();

        // Assert
        assertEquals(FILL_COLOR, actual);
    }

    @Test
    void getStrokeColorWithoutNodeOrBufferedStrokeColorReturnsNull() {
        // Arrange

        // Act
        var actual = sut.getStrokeColor();

        // Assert
        assertNull(actual);
    }

    @Test
    void getStrokeColorBeforeNodeIsSetUsesBufferedStrokeColor() {
        // Arrange
        sut.setStrokeColor(STROKE_COLOR);

        // Act
        var actual = sut.getStrokeColor();

        // Assert
        assertEquals(STROKE_COLOR, actual);
    }

    @Test
    void getStrokeColorAfterNodeIsSetDelegatesTheStrokeColor() {
        // Arrange
        sut.setShape(shape);
        sut.init(injector);

        when(shape.getStroke()).thenReturn(STROKE_COLOR);

        // Act
        var actual = sut.getStrokeColor();

        // Assert
        assertEquals(STROKE_COLOR, actual);
    }

    @Test
    void getStrokeWidthWithoutNodeOrBufferedStrokeWidthReturnsNull() {
        // Arrange

        // Act
        var actual = sut.getStrokeWidth();

        // Assert
        assertNull(actual);
    }

    @Test
    void getStrokeWidthBeforeNodeIsSetUsesBufferedStrokeWidth() {
        // Arrange
        sut.setStrokeWidth(STROKE_WIDTH);

        // Act
        var actual = sut.getStrokeWidth();

        // Assert
        assertEquals(STROKE_WIDTH, actual);
    }

    @Test
    void getStrokeWidthAfterNodeIsSetDelegatesTheStrokeWidth() {
        // Arrange
        sut.setShape(shape);
        sut.init(injector);

        when(shape.getStrokeWidth()).thenReturn(STROKE_WIDTH);

        // Act
        var actual = sut.getStrokeWidth();

        // Assert
        assertEquals(STROKE_WIDTH, actual);
    }

    @Test
    void getNodeReturnsEmptyNodeIfShapeNotSet() {
        // Arrange

        // Act
        var gameNode = sut.getNode();

        // Assert
        Assertions.assertTrue(gameNode.isEmpty());
    }

    @Test
    void getGameNodeReturnsTheShape() {
        // Arrange
        sut.setShape(shape);
        sut.init(injector);

        // Act
        var actual = sut.getNode().get();

        // Verify
        Assertions.assertEquals(shape, actual);
    }

    @Test
    void setShapeSetsStrokeTypeToInside() {
        // Arrange

        // Act
        sut.setShape(shape);

        // Assert
        verify(shape).setStrokeType(StrokeType.INSIDE);
    }

    @Test
    void setShapeSetsManagedToFalse() {
        // Arrange

        // Act
        sut.setShape(shape);

        // Assert
        verify(shape).setManaged(false);
    }

    @Test
    void setShapeSetsFocusTraversableFalse() {
        // Arrange

        // Act
        sut.setShape(shape);

        // Assert
        verify(shape).setFocusTraversable(false);
    }

    @Test
    void settingAncWithoutDelegateStoresPositionAsInitialPosition() {
        // Arrange

        // Act
        sut.setAnchorLocation(LOCATION);

        // Verify
        Assertions.assertEquals(0, Double.compare(sut.getAnchorLocation().getX(), LOCATION.getX()));
        Assertions.assertEquals(0, Double.compare(sut.getAnchorLocation().getY(), LOCATION.getY()));
    }

    @Test
    void settingValuesAfterDelegateIsSetDelegatesTheValues() {
        // Arrange
        sut.setShape(shape);
        sut.init(injector);

        // Act
        sut.setStrokeWidth(STROKE_WIDTH);
        sut.setFill(FILL_COLOR);
        sut.setStrokeColor(STROKE_COLOR);

        // Verify
        verify(shape).setVisible(true);
        verify(shape).setStrokeWidth(STROKE_WIDTH);
        verify(shape).setStroke(STROKE_COLOR);
        verify(shape).setFill(FILL_COLOR);
    }

    @Test
    void settingValuesBeforeDelegateIsSetSetsValuesAtInit() {
        // Arrange
        sut.setStrokeWidth(STROKE_WIDTH);
        sut.setFill(FILL_COLOR);
        sut.setStrokeColor(STROKE_COLOR);

        sut.setShape(shape);

        // Act
        sut.init(injector);

        // Verify
        verify(shape).setStrokeWidth(STROKE_WIDTH);
        verify(shape).setStroke(STROKE_COLOR);
        verify(shape).setFill(FILL_COLOR);
    }

    private class ShapeEntityImpl extends ShapeEntity<Shape> {

        public ShapeEntityImpl(Coordinate2D initialPosition) {
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
