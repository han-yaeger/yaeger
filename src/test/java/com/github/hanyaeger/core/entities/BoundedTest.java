package com.github.hanyaeger.core.entities;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals(0, boundingBox.getWidth());
        assertEquals(0, boundingBox.getHeight());
    }

    @Test
    void getBoundsInSceneReturnsZeroBoundingBoxIfGameNodeIsNotPresent() {
        // Arrange
        var sut = new EmptyGameNodeBoundedImpl();

        // Act
        var boundingBox = sut.getBoundingBox();

        // Assert
        assertEquals(0, boundingBox.getWidth());
        assertEquals(0, boundingBox.getHeight());
    }

    @Test
    void getBoundingBoxDelegatesToGameNodeIfPresent() {
        // Arrange
        var sut = new BoundedImpl();
        sut.setNode(node);

        // Act
        sut.getBoundingBox();

        // Assert
        Mockito.verify(node, times(4)).getBoundsInParent();
    }

    @Test
    void getWidthReturnValueFromBounds() {
        // Arrange
        var width = 37d;
        when(bounds.getWidth()).thenReturn(width);

        // Act
        double returnedWidth = sut.getWidth();

        // Assert
        assertEquals(width, returnedWidth);
    }

    @Test
    void getHeightReturnValueFromBounds() {
        // Arrange
        var height = 0.42;
        when(bounds.getHeight()).thenReturn(height);

        // Act
        double returnedHeight = sut.getHeight();

        // Assert
        assertEquals(height, returnedHeight);
    }

    @Test
    void getBoundingBoxReturnsMinimalBoxOnDefaultLocationIfNodeIsNull(){
        // Arrange
        var emptySut = new EmptyGameNodeBoundedImpl();

        // Act
        var boundingBox = emptySut.getBoundingBox();

        //Assert
        assertEquals(0, boundingBox.getHeight());
        assertEquals(0, boundingBox.getWidth());
        assertEquals(0, boundingBox.getMinX());
        assertEquals(0, boundingBox.getMinY());
    }

    private static class BoundedImpl implements Bounded {

        private Node node;

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        public void setNode(Node node) {
            this.node = node;
        }
    }

    private static class EmptyGameNodeBoundedImpl implements Bounded {

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.empty();
        }
    }
}
