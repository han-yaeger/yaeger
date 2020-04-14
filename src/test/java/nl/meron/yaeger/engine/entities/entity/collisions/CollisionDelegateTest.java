package nl.meron.yaeger.engine.entities.entity.collisions;

import com.google.inject.Injector;
import javafx.scene.Node;
import nl.meron.yaeger.engine.Timer;
import nl.meron.yaeger.engine.entities.entity.AnchorPoint;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.YaegerEntity;
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
        // Arrange
        AABBCollided collided = mock(AABBCollided.class);
        Collider collider = mock(Collider.class);

        collisionDelegate.register(collided);
        collisionDelegate.register(collider);

        ArgumentCaptor<Set> argument = ArgumentCaptor.forClass(Set.class);

        // Act
        collisionDelegate.checkCollisions();

        // Assert
        Mockito.verify(collided).checkForCollisions(argument.capture());
        Assertions.assertEquals(1, argument.getValue().size());
    }

    @Test
    void entitiesGetCorrectlyAdded() {
        // Arrange
        YaegerEntity collidedEntity = mock(CollidedTestEntity.class);
        YaegerEntity colliderEntity = mock(ColliderTestEntity.class);

        collisionDelegate.register(collidedEntity);
        collisionDelegate.register(colliderEntity);

        ArgumentCaptor<Set> argument = ArgumentCaptor.forClass(Set.class);

        // Act
        collisionDelegate.checkCollisions();

        // Assert
        Mockito.verify((AABBCollided) collidedEntity).checkForCollisions(argument.capture());
        Assertions.assertEquals(1, argument.getValue().size());
    }

    @Test
    void afterRemoveCollidedNoCollisionsAreChecked() {
        // Arrange
        YaegerEntity collidedEntity = mock(CollidedTestEntity.class);
        YaegerEntity colliderEntity = mock(ColliderTestEntity.class);

        collisionDelegate.register(collidedEntity);
        collisionDelegate.register(colliderEntity);

        // Act
        collisionDelegate.remove(collidedEntity);
        collisionDelegate.checkCollisions();

        // Assert
        Mockito.verifyNoMoreInteractions(collidedEntity);
    }

    @Test
    void afterRemoveColliderNoCollisionsAreReported() {
        // Arrange
        YaegerEntity collidedEntity = mock(CollidedTestEntity.class);
        YaegerEntity colliderEntity = mock(ColliderTestEntity.class);

        collisionDelegate.register(collidedEntity);
        collisionDelegate.register(colliderEntity);

        ArgumentCaptor<Set> argument = ArgumentCaptor.forClass(Set.class);

        // Act
        collisionDelegate.remove(colliderEntity);
        collisionDelegate.checkCollisions();

        // Assert
        Mockito.verify((AABBCollided) collidedEntity).checkForCollisions(argument.capture());
        Assertions.assertEquals(0, argument.getValue().size());
    }

    private class CollidedTestEntity extends YaegerEntity implements AABBCollided {

        /**
         * Instantiate a new {@link YaegerEntity} for the given {@link Location} and textDelegate.
         *
         * @param initialPosition the initial {@link Location} of this {@link YaegerEntity}
         */
        public CollidedTestEntity(Location initialPosition) {
            super(initialPosition);
        }

        @Override
        public void onCollision(Collider collidingObject) {
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
        public void setAnchorPoint(AnchorPoint anchorPoint) {
            // Not required here.
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

    private class ColliderTestEntity extends YaegerEntity implements Collider {

        /**
         * Instantiate a new {@link YaegerEntity} for the given {@link Location} and textDelegate.
         *
         * @param initialPosition the initial {@link Location} of this {@link YaegerEntity}
         */
        public ColliderTestEntity(Location initialPosition) {
            super(initialPosition);
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
        public void setAnchorPoint(AnchorPoint anchorPoint) {
            // Not required here.
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


