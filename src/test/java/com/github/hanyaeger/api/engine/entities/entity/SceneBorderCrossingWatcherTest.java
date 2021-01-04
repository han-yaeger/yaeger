package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplier;
import com.github.hanyaeger.api.engine.scenes.SceneBorder;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
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
    private SceneBorderCrossingWatcherImpl sut;
    private Node node;
    private Scene scene;
    private MotionApplier motionApplier;

    @BeforeEach
    void setup() {
        sut = new SceneBorderCrossingWatcherImpl();
        node = mock(Node.class, withSettings().withoutAnnotations());
        scene = mock(Scene.class);
        motionApplier = mock(MotionApplier.class);

        sut.setGameNode(node);
        sut.setMotionApplier(motionApplier);
    }

    @Test
    void testWatchForBoundaryCrossingReturnsAnUpdatable() {
        // Arrange

        // Act
        var updatable = sut.watchForBoundaryCrossing();

        // Assert
        assertTrue(updatable instanceof Updatable);
    }

    @Test
    void testBoundaryNotCrossed() {
        // Arrange
        when(node.localToScene((Bounds) any(), any(Boolean.class))).thenReturn(BOUNDS_IN_SCENE);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertNull(sut.borderCrossed);
    }

    @Test
    void testBoundaryLeftCrossedWithZeroSpeed() {
        // Arrange
        when(node.localToScene((Bounds) any(), any(Boolean.class))).thenReturn(BOUNDS_CROSSED_LEFT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(0d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.LEFT, sut.borderCrossed);
    }

    @Test
    void testBoundaryLeftCrossedWithNonZeroSpeed() {
        // Arrange
        when(node.localToScene((Bounds) any(), any(Boolean.class))).thenReturn(BOUNDS_CROSSED_LEFT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(1d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.LEFT, sut.borderCrossed);
    }

    @Test
    void testBoundaryRightCrossedWithZeroSpeed() {
        // Arrange
        when(node.localToScene((Bounds) any(), any(Boolean.class))).thenReturn(BOUNDS_CROSSED_RIGHT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(0d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.RIGHT, sut.borderCrossed);
    }

    @Test
    void testBoundaryRightCrossedWithNonZeroSpeed() {
        // Arrange
        when(node.localToScene((Bounds) any(), any(Boolean.class))).thenReturn(BOUNDS_CROSSED_RIGHT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(1d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.RIGHT, sut.borderCrossed);
    }

    @Test
    void testBoundaryBottomCrossedWithZeroSpeed() {
        // Arrange
        when(node.localToScene((Bounds) any(), any(Boolean.class))).thenReturn(BOUNDS_CROSSED_BOTTOM);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(0d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.BOTTOM, sut.borderCrossed);
    }

    @Test
    void testBoundaryBottomCrossedWithNonZeroSpeed() {
        // Arrange
        when(node.localToScene((Bounds) any(), any(Boolean.class))).thenReturn(BOUNDS_CROSSED_BOTTOM);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(1d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.BOTTOM, sut.borderCrossed);
    }

    @Test
    void testBoundaryTopCrossedWithZeroSpeed() {
        // Arrange
        when(node.localToScene((Bounds) any(), any(Boolean.class))).thenReturn(BOUNDS_CROSSED_TOP);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);


        when(motionApplier.getSpeed()).thenReturn(0d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.TOP, sut.borderCrossed);
    }

    @Test
    void testBoundaryTopCrossedWithNonZeroSpeed() {
        // Arrange
        when(node.localToScene((Bounds) any(), any(Boolean.class))).thenReturn(BOUNDS_CROSSED_TOP);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);

        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);
        when(motionApplier.getSpeed()).thenReturn(1d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.TOP, sut.borderCrossed);
    }

    private class SceneBorderCrossingWatcherImpl implements SceneBorderCrossingWatcher {

        private Node gameNode;
        private MotionApplier motionApplier;
        SceneBorder borderCrossed;

        @Override
        public void notifyBoundaryCrossing(SceneBorder border) {
            borderCrossed = border;
        }

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(gameNode);
        }

        public void setGameNode(Node node) {
            this.gameNode = node;
        }

        @Override
        public void setMotionApplier(final MotionApplier motionApplier) {
            this.motionApplier = motionApplier;
        }

        @Override
        public MotionApplier getMotionApplier() {
            return motionApplier;
        }

        @Override
        public void setAnchorLocationX(double x) {
            // Not required here
        }

        @Override
        public void setAnchorLocationY(double y) {
            // Not required here
        }

        @Override
        public void setAnchorLocation(Coordinate2D anchorLocation) {
            // Not required here
        }

        @Override
        public Coordinate2D getAnchorLocation() {
            return null;
        }

        @Override
        public void transferCoordinatesToNode() {
            // Not required here
        }

        @Override
        public void setAnchorPoint(AnchorPoint anchorPoint) {
            // Not required here
        }

        @Override
        public AnchorPoint getAnchorPoint() {
            return null;
        }
    }
}
