package nl.meron.yaeger.engine.entities.entity.collisions;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CollidedTest {

    private TestCollided collided;

    private static final Bounds TEST_COLLIDED_BOUNDINGBOX = new BoundingBox(50, 50, 0, 25, 25, 0);
    private static final Bounds TEST_COLLIDED_BOTTOM_BOUNDINGBOX = new BoundingBox(55, 49, 0, 10, 2, 0);
    private static final Bounds TEST_COLLIDED_TOP_BOUNDINGBOX = new BoundingBox(55, 74, 0, 10, 2, 0);
    private static final Bounds TEST_COLLIDED_LEFT_BOUNDINGBOX = new BoundingBox(49, 55, 0, 2, 10, 0);
    private static final Bounds TEST_COLLIDED_RIGHT_BOUNDINGBOX = new BoundingBox(74, 55, 0, 2, 10, 0);
    private static final Bounds TEST_COLLIDED_BODY_BOUNDINGBOX = new BoundingBox(66, 66, 0, 2, 2, 0);

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

    @Test
    void testNoCollisionReportsCorrectly() {
        // Setup
        var noCollisionCollider = new CollidingCollider();
        noCollisionCollider.setBounds(TEST_COLLIDED_BODY_BOUNDINGBOX);

        Set<Collider> testColliders = Set.of(noCollisionCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertEquals(CollisionSide.NONE, collided.getSide());
    }

    @Test
    void testBottomCollisionReportsCorrectly() {
        // Setup
        var noCollisionCollider = new CollidingCollider();
        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);
        var bottomCollisionCollider = new CollidingCollider();
        bottomCollisionCollider.setBounds(TEST_COLLIDED_BOTTOM_BOUNDINGBOX);

        Set<Collider> testColliders = Set.of(noCollisionCollider, bottomCollisionCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertEquals(bottomCollisionCollider, collided.getLastCollider());
        assertEquals(CollisionSide.BOTTOM, collided.getSide());
    }

    @Test
    void testTopCollisionReportsCorrectly() {
        // Setup
        var noCollisionCollider = new CollidingCollider();
        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);
        var topCollisionCollider = new CollidingCollider();
        topCollisionCollider.setBounds(TEST_COLLIDED_TOP_BOUNDINGBOX);

        Set<Collider> testColliders = Set.of(noCollisionCollider, topCollisionCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertEquals(collided.getLastCollider(), topCollisionCollider);
        assertEquals(CollisionSide.TOP, collided.getSide());
    }

    @Test
    void testLeftCollisionReportsCorrectly() {
        // Setup
        var noCollisionCollider = new CollidingCollider();
        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);
        var leftCollisionCollider = new CollidingCollider();
        leftCollisionCollider.setBounds(TEST_COLLIDED_LEFT_BOUNDINGBOX);

        Set<Collider> testColliders = Set.of(noCollisionCollider, leftCollisionCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertEquals(collided.getLastCollider(), leftCollisionCollider);
        assertEquals(CollisionSide.LEFT, collided.getSide());
    }

    @Test
    void testRightCollisionReportsCorrectly() {
        // Setup
        var noCollisionCollider = new CollidingCollider();
        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);
        var rightCollisionCollider = new CollidingCollider();
        rightCollisionCollider.setBounds(TEST_COLLIDED_RIGHT_BOUNDINGBOX);

        Set<Collider> testColliders = Set.of(noCollisionCollider, rightCollisionCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertEquals(collided.getLastCollider(), rightCollisionCollider);
        assertEquals(CollisionSide.RIGHT, collided.getSide());
    }

    private class CollidingCollider implements Collider {

        private Bounds bounds;

        @Override
        public Bounds getBounds() {
            return bounds;
        }

        @Override
        public Node getGameNode() {
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
        private CollisionSide side;

        @Override
        public void onCollision(Collider collidingObject, CollisionSide collisionSide) {
            lastCollided = collidingObject;
            side = collisionSide;
        }

        @Override
        public Bounds getBounds() {
            return TEST_COLLIDED_BOUNDINGBOX;
        }


        public Collider getLastCollider() {
            return lastCollided;
        }

        public CollisionSide getSide() {
            return side;
        }

        @Override
        public Node getGameNode() {
            return null;
        }
    }

    private class TestCollidable extends TestCollided implements Collidable {

        @Override
        public Node getGameNode() {
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
