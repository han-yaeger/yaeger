package nl.meron.yaeger.engine.entities.collisions;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import nl.meron.yaeger.engine.entities.collisions.CollisionSide;
import nl.meron.yaeger.engine.entities.collisions.Collidable;
import nl.meron.yaeger.engine.entities.collisions.Collided;
import nl.meron.yaeger.engine.entities.collisions.Collider;
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
        Collider trivialCollider = new CollidingCollider();
        Set<Collider> testColliders = Set.of(trivialCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertEquals(collided.getLastCollider(), trivialCollider);
    }

    @Test
    void testNoCollisionReportNoCollision() {
        // Setup
        Collider noCollisionCollider = new NotCollidingCollider();
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
    void testBottomCollisionReportsCorrectly() {
        // Setup
        Collider noCollisionCollider = new NotCollidingCollider();
        Collider bottomCollisionCollider = new CollidingBottomCollider();

        Set<Collider> testColliders = Set.of(noCollisionCollider, bottomCollisionCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertEquals(collided.getLastCollider(), bottomCollisionCollider);
        assertEquals(collided.getSide(), CollisionSide.BOTTOM);
    }

    @Test
    void testTopCollisionReportsCorrectly() {
        // Setup
        Collider noCollisionCollider = new NotCollidingCollider();
        Collider topCollisionCollider = new CollidingTopCollider();

        Set<Collider> testColliders = Set.of(noCollisionCollider, topCollisionCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertEquals(collided.getLastCollider(), topCollisionCollider);
        assertEquals(collided.getSide(), CollisionSide.TOP);
    }

    @Test
    void testLeftCollisionReportsCorrectly() {
        // Setup
        Collider noCollisionCollider = new NotCollidingCollider();
        Collider leftCollisionCollider = new CollidingLeftCollider();

        Set<Collider> testColliders = Set.of(noCollisionCollider, leftCollisionCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertEquals(collided.getLastCollider(), leftCollisionCollider);
        assertEquals(collided.getSide(), CollisionSide.LEFT);
    }

    @Test
    void testRightCollisionReportsCorrectly() {
        // Setup
        Collider noCollisionCollider = new NotCollidingCollider();
        Collider rightCollisionCollider = new CollidingRightCollider();

        Set<Collider> testColliders = Set.of(noCollisionCollider, rightCollisionCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertEquals(collided.getLastCollider(), rightCollisionCollider);
        assertEquals(collided.getSide(), CollisionSide.RIGHT);
    }

    private class CollidingCollider implements Collider {

        @Override
        public Bounds getBounds() {
            return TEST_COLLIDED_BOUNDINGBOX;
        }

        @Override
        public Node getGameNode() {
            return null;
        }
    }

    private class NotCollidingCollider implements Collider {

        @Override
        public Bounds getBounds() {
            return TEST_NOT_COLLIDING_BOUNDINGBOX;
        }

        @Override
        public Node getGameNode() {
            return null;
        }
    }

    private class CollidingRightCollider implements Collider {

        @Override
        public Bounds getBounds() {
            return TEST_COLLIDED_RIGHT_BOUNDINGBOX;
        }

        @Override
        public Node getGameNode() {
            return null;
        }
    }

    private class CollidingLeftCollider implements Collider {

        @Override
        public Bounds getBounds() {
            return TEST_COLLIDED_LEFT_BOUNDINGBOX;
        }

        @Override
        public Node getGameNode() {
            return null;
        }
    }

    private class CollidingTopCollider implements Collider {

        @Override
        public Bounds getBounds() {
            return TEST_COLLIDED_TOP_BOUNDINGBOX;
        }

        @Override
        public Node getGameNode() {
            return null;
        }
    }

    private class CollidingBottomCollider implements Collider {

        @Override
        public Bounds getBounds() {
            return TEST_COLLIDED_BOTTOM_BOUNDINGBOX;
        }

        @Override
        public Node getGameNode() {
            return null;
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
    }
}
