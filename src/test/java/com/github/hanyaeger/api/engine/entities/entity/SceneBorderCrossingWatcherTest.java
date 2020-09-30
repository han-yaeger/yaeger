package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.entities.entity.motion.DefaultMotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplier;
import com.github.hanyaeger.api.engine.scenes.SceneBorder;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
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
    private SceneBorderCrossingWatcherImpl sut;
    private Node node;
    private Scene scene;
    private DefaultMotionApplier motionApplier;

    @BeforeEach
    void setup() {
        sut = new SceneBorderCrossingWatcherImpl();
        node = mock(Node.class, withSettings().withoutAnnotations());
        scene = mock(Scene.class);
        motionApplier = mock(DefaultMotionApplier.class);

        sut.setGameNode(node);
        sut.setMotionApplier(motionApplier);

        when(motionApplier.getPreviousLocation()).thenReturn(Optional.of(new Point2D(0, 0)));
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
        when(node.getBoundsInParent()).thenReturn(BOUNDS_IN_SCENE);
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
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_LEFT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(0d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.LEFT, sut.borderCrossed);
        verify(motionApplier, times(3)).getPreviousLocation();
    }

    @Test
    void testBoundaryLeftCrossedWithNonZeroSpeed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_LEFT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(1d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.LEFT, sut.borderCrossed);
        verify(motionApplier, times(0)).getPreviousLocation();
    }

    @Test
    void testBoundaryRightCrossedWithZeroSpeed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_RIGHT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(0d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.RIGHT, sut.borderCrossed);
        verify(motionApplier, times(3)).getPreviousLocation();
    }

    @Test
    void testBoundaryRightCrossedWithNonZeroSpeed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_RIGHT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(1d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.RIGHT, sut.borderCrossed);
        verify(motionApplier, times(0)).getPreviousLocation();
    }

    @Test
    void testBoundaryBottomCrossedWithZeroSpeed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_BOTTOM);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(0d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.BOTTOM, sut.borderCrossed);
        verify(motionApplier, times(3)).getPreviousLocation();
    }

    @Test
    void testBoundaryBottomCrossedWithNonZeroSpeed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_BOTTOM);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(1d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.BOTTOM, sut.borderCrossed);
        verify(motionApplier, times(0)).getPreviousLocation();
    }

    @Test
    void testBoundaryTopCrossedWithZeroSpeed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_TOP);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);


        when(motionApplier.getSpeed()).thenReturn(0d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.TOP, sut.borderCrossed);
        verify(motionApplier, times(3)).getPreviousLocation();
    }

    @Test
    void testBoundaryTopCrossedWithNonZeroSpeed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_TOP);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);

        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);
        when(motionApplier.getSpeed()).thenReturn(1d);

        var updatable = sut.watchForBoundaryCrossing();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.TOP, sut.borderCrossed);
        verify(motionApplier, times(0)).getPreviousLocation();
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
        public Optional<Node> getNode() {
            return Optional.of(gameNode);
        }

        public void setGameNode(Node node) {
            this.gameNode = node;
        }

        @Override
        public void setMotionApplier(DefaultMotionApplier motionApplier) {
            this.motionApplier = motionApplier;
        }

        @Override
        public MotionApplier getMotionApplier() {
            return motionApplier;
        }

        @Override
        public void setReferenceX(double x) {

        }

        @Override
        public void setReferenceY(double y) {

        }

        @Override
        public void transferCoordinatesToNode() {

        }

        @Override
        public void setAnchorPoint(AnchorPoint anchorPoint) {

        }

        @Override
        public AnchorPoint getAnchorPoint() {
            return null;
        }
    }
}
