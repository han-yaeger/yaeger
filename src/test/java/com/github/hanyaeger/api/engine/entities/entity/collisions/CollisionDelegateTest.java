package com.github.hanyaeger.api.engine.entities.entity.collisions;

import com.github.hanyaeger.api.engine.Timer;
import com.github.hanyaeger.api.engine.entities.entity.AnchorPoint;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.motion.DefaultMotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplier;
import com.github.hanyaeger.api.guice.factories.MotionApplierFactory;
import com.google.inject.Injector;
import javafx.scene.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

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
        var collided = mock(Collided.class);
        var AABBCollider = mock(Collider.class);

        collisionDelegate.register(collided);
        collisionDelegate.register(AABBCollider);

        ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);

        // Act
        collisionDelegate.checkCollisions();

        // Assert
        Mockito.verify(collided).checkForCollisions(argument.capture());
        Assertions.assertEquals(1, argument.getValue().size());
    }

    @Test
    void entitiesGetCorrectlyAdded() {
        // Arrange
        YaegerEntity collidedEntity = mock(CollidedImpl.class);
        YaegerEntity colliderEntity = mock(ColliderImpl.class);

        collisionDelegate.register(collidedEntity);
        collisionDelegate.register(colliderEntity);

        ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);

        // Act
        collisionDelegate.checkCollisions();

        // Assert
        Mockito.verify((Collided) collidedEntity).checkForCollisions(argument.capture());
        Assertions.assertEquals(1, argument.getValue().size());
    }

    @Test
    void afterRemoveCollidedNoCollisionsAreChecked() {
        // Arrange
        YaegerEntity collidedEntity = mock(CollidedImpl.class);
        YaegerEntity colliderEntity = mock(ColliderImpl.class);

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
        YaegerEntity collidedEntity = mock(CollidedImpl.class);
        YaegerEntity colliderEntity = mock(ColliderImpl.class);

        collisionDelegate.register(collidedEntity);
        collisionDelegate.register(colliderEntity);

        ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);

        // Act
        collisionDelegate.remove(colliderEntity);
        collisionDelegate.checkCollisions();

        // Assert
        Mockito.verify((Collided) collidedEntity).checkForCollisions(argument.capture());
        Assertions.assertEquals(0, argument.getValue().size());
    }

    private class CollidedImpl extends YaegerEntity implements Collided {

        /**
         * Instantiate a new {@link YaegerEntity} for the given {@link Coordinate2D} and textDelegate.
         *
         * @param initialPosition the initial {@link Coordinate2D} of this {@link YaegerEntity}
         */
        public CollidedImpl(Coordinate2D initialPosition) {
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
        public Optional<? extends Node> getNode() {
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
        public void transferCoordinatesToNode() {
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
            // Not required here.
            return null;
        }

        @Override
        public void injectMotionApplierFactory(MotionApplierFactory motionApplierFactory) {
            // Not required here.
        }

        @Override
        public MotionApplier getMotionApplier() {
            // Not required here.
            return null;
        }

        @Override
        public void undoUpdate() {
            // Not required here
        }
    }

    private class ColliderImpl extends YaegerEntity implements Collider {

        /**
         * Instantiate a new {@link YaegerEntity} for the given {@link Coordinate2D} and textDelegate.
         *
         * @param initialPosition the initial {@link Coordinate2D} of this {@link YaegerEntity}
         */
        public ColliderImpl(Coordinate2D initialPosition) {
            super(initialPosition);
        }

        @Override
        public void remove() {
            // Not required here.
        }

        @Override
        public Optional<? extends Node> getNode() {
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
        public void transferCoordinatesToNode() {
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


