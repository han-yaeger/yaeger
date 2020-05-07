package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.scenes.SceneBorder;
import javafx.geometry.BoundingBox;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SceneBorderCrossingWatcherTest {

    private final static double SCENE_HEIGHT = 100;
    private final static double SCENE_WIDTH = 100;
    private final static BoundingBox BOUNDS_IN_SCENE = new BoundingBox(10, 10, 10, 10);
    private final static BoundingBox BOUNDS_CROSSED_LEFT = new BoundingBox(-20, 10, 10, 10);
    private final static BoundingBox BOUNDS_CROSSED_RIGHT = new BoundingBox(110, 10, 10, 10);
    private final static BoundingBox BOUNDS_CROSSED_BOTTOM = new BoundingBox(10, 100, 10, 10);
    private final static BoundingBox BOUNDS_CROSSED_TOP = new BoundingBox(10, -20, 10, 10);
    private TestWatcher watcher;
    private Node node;
    private Scene scene;

    @BeforeEach
    void setup() {
        watcher = new TestWatcher();
        node = mock(Node.class, withSettings().withoutAnnotations());
        scene = mock(Scene.class);
        watcher.setGameNode(node);
    }

    @Test
    void testWatchForBoundaryCrossingReturnsAnUpdatable() {
        // Arrange

        // Act
        var updatable = watcher.watchForBoundaryCrossing();

        // Assert
        assertTrue(updatable instanceof Updatable);
    }

    @Test
    void testBoundaryNotCrossed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_IN_SCENE);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        var updatable = watcher.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertNull(watcher.borderCrossed);
    }

    @Test
    void testBoundaryLeftCrossed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_LEFT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        var updatable = watcher.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        Assertions.assertEquals(SceneBorder.LEFT, watcher.borderCrossed);
    }

    @Test
    void testBoundaryRightCrossed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_RIGHT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        var updatable = watcher.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.RIGHT, watcher.borderCrossed);
    }

    @Test
    void testBoundaryBottomCrossed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_BOTTOM);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        var updatable = watcher.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.BOTTOM, watcher.borderCrossed);
    }

    @Test
    void testBoundaryTopCrossed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_TOP);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        var updatable = watcher.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.TOP, watcher.borderCrossed);
    }

    private class TestWatcher implements SceneBorderCrossingWatcher {

        private Node gameNode;
        SceneBorder borderCrossed;

        @Override
        public void notifyBoundaryCrossing(SceneBorder border) {
            borderCrossed = border;
        }

        @Override
        public Optional<Node> getGameNode() {
            return Optional.of(gameNode);
        }

        public void setGameNode(Node node) {
            this.gameNode = node;
        }
    }
}
