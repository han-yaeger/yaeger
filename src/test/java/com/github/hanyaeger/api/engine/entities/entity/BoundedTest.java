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
        when(node.localToScene((Bounds) any(), any(Boolean.class))).thenReturn(bounds);
        when(node.getBoundsInLocal()).thenReturn(bounds);
        when(node.getBoundsInParent()).thenReturn(bounds);
    }

    @Test
    void getTransformedBoundsReturnsZeroBoundingBoxIfGameNodeIsNotPresent() {
        // Arrange
        var sut = new EmptyGameNodeBoundedImpl();

        // Act
        var boundingBox = sut.getBoundingBox();

        // Assert
        Assertions.assertEquals(0, boundingBox.getWidth());
        Assertions.assertEquals(0, boundingBox.getHeight());
    }

    @Test
    void getBoundsInSceneReturnsZeroBoundingBoxIfGameNodeIsNotPresent() {
        // Arrange
        var sut = new EmptyGameNodeBoundedImpl();

        // Act
        var boundingBox = sut.getBoundingBox();

        // Assert
        Assertions.assertEquals(0, boundingBox.getWidth());
        Assertions.assertEquals(0, boundingBox.getHeight());
    }

    @Test
    void getBoundingBoxDelegatesToGameNodeIfPresent() {
        // Arrange
        var sut = new BoundedImpl();
        sut.setNode(node);

        // Act
        sut.getBoundingBox();

        // Assert
        Mockito.verify(node).localToScene(any(Bounds.class), eq(true));
        Mockito.verify(node).getBoundsInLocal();
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
