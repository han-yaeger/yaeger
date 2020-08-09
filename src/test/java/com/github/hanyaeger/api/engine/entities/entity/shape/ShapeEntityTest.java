package com.github.hanyaeger.api.engine.entities.entity.shape;

import com.github.hanyaeger.api.engine.entities.entity.Location;
import com.google.inject.Injector;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class ShapeEntityTest {

    private static final Location LOCATION = new Location(37, 37);
    private static final Color COLOR_FILL = Color.DARKBLUE;
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
    void getNodeReturnsEmptyNodeIfShapeNotSet() {
        // Arrange

        // Act
        Optional<Node> gameNode = sut.getGameNode();

        // Assert
        Assertions.assertTrue(gameNode.isEmpty());
    }

    @Test
    void getGameNodeReturnsTheShape() {
        // Arrange
        sut.setShape(shape);
        sut.init(injector);

        // Act
        var actual = sut.getGameNode().get();

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
    void settingPositionWithoutDelegateStoresPositionAsInitialPosition() {
        // Arrange

        // Act
        sut.setReferenceX(LOCATION.getX());
        sut.setReferenceY(LOCATION.getY());

        // Verify
        Assertions.assertEquals(0, Double.compare(sut.getInitialLocation().getX(), LOCATION.getX()));
        Assertions.assertEquals(0, Double.compare(sut.getInitialLocation().getY(), LOCATION.getY()));
    }

    @Test
    void settingValuesAfterDelegateIsSetDelegatesTheValues() {
        // Arrange
        sut.setShape(shape);
        sut.init(injector);

        // Act
        sut.setStrokeWidth(STROKE_WIDTH);
        sut.setFill(COLOR_FILL);
        sut.setStrokeColor(STROKE_COLOR);

        // Verify
        verify(shape).setVisible(true);
        verify(shape).setStrokeWidth(STROKE_WIDTH);
        verify(shape).setStroke(STROKE_COLOR);
        verify(shape).setFill(COLOR_FILL);
    }

    @Test
    void settingValuesBeforeDelegateIsSetSetsValuesAtInit() {
        // Arrange
        sut.setStrokeWidth(STROKE_WIDTH);
        sut.setFill(COLOR_FILL);
        sut.setStrokeColor(STROKE_COLOR);

        sut.setShape(shape);

        // Act
        sut.init(injector);

        // Verify
        verify(shape).setStrokeWidth(STROKE_WIDTH);
        verify(shape).setStroke(STROKE_COLOR);
        verify(shape).setFill(COLOR_FILL);
    }

    private class ShapeEntityImpl extends ShapeEntity<Shape> {

        public ShapeEntityImpl(Location initialPosition) {
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

        public Point2D getInitialLocation() {
            return new Point2D(x, y);
        }
    }

}
