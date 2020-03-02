package nl.meron.yaeger.engine.entities.entity.collisions;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SideCollidedTest {

    private static final Bounds TEST_COLLIDED_BOTTOM_BOUNDINGBOX = new BoundingBox(55, 49, 0, 10, 2, 0);
    private static final Bounds TEST_COLLIDED_TOP_BOUNDINGBOX = new BoundingBox(55, 74, 0, 10, 2, 0);
    private static final Bounds TEST_COLLIDED_LEFT_BOUNDINGBOX = new BoundingBox(49, 55, 0, 2, 10, 0);
    private static final Bounds TEST_COLLIDED_RIGHT_BOUNDINGBOX = new BoundingBox(74, 55, 0, 2, 10, 0);
    private static final Bounds TEST_COLLIDED_BODY_BOUNDINGBOX = new BoundingBox(66, 66, 0, 2, 2, 0);

//    @Test
//    void testNoCollisionReportsCorrectly() {
//        // Setup
//        var noCollisionCollider = new CollidedTest.CollidingCollider();
//        noCollisionCollider.setBounds(TEST_COLLIDED_BODY_BOUNDINGBOX);
//
//        Set<Collider> testColliders = Set.of(noCollisionCollider);
//
//        // Test
//        collided.checkForCollisions(testColliders);
//
//        // Verify
//        assertEquals(CollisionSide.UNKNOWN, collided.getSide());
//    }
//
//    @Test
//    void testBottomCollisionReportsCorrectly() {
//        // Setup
//        var noCollisionCollider = new CollidedTest.CollidingCollider();
//        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);
//        var bottomCollisionCollider = new CollidedTest.CollidingCollider();
//        bottomCollisionCollider.setBounds(TEST_COLLIDED_BOTTOM_BOUNDINGBOX);
//
//        Set<Collider> testColliders = Set.of(noCollisionCollider, bottomCollisionCollider);
//
//        // Test
//        collided.checkForCollisions(testColliders);
//
//        // Verify
//        assertEquals(bottomCollisionCollider, collided.getLastCollider());
//        assertEquals(CollisionSide.BOTTOM, collided.getSide());
//    }
//
//    @Test
//    void testTopCollisionReportsCorrectly() {
//        // Setup
//        var noCollisionCollider = new CollidedTest.CollidingCollider();
//        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);
//        var topCollisionCollider = new CollidedTest.CollidingCollider();
//        topCollisionCollider.setBounds(TEST_COLLIDED_TOP_BOUNDINGBOX);
//
//        Set<Collider> testColliders = Set.of(noCollisionCollider, topCollisionCollider);
//
//        // Test
//        collided.checkForCollisions(testColliders);
//
//        // Verify
//        assertEquals(collided.getLastCollider(), topCollisionCollider);
//        assertEquals(CollisionSide.TOP, collided.getSide());
//    }
//
//    @Test
//    void testLeftCollisionReportsCorrectly() {
//        // Setup
//        var noCollisionCollider = new CollidedTest.CollidingCollider();
//        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);
//        var leftCollisionCollider = new CollidedTest.CollidingCollider();
//        leftCollisionCollider.setBounds(TEST_COLLIDED_LEFT_BOUNDINGBOX);
//
//        Set<Collider> testColliders = Set.of(noCollisionCollider, leftCollisionCollider);
//
//        // Test
//        collided.checkForCollisions(testColliders);
//
//        // Verify
//        assertEquals(collided.getLastCollider(), leftCollisionCollider);
//        assertEquals(CollisionSide.LEFT, collided.getSide());
//    }
//
//    @Test
//    void testRightCollisionReportsCorrectly() {
//        // Setup
//        var noCollisionCollider = new CollidedTest.CollidingCollider();
//        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);
//        var rightCollisionCollider = new CollidedTest.CollidingCollider();
//        rightCollisionCollider.setBounds(TEST_COLLIDED_RIGHT_BOUNDINGBOX);
//
//        Set<Collider> testColliders = Set.of(noCollisionCollider, rightCollisionCollider);
//
//        // Test
//        collided.checkForCollisions(testColliders);
//
//        // Verify
//        assertEquals(collided.getLastCollider(), rightCollisionCollider);
//        assertEquals(CollisionSide.RIGHT, collided.getSide());
//    }
}
