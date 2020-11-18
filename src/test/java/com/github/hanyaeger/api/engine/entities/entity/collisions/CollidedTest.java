package com.github.hanyaeger.api.engine.entities.entity.collisions;

import com.github.hanyaeger.api.engine.entities.entity.AnchorPoint;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.motion.DefaultMotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.EntityMotionInitBuffer;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplierType;
import com.github.hanyaeger.api.guice.factories.MotionApplierFactory;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CollidedTest {

    private static final Bounds TEST_COLLIDED_BOUNDINGBOX = new BoundingBox(50, 50, 0, 25, 25, 0);
    private static final Bounds TEST_NOT_COLLIDING_BOUNDINGBOX = new BoundingBox(0, 0, 0, 1, 1, 0);

    private TestCollided sut;

    private MotionApplierFactory motionApplierFactory;
    private MotionApplier motionApplier;

    @BeforeEach
    void setup() {
        sut = new TestCollided();
        motionApplierFactory = mock(MotionApplierFactory.class);
        motionApplier = mock(DefaultMotionApplier.class);

        when(motionApplierFactory.create(any(MotionApplierType.class))).thenReturn(motionApplier);

        sut.injectMotionApplierFactory(motionApplierFactory);
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
        List<Collider> emptySet = List.of();

        // Act
        sut.checkForCollisions(emptySet);

        //Verify
        assertNull(sut.getLastCollider());
    }

    @Test
    void testTrivialCollisionGivesCollision() {
        // Arrange
        var trivialCollider = new CollidingCollider();
        trivialCollider.setBounds(TEST_COLLIDED_BOUNDINGBOX);

        when(motionApplier.getSpeed()).thenReturn(0d);
        when(motionApplier.getPreviousLocation()).thenReturn(Optional.of(new Coordinate2D(0, 0)));

        List<Collider> testColliders = List.of(trivialCollider);

        // Act
        sut.checkForCollisions(testColliders);

        // Assert
        assertEquals(trivialCollider, sut.getLastCollider());
    }

    @Test
    void testNoCollisionReportNoCollision() {
        // Arrange
        var noCollisionCollider = new CollidingCollider();
        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);

        List<Collider> testColliders = List.of(noCollisionCollider);

        // Act
        sut.checkForCollisions(testColliders);

        // Assert
        assertNull(sut.getLastCollider());
    }

    @Test
    void tesCollisionWithSelfReportsNoCollision() {
        // Arrange
        var collidables = new TestCollidable();
        List<Collider> testColliders = List.of(collidables);

        // Act
        collidables.checkForCollisions(testColliders);

        // Assert
        assertNull(collidables.getLastCollider());
    }

    private class CollidingCollider implements Collider {

        private Bounds bounds;

        @Override
        public Bounds getBoundsInScene() {
            return bounds;
        }

        @Override
        public Optional<? extends Node> getNode() {
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

    private class TestCollided implements Collided {

        private Collider lastCollided;
        private MotionApplier motionApplier;
        private boolean setAnchorLocationCalled = false;
        private boolean setOriginYcalled = false;

        @Override
        public void onCollision(Collider collidingObject) {
            lastCollided = collidingObject;
        }

        @Override
        public Bounds getBoundsInScene() {
            return TEST_COLLIDED_BOUNDINGBOX;
        }

        public Collider getLastCollider() {
            return lastCollided;
        }

        @Override
        public Optional<? extends Node> getNode() {
            return null;
        }

        @Override
        public void injectMotionApplierFactory(MotionApplierFactory motionApplierFactory) {
            this.motionApplier = motionApplierFactory.create(MotionApplierType.DEFAULT);
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
            this.setAnchorLocationCalled = true;
        }

        @Override
        public Coordinate2D getAnchorLocation() {
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

        public boolean isSetAnchorLocationCalled() {
            return setAnchorLocationCalled;
        }
    }

    private class TestCollidable extends TestCollided implements Collider, Collided {

        @Override
        public Optional<? extends Node> getNode() {
            return null;
        }

        @Override
        public double getDirection() {
            return 0;
        }

        @Override
        public void injectMotionApplierFactory(MotionApplierFactory motionApplierFactory) {
            // Not required here
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
            // Not required here
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
