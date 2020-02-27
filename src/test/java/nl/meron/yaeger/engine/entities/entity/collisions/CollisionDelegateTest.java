package nl.meron.yaeger.engine.entities.entity.collisions;

import com.google.inject.Injector;
import javafx.scene.Node;
import nl.meron.yaeger.engine.Timer;
import nl.meron.yaeger.engine.entities.entity.AnchorPoint;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.entity.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.mock;

class CollisionDelegateTest {

    private CollisionDelegate collisionDelegate;

    @BeforeEach
    void setup() {
        collisionDelegate = new CollisionDelegate();
    }

    @Test
    void onlyCollidedGetsCollisionCheck() {
        // Setup
        Collided collided = mock(Collided.class);
        Collider collider = mock(Collider.class);

        collisionDelegate.register(collided);
        collisionDelegate.register(collider);

        ArgumentCaptor<Set> argument = ArgumentCaptor.forClass(Set.class);

        // Test
        collisionDelegate.checkCollisions();

        // Verify
        Mockito.verify(collided).checkForCollisions(argument.capture());
        Assertions.assertEquals(1, argument.getValue().size());
    }

    @Test
    void collidableGetsCheckedIncludingItself() {
        // Setup
        Collidable collidable = mock(Collidable.class);
        Collider collider = mock(Collider.class);
        collisionDelegate.register(collidable);
        collisionDelegate.register(collider);

        ArgumentCaptor<Set> argument = ArgumentCaptor.forClass(Set.class);

        // Test
        collisionDelegate.checkCollisions();

        // Verify
        Mockito.verify(collidable).checkForCollisions(argument.capture());
        Assertions.assertEquals(2, argument.getValue().size());
    }

    @Test
    void entitiesGetCorrectlyAdded() {
        // Setup
        Entity collidedEntity = mock(CollidedTestEntity.class);
        Entity colliderEntity = mock(ColliderTestEntity.class);

        collisionDelegate.register(collidedEntity);
        collisionDelegate.register(colliderEntity);

        ArgumentCaptor<Set> argument = ArgumentCaptor.forClass(Set.class);

        // Test
        collisionDelegate.checkCollisions();

        // Verify
        Mockito.verify((Collided) collidedEntity).checkForCollisions(argument.capture());
        Assertions.assertEquals(1, argument.getValue().size());
    }

    @Test
    void afterRemoveCollidedNoCollisionsAreChecked() {
        // Setup
        Entity collidedEntity = mock(CollidedTestEntity.class);
        Entity colliderEntity = mock(ColliderTestEntity.class);

        collisionDelegate.register(collidedEntity);
        collisionDelegate.register(colliderEntity);

        // Test
        collisionDelegate.remove(collidedEntity);
        collisionDelegate.checkCollisions();

        // Verify
        Mockito.verifyNoMoreInteractions(collidedEntity);
    }

    @Test
    void afterRemoveColliderNoCollisionsAreReported() {
        // Setup
        Entity collidedEntity = mock(CollidedTestEntity.class);
        Entity colliderEntity = mock(ColliderTestEntity.class);

        collisionDelegate.register(collidedEntity);
        collisionDelegate.register(colliderEntity);

        ArgumentCaptor<Set> argument = ArgumentCaptor.forClass(Set.class);

        // Test
        collisionDelegate.remove(colliderEntity);
        collisionDelegate.checkCollisions();

        // Verify
        Mockito.verify((Collided) collidedEntity).checkForCollisions(argument.capture());
        Assertions.assertEquals(0, argument.getValue().size());
    }

    private class CollidedTestEntity implements Entity, Collided {

        @Override
        public void onCollision(Collider collidingObject, CollisionSide collisionSide) {
            // Not required here.
        }

        @Override
        public void remove() {
            // Not required here.
        }

        @Override
        public Optional<Node> getGameNode() {
            return null;
        }

        @Override
        public AnchorPoint getAnchorPoint() {
            return null;
        }

        @Override
        public void setOriginX(double x) {
            // Not required here.
        }

        @Override
        public void setOriginY(double y) {
            // Not required here.
        }

        @Override
        public void placeOnScene() {
            // Not required here.
        }

        @Override
        public double getRightX() {
            return 0;
        }

        @Override
        public double getLeftX() {
            return 0;
        }

        @Override
        public double getBottomY() {
            return 0;
        }

        @Override
        public double getTopY() {
            return 0;
        }

        @Override
        public void init(Injector injector) {
            // Not required here.
        }

        @Override
        public List<Timer> getTimers() {
            return null;
            // Not required here.
        }
    }

    private class ColliderTestEntity implements Entity, Collider {

        @Override
        public void remove() {
            // Not required here.
        }

        @Override
        public Optional<Node> getGameNode() {
            return null;
        }

        @Override
        public AnchorPoint getAnchorPoint() {
            return null;
        }

        @Override
        public void setOriginX(double x) {
            // Not required here.
        }

        @Override
        public void setOriginY(double y) {
            // Not required here.
        }

        @Override
        public void placeOnScene() {
            // Not required here.
        }

        @Override
        public double getRightX() {
            return 0;
        }

        @Override
        public double getLeftX() {
            return 0;
        }

        @Override
        public double getBottomY() {
            return 0;
        }

        @Override
        public double getTopY() {
            return 0;
        }

        @Override
        public void init(Injector injector) {
            // Not required here.
        }

        @Override
        public double getDirection() {
            return 0;
        }

        @Override
        public double getSpeed() {
            return 0;
        }

        @Override
        public List<Timer> getTimers() {
            return null;
            // Not required here.
        }
    }
}


