package nl.meron.yaeger.engine.entities.entity.collisions;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SideAABBCollidedTest {

    private static final Bounds TEST_NOT_COLLIDING_BOUNDINGBOX = new BoundingBox(0, 0, 0, 1, 1, 0);
    private static final Bounds TEST_COLLIDED_BOUNDINGBOX = new BoundingBox(50, 50, 0, 25, 25, 0);
    private static final Bounds TEST_COLLIDED_BOTTOM_BOUNDINGBOX = new BoundingBox(55, 49, 0, 10, 2, 0);
    private static final Bounds TEST_COLLIDED_TOP_BOUNDINGBOX = new BoundingBox(55, 74, 0, 10, 2, 0);
    private static final Bounds TEST_COLLIDED_LEFT_BOUNDINGBOX = new BoundingBox(49, 55, 0, 2, 10, 0);
    private static final Bounds TEST_COLLIDED_RIGHT_BOUNDINGBOX = new BoundingBox(74, 55, 0, 2, 10, 0);
    private static final Bounds TEST_COLLIDED_BODY_BOUNDINGBOX = new BoundingBox(66, 66, 0, 2, 2, 0);

    private TestAwareCollidedAABB sut;

    @BeforeEach
    void setup() {
        sut = new TestAwareCollidedAABB();
    }

    @Test
    void testNoSideCollisionReportsCorrectly() {
        // Arrange
        var noCollisionCollider = new CollidingCollider();
        noCollisionCollider.setBounds(TEST_COLLIDED_BODY_BOUNDINGBOX);

        Set<Collider> testColliders = Set.of(noCollisionCollider);

        // Act
        sut.checkForCollisions(testColliders);

        // Assert
        assertEquals(CollisionSide.UNKNOWN, sut.getCollisionSide());
    }

    @Test
    void testBottomCollisionReportsCorrectly() {
        // Arrange
        var noCollisionCollider = new CollidingCollider();
        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);
        var bottomCollisionCollider = new CollidingCollider();
        bottomCollisionCollider.setBounds(TEST_COLLIDED_BOTTOM_BOUNDINGBOX);

        Set<Collider> testColliders = Set.of(noCollisionCollider, bottomCollisionCollider);

        // Act
        sut.checkForCollisions(testColliders);

        // Assert
        assertEquals(bottomCollisionCollider, sut.getLastCollider());
        assertEquals(CollisionSide.BOTTOM, sut.getCollisionSide());
    }

    @Test
    void testTopCollisionReportsCorrectly() {
        // Arrange
        var noCollisionCollider = new CollidingCollider();
        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);
        var topCollisionCollider = new CollidingCollider();
        topCollisionCollider.setBounds(TEST_COLLIDED_TOP_BOUNDINGBOX);

        Set<Collider> testColliders = Set.of(noCollisionCollider, topCollisionCollider);

        // Act
        sut.checkForCollisions(testColliders);

        // Assert
        assertEquals(sut.getLastCollider(), topCollisionCollider);
        assertEquals(CollisionSide.TOP, sut.getCollisionSide());
    }

    @Test
    void testLeftCollisionReportsCorrectly() {
        // Arrange
        var noCollisionCollider = new CollidingCollider();
        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);
        var leftCollisionCollider = new CollidingCollider();
        leftCollisionCollider.setBounds(TEST_COLLIDED_LEFT_BOUNDINGBOX);

        Set<Collider> testColliders = Set.of(noCollisionCollider, leftCollisionCollider);

        // Act
        sut.checkForCollisions(testColliders);

        // Assert
        assertEquals(sut.getLastCollider(), leftCollisionCollider);
        assertEquals(CollisionSide.LEFT, sut.getCollisionSide());
    }

    @Test
    void testRightCollisionReportsCorrectly() {
        // Arrange
        var noCollisionCollider = new CollidingCollider();
        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);
        var rightCollisionCollider = new CollidingCollider();
        rightCollisionCollider.setBounds(TEST_COLLIDED_RIGHT_BOUNDINGBOX);

        Set<Collider> testColliders = Set.of(noCollisionCollider, rightCollisionCollider);

        // Act
        sut.checkForCollisions(testColliders);

        // Assert
        assertEquals(sut.getLastCollider(), rightCollisionCollider);
        assertEquals(CollisionSide.RIGHT, sut.getCollisionSide());
    }

    private class CollidingCollider implements Collider {

        private Bounds bounds;

        @Override
        public Bounds getTransformedBounds() {
            return bounds;
        }

        @Override
        public Bounds getNonTransformedBounds() {
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

    private class TestAwareCollidedAABB implements AABBSideAwareCollided {

        private Collider collider;
        private CollisionSide collisionSide;

        @Override
        public void onCollision(Collider collidingObject, CollisionSide side) {
            this.collider = collidingObject;
            this.collisionSide = side;
        }

        @Override
        public Bounds getTransformedBounds() {
            return TEST_COLLIDED_BOUNDINGBOX;
        }


        @Override
        public Bounds getNonTransformedBounds() {
            return TEST_COLLIDED_BOUNDINGBOX;
        }

        @Override
        public Optional<Node> getGameNode() {
            return null;
        }

        public CollisionSide getCollisionSide() {
            return collisionSide;
        }

        public Collider getLastCollider() {
            return collider;
        }
    }
}
