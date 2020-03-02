package nl.meron.yaeger.engine.entities.entity.collisions;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CollidedTest {

    private TestCollided collided;

    private static final Bounds TEST_COLLIDED_BOUNDINGBOX = new BoundingBox(50, 50, 0, 25, 25, 0);
    private static final Bounds TEST_NOT_COLLIDING_BOUNDINGBOX = new BoundingBox(0, 0, 0, 1, 1, 0);

    @BeforeEach
    void setup() {
        collided = new TestCollided();
    }

    @Test
    void testNullCollidersGivesNoCollisions() {
        // Setup

        // Test
        collided.checkForCollisions(null);

        //Verify
        assertNull(collided.getLastCollider());
    }

    @Test
    void testNoCollidersGivesNoCollisions() {
        // Setup
        Set<Collider> emptySet = Set.of();

        // Test
        collided.checkForCollisions(emptySet);

        //Verify
        assertNull(collided.getLastCollider());
    }

    @Test
    void testTrivialCollisionGivesCollision() {
        // Setup
        var trivialCollider = new CollidingCollider();
        trivialCollider.setBounds(TEST_COLLIDED_BOUNDINGBOX);

        Set<Collider> testColliders = Set.of(trivialCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertEquals(trivialCollider, collided.getLastCollider());
    }

    @Test
    void testNoCollisionReportNoCollision() {
        // Setup
        var noCollisionCollider = new CollidingCollider();
        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);

        Set<Collider> testColliders = Set.of(noCollisionCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertNull(collided.getLastCollider());
    }

    @Test
    void tesCollisionWithSelfReportsNoCollision() {
        // Setup
        TestCollidable collidable = new TestCollidable();
        Set<Collider> testColliders = Set.of(collidable);

        // Test
        collidable.checkForCollisions(testColliders);

        // Verify
        assertNull(collidable.getLastCollider());
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

    private class TestCollided implements Collided {

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

    private class TestCollidable extends TestCollided implements Collidable {

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
