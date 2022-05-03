package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.CircleEntity;
import com.google.inject.Injector;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void setAnchorLocationSetsAnchorLocationOnNode() {
        // Arrange
        sut.setShape(circle);
        var expected = new Coordinate2D(1.1, 2.2);

        // Act
        sut.setAnchorLocation(expected);

        // Assert
        verify(circle).setCenterX(expected.getX());
        verify(circle).setCenterY(expected.getY());
    }

    @Test
    void settingRadiusAfterNodeIsSetDelegatesTheRadius() {
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
    void getRadiusWithoutNodeOrBufferedRadiusReturnsDefault() {
        // Arrange

        // Act
        var actual = sut.getRadius();

        // Assert
        assertEquals(CircleEntity.DEFAULT_RADIUS, actual);
    }

    @Test
    void getRadiusBeforeNodeIsSetUsesBufferedRadius() {
        // Arrange
        sut.setRadius(RADIUS);

        // Act
        var actual = sut.getRadius();

        // Assert
        assertEquals(RADIUS, actual);
    }

    @Test
    void getRadiusAfterNodeIsSetDelegatesTheRadius() {
        // Arrange
        sut.setShape(circle);
        sut.init(injector);

        when(circle.getRadius()).thenReturn(RADIUS);

        // Act
        var actual = sut.getRadius();

        // Assert
        assertEquals(RADIUS, actual);
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

    private class CircleEntityImpl extends CircleEntity {

        public CircleEntityImpl(Coordinate2D initialPosition) {
            super(initialPosition);
        }
    }
}
