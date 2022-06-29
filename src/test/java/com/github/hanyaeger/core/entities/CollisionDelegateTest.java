package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.google.inject.Injector;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
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
        var collider = mock(Collider.class);

        collisionDelegate.register(collided);
        collisionDelegate.register(collider);

        ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);

        // Act
        collisionDelegate.checkCollisions();

        // Assert
        verify(collided).checkForCollisions(argument.capture());
        assertEquals(1, argument.getValue().size());
    }

    @Test
    void nonColliderOrCollidedReturnsFalseOnRegister() {
        // Act
        var normalEntity = mock(YaegerEntity.class);

        // Arrange
        boolean register = collisionDelegate.register(normalEntity);

        // Assert
        assertFalse(register);
    }

    @Test
    void colliderReturnsTrueOnRegister() {
        // Act
        YaegerEntity collider = mock(ColliderImpl.class);

        // Arrange
        boolean register = collisionDelegate.register(collider);

        // Assert
        assertTrue(register);
    }

    @Test
    void collidedReturnsTrueOnRegister() {
        // Act
        YaegerEntity collided = mock(CollidedImpl.class);

        // Arrange
        boolean register = collisionDelegate.register(collided);

        // Assert
        assertTrue(register);
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
        verify((Collided) collidedEntity).checkForCollisions(argument.capture());
        assertEquals(1, argument.getValue().size());
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
        verifyNoMoreInteractions(collidedEntity);
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
        verify((Collided) collidedEntity).checkForCollisions(argument.capture());
        assertEquals(0, argument.getValue().size());
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
        public void onCollision(List<Collider> collidingObject) {
            // Not required here
        }

        @Override
        public void remove() {
            // Not required here
        }

        @Override
        public Optional<? extends Node> getNode() {
            return null;
        }

        @Override
        public void setAnchorPoint(AnchorPoint anchorPoint) {
            // Not required here
        }

        @Override
        public AnchorPoint getAnchorPoint() {
            return null;
        }

        @Override
        public void transferCoordinatesToNode() {
            // Not required here
        }

        @Override
        public void init(Injector injector) {
            // Not required here
        }

        @Override
        public List<Timer> getTimers() {
            return null;
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
            // Not required here
        }

        @Override
        public Optional<? extends Node> getNode() {
            return null;
        }

        @Override
        public void setAnchorPoint(AnchorPoint anchorPoint) {
            // Not required here
        }

        @Override
        public AnchorPoint getAnchorPoint() {
            return null;
        }

        @Override
        public void transferCoordinatesToNode() {
            // Not required here
        }

        @Override
        public void init(Injector injector) {
            // Not required here
        }

        @Override
        public List<Timer> getTimers() {
            return null;
        }
    }
}


