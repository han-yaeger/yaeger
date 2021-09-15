package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.entities.motion.MotionApplier;
import com.github.hanyaeger.api.scenes.SceneBorder;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
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
    private final static BoundingBox BOUNDS_IN_PARENT = new BoundingBox(10, 10, 10, 10);
    private final static BoundingBox BOUNDS_CROSSED_LEFT = new BoundingBox(-20, 10, 10, 10);
    private final static BoundingBox BOUNDS_CROSSED_RIGHT = new BoundingBox(110, 10, 10, 10);
    private final static BoundingBox BOUNDS_CROSSED_BOTTOM = new BoundingBox(10, 100, 10, 10);
    private final static BoundingBox BOUNDS_CROSSED_TOP = new BoundingBox(10, -20, 10, 10);
    private SceneBorderCrossingWatcherImpl sut;
    private Node node;
    private MotionApplier motionApplier;

    @BeforeEach
    void setup() {
        sut = new SceneBorderCrossingWatcherImpl();
        node = mock(Node.class, withSettings().withoutAnnotations());
        motionApplier = mock(MotionApplier.class);

        sut.setGameNode(node);
        sut.setMotionApplier(motionApplier);
        sut.setWidth(SCENE_WIDTH);
        sut.setHeight(SCENE_HEIGHT);
    }

    @Test
    void testWatchForBoundaryCrossingReturnsAnUpdatable() {
        // Arrange

        // Act
        var updatable = sut.watchForBoundaryCrossing();

        // Assert
        assertNotNull(updatable);
    }

    @Test
    void testBoundaryNotCrossed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_IN_PARENT);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertNull(sut.borderCrossed);
    }

    @Test
    void testBoundaryLeftCrossedWithZeroSpeed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_LEFT);
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
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_LEFT);
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
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_RIGHT);
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
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_RIGHT);
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
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_BOTTOM);
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
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_BOTTOM);
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
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_TOP);
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
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_TOP);
        when(motionApplier.getSpeed()).thenReturn(1d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.TOP, sut.borderCrossed);
    }

    private static class SceneBorderCrossingWatcherImpl implements SceneBorderCrossingWatcher {

        private Node gameNode;
        private MotionApplier motionApplier;
        SceneBorder borderCrossed;
        private double width;
        private double height;

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

        @Override
        public double getSceneWidth() {
            return width;
        }

        @Override
        public double getSceneHeight() {
            return height;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public void setHeight(double height) {
            this.height = height;
        }
    }
}
