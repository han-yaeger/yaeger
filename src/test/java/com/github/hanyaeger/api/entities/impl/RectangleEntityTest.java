package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Coordinate2D;
import com.google.inject.Injector;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RectangleEntityTest {
    private static final Coordinate2D LOCATION = new Coordinate2D(37, 37);
    public static final double ARC_HEIGHT = 1d;
    public static final double ARC_WIDTH = 2d;
    public static final double WIDTH = 3d;
    public static final double HEIGHT = 5d;

    private Rectangle rectangle;
    private Injector injector;

    @BeforeEach
    void setup() {
        rectangle = mock(Rectangle.class);
        injector = mock(Injector.class);
    }

    @Nested
    class OneArgumentConstructor {

        private RectangleEntityImpl sut;

        @BeforeEach
        void setup() {
            sut = new RectangleEntityImpl(LOCATION);
        }

        @Test
        void setAnchorLocationSetsAnchorLocationOnNode() {
            // Arrange
            sut.setShape(rectangle);
            var expected = new Coordinate2D(1.1, 2.2);

            // Act
            sut.setAnchorLocation(expected);

            // Assert
            verify(rectangle).setX(expected.getX());
            verify(rectangle).setY(expected.getY());
        }

        @Test
        void getWidthWithoutNodeOrBufferedWidthReturnsDefault() {
            // Arrange

            // Act
            var actual = sut.getWidth();

            // Assert
            assertEquals(RectangleEntity.DEFAULT_WIDTH, actual);
        }

        @Test
        void getWidthBeforeNodeIsSetUsesBufferedWidth() {
            // Arrange
            sut.setWidth(WIDTH);

            // Act
            var actual = sut.getWidth();

            // Assert
            assertEquals(WIDTH, actual);
        }

        @Test
        void getWidthAfterNodeIsSetDelegatesTheWidth() {
            // Arrange
            sut.setShape(rectangle);
            sut.init(injector);

            when(rectangle.getWidth()).thenReturn(WIDTH);

            // Act
            var actual = sut.getWidth();

            // Assert
            assertEquals(WIDTH, actual);
        }

        @Test
        void getHeightWithoutNodeOrBufferedHeightReturnsDefault() {
            // Arrange

            // Act
            var actual = sut.getHeight();

            // Assert
            assertEquals(RectangleEntity.DEFAULT_HEIGHT, actual);
        }

        @Test
        void getHeightBeforeNodeIsSetUsesBufferedHeight() {
            // Arrange
            sut.setHeight(HEIGHT);

            // Act
            var actual = sut.getHeight();

            // Assert
            assertEquals(HEIGHT, actual);
        }

        @Test
        void getHeightAfterNodeIsSetDelegatesTheHeight() {
            // Arrange
            sut.setShape(rectangle);
            sut.init(injector);

            when(rectangle.getHeight()).thenReturn(HEIGHT);

            // Act
            var actual = sut.getHeight();

            // Assert
            assertEquals(HEIGHT, actual);
        }

        @Test
        void getArcWidthWithoutNodeOrBufferedArcWidthReturnsDefault() {
            // Arrange

            // Act
            var actual = sut.getArcWidth();

            // Assert
            assertEquals(RectangleEntity.DEFAULT_ARC, actual);
        }

        @Test
        void getArcWidthBeforeNodeIsSetUsesBufferedArcWidth() {
            // Arrange
            sut.setArcWidth(ARC_WIDTH);

            // Act
            var actual = sut.getArcWidth();

            // Assert
            assertEquals(ARC_WIDTH, actual);
        }

        @Test
        void getArcWidthAfterNodeIsSetDelegatesTheArcWidth() {
            // Arrange
            sut.setShape(rectangle);
            sut.init(injector);

            when(rectangle.getArcWidth()).thenReturn(ARC_WIDTH);

            // Act
            var actual = sut.getArcWidth();

            // Assert
            assertEquals(ARC_WIDTH, actual);
        }

        @Test
        void getArcHeightWithoutNodeOrBufferedArcHeightReturnsDefault() {
            // Arrange

            // Act
            var actual = sut.getArcHeight();

            // Assert
            assertEquals(RectangleEntity.DEFAULT_ARC, actual);
        }

        @Test
        void getArcHeightBeforeNodeIsSetUsesBufferedHeight() {
            // Arrange
            sut.setArcHeight(ARC_HEIGHT);

            // Act
            var actual = sut.getArcHeight();

            // Assert
            assertEquals(ARC_HEIGHT, actual);
        }

        @Test
        void getArcHeightAfterNodeIsSetDelegatesTheArcHeight() {
            // Arrange
            sut.setShape(rectangle);
            sut.init(injector);

            when(rectangle.getArcHeight()).thenReturn(ARC_HEIGHT);

            // Act
            var actual = sut.getArcHeight();

            // Assert
            assertEquals(ARC_HEIGHT, actual);
        }

        @Test
        void settingValuesAfterDelegateIsSetDelegatesTheValues() {
            // Arrange
            sut.setShape(rectangle);
            sut.init(injector);

            // Act
            sut.setArcHeight(ARC_HEIGHT);
            sut.setArcWidth(ARC_WIDTH);
            sut.setWidth(WIDTH);
            sut.setHeight(HEIGHT);

            // Assert
            verify(rectangle).setVisible(true);
            verify(rectangle).setArcWidth(ARC_WIDTH);
            verify(rectangle).setArcHeight(ARC_HEIGHT);
            verify(rectangle).setWidth(WIDTH);
            verify(rectangle).setHeight(HEIGHT);
        }

        @Test
        void ifNodeNotYetSetStrokeHeightIsStoredAndSetAtInit() {
            // Arrange

            // Act
            sut.setHeight(HEIGHT);
            sut.setShape(rectangle);
            sut.init(injector);

            // Assert
            verify(rectangle).setHeight(HEIGHT);
        }

        @Test
        void ifNodeNotYetSetArcWidthIsStoredAndSetAtInit() {
            // Arrange
            sut.setArcWidth(ARC_WIDTH);
            sut.setShape(rectangle);

            // Act
            sut.init(injector);

            // Assert
            verify(rectangle).setArcWidth(ARC_WIDTH);
        }

        @Test
        void ifNodeNotYetSetArcHeightIsStoredAndSetAtInit() {
            // Arrange
            sut.setArcHeight(ARC_HEIGHT);
            sut.setShape(rectangle);

            // Act
            sut.init(injector);

            // Assert
            verify(rectangle).setArcHeight(ARC_HEIGHT);
        }

        private class RectangleEntityImpl extends RectangleEntity {

            public RectangleEntityImpl(Coordinate2D initialPosition) {
                super(initialPosition);
            }
        }
    }

    @Nested
    class TwoArgumentConstructor {

        private final Size SIZE = new Size(WIDTH, HEIGHT);

        private RectangleEntityImpl twoArgumentConstructorSut;

        @BeforeEach
        void setup() {
            twoArgumentConstructorSut = new RectangleEntityImpl(LOCATION, SIZE);
        }

        @Test
        void getWidthReturnsWidthFromConstructor() {
            // Arrange

            // Act
            var actual = twoArgumentConstructorSut.getWidth();

            // Assert
            assertEquals(WIDTH, actual);
        }

        @Test
        void getHeightReturnsHeightFromConstructor() {
            // Arrange

            // Act
            var actual = twoArgumentConstructorSut.getHeight();

            // Assert
            assertEquals(HEIGHT, actual);
        }

        private class RectangleEntityImpl extends RectangleEntity {

            public RectangleEntityImpl(final Coordinate2D initialPosition, final Size size) {
                super(initialPosition, size);
            }
        }
    }


}
