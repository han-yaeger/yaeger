package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Coordinate2D;
import com.google.inject.Injector;
import javafx.scene.shape.Ellipse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class EllipseEntityTest {
    private static final Coordinate2D LOCATION = new Coordinate2D(37, 37);
    public static final double RADIUS_X = 37;
    public static final double RADIUS_Y = 42;

    private Ellipse ellipse;
    private Injector injector;

    @BeforeEach
    void setup() {
        ellipse = mock(Ellipse.class);
        injector = mock(Injector.class);
    }

    @Nested
    class OneArgumentConstructor {

        private EllipseEntity sut;

        @BeforeEach
        void setup() {
            sut = new EllipseEntityImpl(LOCATION);
        }

        @Test
        void setAnchorLocationSetsAnchorLocationOnNode() {
            // Arrange
            sut.setShape(ellipse);
            var expected = new Coordinate2D(1.1, 2.2);

            // Act
            sut.setAnchorLocation(expected);

            // Assert
            verify(ellipse).setCenterX(expected.getX());
            verify(ellipse).setCenterY(expected.getY());
        }

        @Test
        void getRadiusXWithoutNodeOrBufferedRadiusXReturnsDefault() {
            // Arrange

            // Act
            var actual = sut.getRadiusX();

            // Assert
            assertEquals(EllipseEntity.DEFAULT_RADIUS_X, actual);
        }

        @Test
        void getRadiusXBeforeNodeIsSetUsesBufferedRadiusX() {
            // Arrange
            sut.setRadiusX(RADIUS_X);

            // Act
            var actual = sut.getRadiusX();

            // Assert
            assertEquals(RADIUS_X, actual);
        }

        @Test
        void getRadiusXAfterNodeIsSetDelegatesTheRadiusX() {
            // Arrange
            sut.setShape(ellipse);
            sut.init(injector);

            when(ellipse.getRadiusX()).thenReturn(RADIUS_X);

            // Act
            var actual = sut.getRadiusX();

            // Assert
            assertEquals(RADIUS_X, actual);
        }

        @Test
        void getRadiusYWithoutNodeOrBufferedRadiusYReturnsDefault() {
            // Arrange

            // Act
            var actual = sut.getRadiusY();

            // Assert
            assertEquals(EllipseEntity.DEFAULT_RADIUS_Y, actual);
        }

        @Test
        void getRadiusYBeforeNodeIsSetUsesBufferedRadiusY() {
            // Arrange
            sut.setRadiusY(RADIUS_Y);

            // Act
            var actual = sut.getRadiusY();

            // Assert
            assertEquals(RADIUS_Y, actual);
        }

        @Test
        void getRadiusYAfterNodeIsSetDelegatesTheRadiusY() {
            // Arrange
            sut.setShape(ellipse);
            sut.init(injector);

            when(ellipse.getRadiusY()).thenReturn(RADIUS_Y);

            // Act
            var actual = sut.getRadiusY();

            // Assert
            assertEquals(RADIUS_Y, actual);
        }

        @Test
        void settingRadiusXAfterNodeIsSetDelegatesTheRadiusX() {
            // Arrange
            sut.setShape(ellipse);
            sut.init(injector);

            // Act
            sut.setRadiusX(RADIUS_X);

            // Assert
            verify(ellipse).setVisible(true);
            verify(ellipse).setRadiusX(RADIUS_X);
        }

        @Test
        void settingRadiusYAfterNodeIsSetDelegatesTheRadiusY() {
            // Arrange
            sut.setShape(ellipse);
            sut.init(injector);

            // Act
            sut.setRadiusY(RADIUS_Y);

            // Assert
            verify(ellipse).setVisible(true);
            verify(ellipse).setRadiusY(RADIUS_Y);
        }

        @Test
        void ifNodeNotYetSetRadiusXIsStoredAndSetAtInit() {
            // Arrange
            sut.setRadiusX(RADIUS_X);
            sut.setShape(ellipse);

            // Act
            sut.init(injector);

            // Assert
            verify(ellipse).setRadiusX(RADIUS_X);
        }

        @Test
        void ifNodeNotYetSetRadiusYIsStoredAndSetAtInit() {
            // Arrange
            sut.setRadiusY(RADIUS_Y);
            sut.setShape(ellipse);

            // Act
            sut.init(injector);

            // Assert
            verify(ellipse).setRadiusY(RADIUS_Y);
        }

        private class EllipseEntityImpl extends EllipseEntity {

            public EllipseEntityImpl(Coordinate2D initialPosition) {
                super(initialPosition);
            }
        }
    }

    @Nested
    class TwoArgumentConstructor {

        private final Size SIZE = new Size(RADIUS_X * 2, RADIUS_Y * 2);

        private EllipseEntity sut;

        @BeforeEach
        void setup() {
            sut = new EllipseEntityImpl(LOCATION, SIZE);
        }

        @Test
        void getRadiusXReturnsHalfWidthFromConstructor() {
            // Arrange

            // Act
            var actual = sut.getRadiusX();

            // Assert
            assertEquals(SIZE.width() / 2, actual);
        }

        @Test
        void getRadiusYReturnsHalfHeightFromConstructor() {
            // Arrange

            // Act
            var actual = sut.getRadiusY();

            // Assert
            assertEquals(SIZE.height() / 2, actual);
        }

        private class EllipseEntityImpl extends EllipseEntity {
            public EllipseEntityImpl(final Coordinate2D initialPosition, final Size size) {
                super(initialPosition, size);
            }
        }
    }
}
