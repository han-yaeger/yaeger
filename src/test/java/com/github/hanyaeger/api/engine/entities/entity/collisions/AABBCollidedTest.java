package com.github.hanyaeger.api.engine.entities.entity.collisions;

import com.github.hanyaeger.api.engine.entities.entity.AnchorPoint;
import com.github.hanyaeger.api.engine.entities.entity.motion.DefaultMotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplier;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AABBCollidedTest {

    private static final Bounds TEST_COLLIDED_BOUNDINGBOX = new BoundingBox(50, 50, 0, 25, 25, 0);
    private static final Bounds TEST_NOT_COLLIDING_BOUNDINGBOX = new BoundingBox(0, 0, 0, 1, 1, 0);

    private TestCollided sut;
    private DefaultMotionApplier motionApplier;

    @BeforeEach
    void setup() {
        sut = new TestCollided();
        motionApplier = mock(DefaultMotionApplier.class);
        sut.setMotionApplier(motionApplier);
    }

    @Test
    void testNullCollidersGivesNoCollisions() {
        // Arrange

        // Act
        sut.checkForCollisions(null);

        //Verify
        assertNull(sut.getLastCollider());
    }

    @Test
    void testNoCollidersGivesNoCollisions() {
        // Arrange
        Set<AABBCollider> emptySet = Set.of();

        // Act
        sut.checkForCollisions(emptySet);

        //Verify
        assertNull(sut.getLastCollider());
    }

    @Test
    void testTrivialCollisionGivesCollision() {
        // Arrange
        var trivialCollider = new CollidingAABBCollider();
        trivialCollider.setBounds(TEST_COLLIDED_BOUNDINGBOX);

        when(motionApplier.getSpeed()).thenReturn(0d);
        when(motionApplier.getPreviousLocation()).thenReturn(new Point2D(0, 0));

        Set<AABBCollider> testAABBColliders = Set.of(trivialCollider);

        // Act
        sut.checkForCollisions(testAABBColliders);

        // Assert
        assertEquals(trivialCollider, sut.getLastCollider());
    }

    @Test
    void testCollisionCallsUndoUpdate() {
        // Arrange
        var trivialCollider = new CollidingAABBCollider();
        trivialCollider.setBounds(TEST_COLLIDED_BOUNDINGBOX);

        var previousX = 37d;
        var previousY = 37d;

        when(motionApplier.getSpeed()).thenReturn(0d);
        when(motionApplier.getPreviousLocation()).thenReturn(new Point2D(previousX, previousY));

        Set<AABBCollider> testAABBColliders = Set.of(trivialCollider);

        // Act
        sut.checkForCollisions(testAABBColliders);

        // Assert
        assertTrue(sut.isSetOriginXcalled());
        assertTrue(sut.isSetOriginYcalled());
    }

    @Test
    void testNoCollisionReportNoCollision() {
        // Arrange
        var noCollisionCollider = new CollidingAABBCollider();
        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);

        Set<AABBCollider> testAABBColliders = Set.of(noCollisionCollider);

        // Act
        sut.checkForCollisions(testAABBColliders);

        // Assert
        assertNull(sut.getLastCollider());
    }

    @Test
    void tesCollisionWithSelfReportsNoCollision() {
        // Arrange
        TestCollidable collidables = new TestCollidable();
        Set<AABBCollider> testAABBColliders = Set.of(collidables);

        // Act
        collidables.checkForCollisions(testAABBColliders);

        // Assert
        assertNull(collidables.getLastCollider());
    }

    private class CollidingAABBCollider implements AABBCollider {

        private Bounds bounds;

        @Override
        public Bounds getTransformedBounds() {
            return bounds;
        }

        @Override
        public Optional<Node> getGameNode() {
            return null;
        }

        public void setBounds(Bounds bounds) {
            this.bounds = bounds;
        }

        @Override
        public double getDirection() {
            return 0;
        }

        @Override
        public double getSpeed() {
            return 0;
        }
    }

    private class TestCollided implements AABBCollided {

        private AABBCollider lastCollided;
        private MotionApplier motionApplier;
        private boolean setOriginXcalled = false;
        private boolean setOriginYcalled = false;

        @Override
        public void onCollision(AABBCollider collidingObject) {
            lastCollided = collidingObject;
        }

        @Override
        public Bounds getTransformedBounds() {
            return TEST_COLLIDED_BOUNDINGBOX;
        }

        public AABBCollider getLastCollider() {
            return lastCollided;
        }

        @Override
        public Optional<Node> getGameNode() {
            return null;
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
        public void setOriginX(double x) {
            this.setOriginXcalled = true;
        }

        @Override
        public void setOriginY(double y) {
            this.setOriginYcalled = true;
        }

        @Override
        public void placeOnScene() {

        }

        @Override
        public void setAnchorPoint(AnchorPoint anchorPoint) {

        }

        @Override
        public AnchorPoint getAnchorPoint() {
            return null;
        }

        public boolean isSetOriginXcalled() {
            return setOriginXcalled;
        }

        public boolean isSetOriginYcalled() {
            return setOriginYcalled;
        }
    }

    private class TestCollidable extends TestCollided implements AABBCollider, AABBCollided {

        @Override
        public Optional<Node> getGameNode() {
            return null;
        }

        @Override
        public double getDirection() {
            return 0;
        }

        @Override
        public void setMotionApplier(DefaultMotionApplier motionApplier) {

        }

        @Override
        public MotionApplier getMotionApplier() {
            return null;
        }

        @Override
        public double getSpeed() {
            return 0;
        }

        @Override
        public void undoUpdate() {
            // TODO
        }

        @Override
        public void setOriginX(double x) {

        }

        @Override
        public void setOriginY(double y) {

        }

        @Override
        public void placeOnScene() {

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
