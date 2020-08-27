package com.github.hanyaeger.api.engine.entities.entity.shape.circle;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.google.inject.Injector;
import javafx.geometry.Bounds;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class CircleEntityTest {
    private static final Coordinate2D LOCATION = new Coordinate2D(37, 37);
    public static final double RADIUS = 37;

    private Circle circle;
    private Injector injector;
    private CircleEntityImpl sut;

    @BeforeEach
    void setup() {
        circle = mock(Circle.class);
        injector = mock(Injector.class);

        sut = new CircleEntityImpl(LOCATION);
    }

    @Test
    void settingRadiusAfterShapeIsSetDelegatesTheRadius() {
        // Arrange
        sut.setShape(circle);
        sut.init(injector);

        // Act
        sut.setRadius(RADIUS);

        // Assert
        verify(circle).setVisible(true);
        verify(circle).setRadius(RADIUS);
    }

    @Test
    void ifShapeNotYetSetRadiusIsStoredAndSetAtInit() {
        // Arrange
        sut.setRadius(RADIUS);
        sut.setShape(circle);

        // Act
        sut.init(injector);

        // Assert
        verify(circle).setRadius(RADIUS);
    }

    @Test
    void getLeftXTakesRadiusIntoAccount() {
        // Arrange
        sut.setRadius(RADIUS);
        sut.setShape(circle);
        sut.init(injector);
        var bounds = mock(Bounds.class);
        when(circle.getBoundsInLocal()).thenReturn(bounds);
        when(bounds.getMinX()).thenReturn(LOCATION.getX());

        // Act
        var actual = sut.getLeftX();

        // Assert
        var expected = LOCATION.getX() + RADIUS;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getTopYTakesRadiusIntoAccount() {
        // Arrange
        sut.setRadius(RADIUS);
        sut.setShape(circle);
        sut.init(injector);
        var bounds = mock(Bounds.class);
        when(circle.getBoundsInLocal()).thenReturn(bounds);
        when(bounds.getMinY()).thenReturn(LOCATION.getY());

        // Act
        var actual = sut.getTopY();

        // Assert
        var expected = LOCATION.getY() + RADIUS;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void setReferenceXCallsSetXOnShapeAfterInitHasBeenCalled() {
        // Arrange
        sut.setShape(circle);
        sut.init(injector);

        var referenceX = 1d;

        // Act
        sut.setReferenceX(referenceX);

        // Assert
        verify(circle).setCenterX(referenceX);
    }

    @Test
    void setReferenceYCallsSetYOnShapeAfterInitHasBeenCalled() {
        // Arrange
        sut.setShape(circle);
        sut.init(injector);

        var referenceY = 1d;

        // Act
        sut.setReferenceY(referenceY);

        // Assert
        verify(circle).setCenterY(referenceY);
    }

    private class CircleEntityImpl extends CircleEntity {

        public CircleEntityImpl(Coordinate2D initialPosition) {
            super(initialPosition);
        }
    }
}
