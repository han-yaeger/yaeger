package nl.han.ica.yaeger.gameobjects.interfaces;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import nl.han.ica.yaeger.delegates.CollisionSide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class CollidedTest {

    private TestCollided collided;

    private static final Bounds TEST_BOUNDINGBOX = new BoundingBox(50, 50, 0, 25, 25, 0);

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
        Assertions.assertEquals(collided.getLastCollider(), null);
    }

    @Test
    public void testNoCollidersGivesNoCollisions() {
        // Setup
        Set<Collider> emptySet = Set.of();

        // Test
        collided.checkForCollisions(emptySet);

        //Verify
        Assertions.assertEquals(collided.getLastCollider(), null);
    }

    @Test
    public void testTrivialCollisionGivesCollision() {
        // Setup
        Collider trivialCollider = new TrivialCollider();
        Set<Collider> testColliders = Set.of(trivialCollider);

        // Test
        collided.checkForCollisions(testColliders);

        // Verify
        Assertions.assertEquals(collided.getLastCollider(), trivialCollider);
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
            return TEST_BOUNDINGBOX;
        }


        public Collider getLastCollider() {
            return lastCollided;
        }

        public CollisionSide getSide() {
            return side;
        }
    }

    private class TrivialCollider implements Collider {

        @Override
        public Bounds getBounds() {
            return TEST_BOUNDINGBOX;
        }
    }
}
