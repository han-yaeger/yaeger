package com.github.hanyaeger.api.engine.entities.entity;

import javafx.scene.Node;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SceneChildTest {

    public static final double WIDTH = 37d;
    public static final double HEIGHT = 42d;
    private SceneChildImpl sut;
    private Node node;
    private Scene scene;

    @BeforeEach
    void setup() {
        sut = new SceneChildImpl();
        node = mock(Node.class, withSettings().withoutAnnotations());
        scene = mock(Scene.class);
        sut.setNode(Optional.of(node));

        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(WIDTH);
        when(scene.getHeight()).thenReturn(HEIGHT);
    }

    @Test
    void getWidthDelegatesToNodeIfPresent() {
        // Arrange

        // Act
        double sceneWidth = sut.getSceneWidth();

        // Assert
        verify(scene).getWidth();
        assertEquals(WIDTH, sceneWidth);
    }

    @Test
    void getHeightDelegatesToNodeIfPresent() {
        // Arrange

        // Act
        double sceneHeight = sut.getSceneHeight();

        // Assert
        verify(scene).getHeight();
        assertEquals(HEIGHT, sceneHeight);
    }

    @Test
    void getWidthReturnsZeroIfNodeNotPresent() {
        // Arrange
        sut.setNode(Optional.empty());

        // Act
        double sceneWidth = sut.getSceneWidth();

        // Assert
        assertEquals(0, sceneWidth);
    }


    @Test
    void getHeightReturnsZeroIfNodeNotPresent() {
        // Arrange
        sut.setNode(Optional.empty());

        // Act
        double sceneHeight = sut.getSceneHeight();

        // Assert
        assertEquals(0, sceneHeight);
    }

    private class SceneChildImpl implements SceneChild {

        private Optional<Node> node;

        @Override
        public Optional<? extends Node> getNode() {
            return node;
        }

        public void setNode(Optional<Node> node) {
            this.node = node;
        }
    }
}
