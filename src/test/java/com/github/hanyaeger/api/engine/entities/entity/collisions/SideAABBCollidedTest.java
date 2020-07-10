package com.github.hanyaeger.api.engine.entities.entity.collisions;

import com.github.hanyaeger.api.engine.entities.entity.AnchorPoint;
import com.github.hanyaeger.api.engine.entities.entity.motion.DefaultMotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplier;
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
        var noCollisionCollider = new CollidingAABBCollider();
        noCollisionCollider.setBounds(TEST_COLLIDED_BODY_BOUNDINGBOX);

        Set<AABBCollider> testAABBColliders = Set.of(noCollisionCollider);

        // Act
        sut.checkForCollisions(testAABBColliders);

        // Assert
        assertEquals(CollisionSide.UNKNOWN, sut.getCollisionSide());
    }

    @Test
    void testBottomCollisionReportsCorrectly() {
        // Arrange
        var noCollisionCollider = new CollidingAABBCollider();
        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);
        var bottomCollisionCollider = new CollidingAABBCollider();
        bottomCollisionCollider.setBounds(TEST_COLLIDED_BOTTOM_BOUNDINGBOX);

        Set<AABBCollider> testAABBColliders = Set.of(noCollisionCollider, bottomCollisionCollider);

        // Act
        sut.checkForCollisions(testAABBColliders);

        // Assert
        assertEquals(bottomCollisionCollider, sut.getLastCollider());
        assertEquals(CollisionSide.BOTTOM, sut.getCollisionSide());
    }

    @Test
    void testTopCollisionReportsCorrectly() {
        // Arrange
        var noCollisionCollider = new CollidingAABBCollider();
        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);
        var topCollisionCollider = new CollidingAABBCollider();
        topCollisionCollider.setBounds(TEST_COLLIDED_TOP_BOUNDINGBOX);

        Set<AABBCollider> testAABBColliders = Set.of(noCollisionCollider, topCollisionCollider);

        // Act
        sut.checkForCollisions(testAABBColliders);

        // Assert
        assertEquals(sut.getLastCollider(), topCollisionCollider);
        assertEquals(CollisionSide.TOP, sut.getCollisionSide());
    }

    @Test
    void testLeftCollisionReportsCorrectly() {
        // Arrange
        var noCollisionCollider = new CollidingAABBCollider();
        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);
        var leftCollisionCollider = new CollidingAABBCollider();
        leftCollisionCollider.setBounds(TEST_COLLIDED_LEFT_BOUNDINGBOX);

        Set<AABBCollider> testAABBColliders = Set.of(noCollisionCollider, leftCollisionCollider);

        // Act
        sut.checkForCollisions(testAABBColliders);

        // Assert
        assertEquals(sut.getLastCollider(), leftCollisionCollider);
        assertEquals(CollisionSide.LEFT, sut.getCollisionSide());
    }

    @Test
    void testRightCollisionReportsCorrectly() {
        // Arrange
        var noCollisionCollider = new CollidingAABBCollider();
        noCollisionCollider.setBounds(TEST_NOT_COLLIDING_BOUNDINGBOX);
        var rightCollisionCollider = new CollidingAABBCollider();
        rightCollisionCollider.setBounds(TEST_COLLIDED_RIGHT_BOUNDINGBOX);

        Set<AABBCollider> testAABBColliders = Set.of(noCollisionCollider, rightCollisionCollider);

        // Act
        sut.checkForCollisions(testAABBColliders);

        // Assert
        assertEquals(sut.getLastCollider(), rightCollisionCollider);
        assertEquals(CollisionSide.RIGHT, sut.getCollisionSide());
    }

    private class CollidingAABBCollider implements AABBCollider {

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

        private AABBCollider AABBCollider;
        private CollisionSide collisionSide;

        @Override
        public void onCollision(AABBCollider collidingObject, CollisionSide side) {
            this.AABBCollider = collidingObject;
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

        public AABBCollider getLastCollider() {
            return AABBCollider;
        }

        @Override
        public void setMotionApplier(DefaultMotionApplier motionApplier) {

        }

        @Override
        public MotionApplier getMotionApplier() {
            return null;
        }

        @Override
        public void undoUpdate() {
            // TODO
        }

        @Override
        public void setOriginX(double x) {

        }

        @Override
        public void setOriginY(double y) {

        }

        @Override
        public void placeOnScene() {

        }

        @Override
        public void setAnchorPoint(AnchorPoint anchorPoint) {

        }

        @Override
        public AnchorPoint getAnchorPoint() {
            return null;
        }
    }
}
