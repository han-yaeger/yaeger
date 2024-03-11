package com.github.hanyaeger.api.entities;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CollidedTest {

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
        List<Collidable> emptySet = List.of();

        // Act
        sut.checkForCollisions(emptySet);

        //Verify
        assertNull(sut.getLastCollider());
    }

    @Test
    void testTrivialCollisionGivesCollision() {
        // Arrange
        List<CollidingCollidable> trivialColliders = new ArrayList<CollidingCollidable>();
        trivialColliders.add(new CollidingCollidable());
        for (var trivialCollider : trivialColliders) {
            trivialCollider.setBounds(TEST_COLLIDED_BOUNDINGBOX);

            List<Collidable> testColliders = List.of(trivialCollider);

            // Act
            sut.checkForCollisions(testColliders);

            // Assert
        }

        assertEquals(trivialColliders, sut.getLastCollider());
    }

    @Test
    void testNoCollisionReportNoCollision() {
        // Arrange
        var noCollisionCollider = new CollidingCollidable();
        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);

        List<Collidable> testColliders = List.of(noCollisionCollider);

        // Act
        sut.checkForCollisions(testColliders);

        // Assert
        assertNull(sut.getLastCollider());
    }

    @Test
    void tesCollisionWithSelfReportsNoCollision() {
        // Arrange
        var collidables = new TestCollidable();
        List<Collidable> testColliders = List.of(collidables);

        // Act
        collidables.checkForCollisions(testColliders);

        // Assert
        assertNull(collidables.getLastCollider());
    }

    private static class CollidingCollidable implements Collidable {

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

        @Override
        public void onCollision(List<Collidable> collidingObjects) {

        }
    }

    private static class TestCollided implements Collidable {

        private List<Collidable> lastCollideds;

        @Override
        public void onCollision(List<Collidable> collidingObject) {
            lastCollideds = collidingObject;
        }

        @Override
        public Bounds getBoundingBox() {
            return TEST_COLLIDED_BOUNDINGBOX;
        }

        public List<Collidable> getLastCollider() {
            return lastCollideds;
        }

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.empty();
        }
    }

    private static class TestCollidable extends TestCollided implements Collidable {

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.empty();
        }
    }
}
