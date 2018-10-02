package nl.han.ica.yaeger.engine.entities.interfaces;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import nl.han.ica.yaeger.engine.delegates.CollisionSide;
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
    public void setup() {
        collided = new TestCollided();
    }

    @Test
    public void testNullCollidersGivesNoCollisions() {
        // Setup

        // Test
        collided.checkForCollisions(null);

        //Verify
        assertNull(collided.getLastCollider());
    }

    @Test
    public void testNoCollidersGivesNoCollisions() {
        // Setup
        Set<Collider> emptySet = Set.of();

        // Test
        collided.checkForCollisions(emptySet);

        //Verify
        assertNull(collided.getLastCollider());
    }

    @Test
    public void testTrivialCollisionGivesCollision() {
        // Setup
        Collider trivialCollider = () -> TEST_COLLIDED_BOUNDINGBOX;
        Set<Collider> testColliders = Set.of(trivialCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertEquals(collided.getLastCollider(), trivialCollider);
    }

    @Test
    public void testNoCollisionReportNoCollision() {
        // Setup
        Collider noCollisionCollider = () -> TEST_NOT_COLLIDING_BOUNDINGBOX;
        Set<Collider> testColliders = Set.of(noCollisionCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertNull(collided.getLastCollider());
    }

    @Test
    public void tesCollisionWithSelfReportsNoCollision() {
        // Setup
        TestCollidable collidable = new TestCollidable();
        Set<Collider> testColliders = Set.of(collidable);

        // Test
        collidable.checkForCollisions(testColliders);

        // Verify
        assertNull(collidable.getLastCollider());
    }

    @Test
    public void testBottomCollisionReportsCorrectly() {
        // Setup
        Collider noCollisionCollider = () -> TEST_NOT_COLLIDING_BOUNDINGBOX;
        Collider bottomCollisionCollider = () -> TEST_COLLIDED_BOTTOM_BOUNDINGBOX;

        Set<Collider> testColliders = Set.of(noCollisionCollider, bottomCollisionCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertEquals(collided.getLastCollider(), bottomCollisionCollider);
        assertEquals(collided.getSide(), CollisionSide.BOTTOM);
    }

    @Test
    public void testTopCollisionReportsCorrectly() {
        // Setup
        Collider noCollisionCollider = () -> TEST_NOT_COLLIDING_BOUNDINGBOX;
        Collider topCollisionCollider = () -> TEST_COLLIDED_TOP_BOUNDINGBOX;

        Set<Collider> testColliders = Set.of(noCollisionCollider, topCollisionCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertEquals(collided.getLastCollider(), topCollisionCollider);
        assertEquals(collided.getSide(), CollisionSide.TOP);
    }

    @Test
    public void testLeftCollisionReportsCorrectly() {
        // Setup
        Collider noCollisionCollider = () -> TEST_NOT_COLLIDING_BOUNDINGBOX;
        Collider leftCollisionCollider = () -> TEST_COLLIDED_LEFT_BOUNDINGBOX;

        Set<Collider> testColliders = Set.of(noCollisionCollider, leftCollisionCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertEquals(collided.getLastCollider(), leftCollisionCollider);
        assertEquals(collided.getSide(), CollisionSide.LEFT);
    }

    @Test
    public void testRightCollisionReportsCorrectly() {
        // Setup
        Collider noCollisionCollider = () -> TEST_NOT_COLLIDING_BOUNDINGBOX;
        Collider rightCollisionCollider = () -> TEST_COLLIDED_RIGHT_BOUNDINGBOX;

        Set<Collider> testColliders = Set.of(noCollisionCollider, rightCollisionCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        assertEquals(collided.getLastCollider(), rightCollisionCollider);
        assertEquals(collided.getSide(), CollisionSide.RIGHT);
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
    }

    private class TestCollidable extends TestCollided implements Collidable {

    }
}
