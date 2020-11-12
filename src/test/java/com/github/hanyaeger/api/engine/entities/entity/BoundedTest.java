package com.github.hanyaeger.api.engine.entities.entity;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;

class BoundedTest {

    private Node node;
    private Bounds bounds;
    private BoundedImpl sut;

    @BeforeEach
    void setup() {
        node = mock(Node.class, withSettings().withoutAnnotations());
        bounds = mock(Bounds.class);

        sut = new BoundedImpl();
        sut.setNode(node);
        when(node.getBoundsInLocal()).thenReturn(bounds);
    }

    @Test
    void getNonTransformedBoundsReturnsZeroBoundingBoxIfGameNodeIsNotPresent() {
        // Arrange
        var sut = new EmptyGameNodeBoundedImpl();

        // Act
        var boundingBox = sut.getNonTransformedBounds();

        // Assert
        Assertions.assertEquals(0, boundingBox.getWidth());
        Assertions.assertEquals(0, boundingBox.getHeight());
    }

    @Test
    void getNonTransformedBoundsDelegatesToGameNodeIfPresent() {
        // Arrange
        var sut = new BoundedImpl();
        sut.setNode(node);

        // Act
        sut.getNonTransformedBounds();

        // Assert
        Mockito.verify(node).getBoundsInLocal();
    }

    @Test
    void getTransformedBoundsReturnsZeroBoundingBoxIfGameNodeIsNotPresent() {
        // Arrange
        var sut = new EmptyGameNodeBoundedImpl();

        // Act
        var boundingBox = sut.getTransformedBounds();

        // Assert
        Assertions.assertEquals(0, boundingBox.getWidth());
        Assertions.assertEquals(0, boundingBox.getHeight());
    }

    @Test
    void getTransformedBoundsDelegatesToGameNodeIfPresent() {
        // Arrange
        var sut = new BoundedImpl();
        sut.setNode(node);

        // Act
        sut.getTransformedBounds();

        // Assert
        Mockito.verify(node).getBoundsInParent();
    }

    @Test
    void getBoundsInSceneReturnsZeroBoundingBoxIfGameNodeIsNotPresent() {
        // Arrange
        var sut = new EmptyGameNodeBoundedImpl();

        // Act
        var boundingBox = sut.getBoundsInScene();

        // Assert
        Assertions.assertEquals(0, boundingBox.getWidth());
        Assertions.assertEquals(0, boundingBox.getHeight());
    }

    @Test
    void getBoundsInSceneDelegatesToGameNodeIfPresent() {
        // Arrange
        var sut = new BoundedImpl();
        sut.setNode(node);

        // Act
        sut.getBoundsInScene();

        // Assert
        Mockito.verify(node).localToScene(any(Bounds.class), eq(true));
        Mockito.verify(node).getBoundsInLocal();
    }

    @Test
    void getLeftXReturnValueFromBounds() {
        // Arrange
        var minX = 0.37;
        when(bounds.getMinX()).thenReturn(minX);

        // Act
        double leftSideX = sut.getLeftX();

        // Assert
        Assertions.assertEquals(minX, leftSideX);
    }

    @Test
    void getRightXReturnValueFromBounds() {
        // Arrange
        var maxX = 0.42;
        when(bounds.getMaxX()).thenReturn(maxX);

        // Act
        double rightSideX = sut.getRightX();

        // Assert
        Assertions.assertEquals(maxX, rightSideX);
    }

    @Test
    void getCenterXReturnValueFromBounds() {
        // Arrange
        var centerX = 0.24;
        when(bounds.getCenterX()).thenReturn(centerX);

        // Act
        double returnedCenterX = sut.getCenterX();

        // Assert
        Assertions.assertEquals(centerX, returnedCenterX);
    }

    @Test
    void getTopYReturnValueFromBounds() {
        // Arrange
        var minY = 0.37;
        when(bounds.getMinY()).thenReturn(minY);

        // Act
        double topSideY = sut.getTopY();

        // Assert
        Assertions.assertEquals(minY, topSideY);
    }

    @Test
    void getBottomYReturnValueFromBounds() {
        // Arrange
        var minY = 0.42;
        when(bounds.getMaxY()).thenReturn(minY);

        // Act
        double bottomY = sut.getBottomY();

        // Assert
        Assertions.assertEquals(minY, bottomY);
    }

    @Test
    void getCenterYReturnValueFromBounds() {
        // Arrange
        var centerY = 0.23;
        when(bounds.getCenterY()).thenReturn(centerY);

        // Act
        double returnedCenterY = sut.getCenterY();

        // Assert
        Assertions.assertEquals(centerY, returnedCenterY);
    }

    @Test
    void getWidthReturnValueFromBounds() {
        // Arrange
        var width = 37d;
        when(bounds.getWidth()).thenReturn(width);

        // Act
        double returnedWidth = sut.getWidth();

        // Assert
        Assertions.assertEquals(width, returnedWidth);
    }

    @Test
    void getHeightReturnValueFromBounds() {
        // Arrange
        var height = 0.42;
        when(bounds.getHeight()).thenReturn(height);

        // Act
        double returnedHeight = sut.getHeight();

        // Assert
        Assertions.assertEquals(height, returnedHeight);
    }

    private class BoundedImpl implements Bounded {

        private Node node;

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        public void setNode(Node node) {
            this.node = node;
        }
    }

    private class EmptyGameNodeBoundedImpl implements Bounded {

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.empty();
        }
    }
}
