package com.github.hanyaeger.api.engine.entities.entity.collisions;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AABBCollidedTest {

    private static final Bounds TEST_COLLIDED_BOUNDINGBOX = new BoundingBox(50, 50, 0, 25, 25, 0);
    private static final Bounds TEST_NOT_COLLIDING_BOUNDINGBOX = new BoundingBox(0, 0, 0, 1, 1, 0);

    private TestCollided sut;

    @BeforeEach
    void setup() {
        sut = new TestCollided();
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
        Set<Collider> emptySet = Set.of();

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

        Set<Collider> testColliders = Set.of(trivialCollider);

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

        Set<Collider> testColliders = Set.of(noCollisionCollider);

        // Act
        sut.checkForCollisions(testColliders);

        // Assert
        assertNull(sut.getLastCollider());
    }

    @Test
    void tesCollisionWithSelfReportsNoCollision() {
        // Arrange
        TestCollidable collidables = new TestCollidable();
        Set<Collider> testColliders = Set.of(collidables);

        // Act
        collidables.checkForCollisions(testColliders);

        // Assert
        assertNull(collidables.getLastCollider());
    }

    private class CollidingCollider implements Collider {

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

        private Collider lastCollided;

        @Override
        public void onCollision(Collider collidingObject) {
            lastCollided = collidingObject;
        }

        @Override
        public Bounds getTransformedBounds() {
            return TEST_COLLIDED_BOUNDINGBOX;
        }

        public Collider getLastCollider() {
            return lastCollided;
        }

        @Override
        public Optional<Node> getGameNode() {
            return null;
        }
    }

    private class TestCollidable extends TestCollided implements Collider, AABBCollided {

        @Override
        public Optional<Node> getGameNode() {
            return null;
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
}
