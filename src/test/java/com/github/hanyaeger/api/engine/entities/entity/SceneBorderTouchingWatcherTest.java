package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.entities.entity.motion.DefaultMotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplier;
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

class SceneBorderTouchingWatcherTest {

    private final static double SCENE_HEIGHT = 100;
    private final static double SCENE_WIDTH = 100;
    private final static BoundingBox BOUNDS_IN_SCENE = new BoundingBox(10, 10, 10, 10);
    private final static BoundingBox BOUNDS_CROSSED_LEFT = new BoundingBox(-20, 10, 10, 10);
    private final static BoundingBox BOUNDS_CROSSED_RIGHT = new BoundingBox(110, 10, 10, 10);
    private final static BoundingBox BOUNDS_CROSSED_BOTTOM = new BoundingBox(10, 100, 10, 10);
    private final static BoundingBox BOUNDS_CROSSED_TOP = new BoundingBox(10, -20, 10, 10);
    private SceneBorderTouchingWatcherImpl sut;
    private Node node;
    private Scene scene;
    private DefaultMotionApplier motionApplier;

    @BeforeEach
    void setup() {
        sut = new SceneBorderTouchingWatcherImpl();
        node = mock(Node.class, withSettings().withoutAnnotations());
        scene = mock(Scene.class);
        motionApplier = mock(DefaultMotionApplier.class);

        sut.setGameNode(node);
        sut.setMotionApplier(motionApplier);

        when(motionApplier.getPreviousLocation()).thenReturn(Optional.of(new Coordinate2D(0, 0)));
    }

    @Test
    void testWatchForBoundaryTouchingReturnsAnUpdatable() {
        // Arrange

        // Act
        var updatable = sut.watchForBoundaryTouching();

        // Assert
        assertTrue(updatable instanceof Updatable);
    }

    @Test
    void testBoundaryNotTouched() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_IN_SCENE);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        var updatable = sut.watchForBoundaryTouching();

        // Act
        updatable.update(0);

        // Assert
        assertNull(sut.borderTouched);
    }

    @Test
    void testBoundaryLeftCrossedWithZeroSpeed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_LEFT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(0d);

        var updatable = sut.watchForBoundaryTouching();

        // Act
        updatable.update(0);

        // Assert
        Assertions.assertEquals(SceneBorder.LEFT, sut.borderTouched);
    }

    @Test
    void testBoundaryLeftCrossedWithNonZeroSpeed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_LEFT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(1d);

        var updatable = sut.watchForBoundaryTouching();

        // Act
        updatable.update(0);

        // Assert
        Assertions.assertEquals(SceneBorder.LEFT, sut.borderTouched);
    }

    @Test
    void testBoundaryRightCrossedWithZeroSpeed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_RIGHT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(0d);

        var updatable = sut.watchForBoundaryTouching();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.RIGHT, sut.borderTouched);
    }

    @Test
    void testBoundaryRightCrossedWithNonZeroSpeed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_RIGHT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(1d);

        var updatable = sut.watchForBoundaryTouching();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.RIGHT, sut.borderTouched);
    }

    @Test
    void testBoundaryBottomTouchedWithZeroSpeed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_BOTTOM);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(0d);

        var updatable = sut.watchForBoundaryTouching();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.BOTTOM, sut.borderTouched);
    }

    @Test
    void testBoundaryBottomTouchedWithNonZeroSpeed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_BOTTOM);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(1d);

        var updatable = sut.watchForBoundaryTouching();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.BOTTOM, sut.borderTouched);
    }

    @Test
    void testBoundaryTopTouchedWithZeroSpeed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_TOP);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(0d);

        var updatable = sut.watchForBoundaryTouching();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.TOP, sut.borderTouched);
    }

    @Test
    void testBoundaryTopTouchedWithNonZeroSpeed() {
        // Arrange
        when(node.getBoundsInParent()).thenReturn(BOUNDS_CROSSED_TOP);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        when(motionApplier.getSpeed()).thenReturn(1d);

        var updatable = sut.watchForBoundaryTouching();

        // Act
        updatable.update(0);

        // Assert
        assertEquals(SceneBorder.TOP, sut.borderTouched);
    }

    private class SceneBorderTouchingWatcherImpl implements SceneBorderTouchingWatcher {

        private Node gameNode;
        private MotionApplier motionApplier;
        SceneBorder borderTouched;

        @Override
        public void notifyBoundaryTouching(SceneBorder border) {
            borderTouched = border;
        }

        @Override
        public Optional<? extends Node> getNode() {
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
        public void setAnchorLocationX(double x) {
            // Not required here.
        }

        @Override
        public void setAnchorLocationY(double y) {
            // Not required here.
        }

        @Override
        public void setAnchorLocation(Coordinate2D anchorLocation) {
            // Not required here.
        }

        @Override
        public Coordinate2D getAnchorLocation() {
            // Not required here.
            return null;
        }

        @Override
        public void transferCoordinatesToNode() {
            // Not required here.
        }

        @Override
        public void setAnchorPoint(AnchorPoint anchorPoint) {
            // Not required here.
        }

        @Override
        public AnchorPoint getAnchorPoint() {
            return null;
        }
    }
}
