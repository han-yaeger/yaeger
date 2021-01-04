package com.github.hanyaeger.api.engine.entities.entity.collisions;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CollidedTest {

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
        public Bounds getBoundingBox() {
            return bounds;
        }

        @Override
        public Optional<? extends Node> getNode() {
            return null;
        }

        public void setBounds(Bounds bounds) {
            this.bounds = bounds;
        }
    }

    private class TestCollided implements Collided {

        private Collider lastCollided;

        @Override
        public void onCollision(Collider collidingObject) {
            lastCollided = collidingObject;
        }

        @Override
        public Bounds getBoundingBox() {
            return TEST_COLLIDED_BOUNDINGBOX;
        }

        public Collider getLastCollider() {
            return lastCollided;
        }

        @Override
        public Optional<? extends Node> getNode() {
            return null;
        }
    }

    private class TestCollidable extends TestCollided implements Collider, Collided {

        @Override
        public Optional<? extends Node> getNode() {
            return null;
        }
    }
}
