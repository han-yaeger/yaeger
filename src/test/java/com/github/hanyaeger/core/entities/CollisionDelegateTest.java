package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Collidable;
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
    void nonCollidableReturnsFalseOnRegister() {
        // Act
        var normalEntity = mock(YaegerEntity.class);

        // Arrange
        boolean register = collisionDelegate.register(normalEntity);

        // Assert
        assertFalse(register);
    }

    @Test
    void collidableReturnsTrueOnRegister() {
        // Act
        YaegerEntity collider = mock(CollidableImpl.class);

        // Arrange
        boolean register = collisionDelegate.register(collider);

        // Assert
        assertTrue(register);
    }

    @Test
    void entityGetsCorrectlyAdded() {
        // Arrange
        YaegerEntity collidableEntity1 = mock(CollidableImpl.class);
        YaegerEntity collidableEntity2 = mock(CollidableImpl.class);

        collisionDelegate.register(collidableEntity1);
        collisionDelegate.register(collidableEntity2);

        ArgumentCaptor<List<Collidable>> argument = ArgumentCaptor.forClass(List.class);

        // Act
        collisionDelegate.checkCollisions();

        // Assert
        verify((Collidable) collidableEntity1).checkForCollisions(argument.capture());
        assertEquals(2, argument.getValue().size());
    }

    @Test
    void afterRemoveCollidableNoCollisionsAreChecked() {
        // Arrange
        YaegerEntity collidableEntity1 = mock(CollidableImpl.class);
        YaegerEntity collidableEntity2 = mock(CollidableImpl.class);

        collisionDelegate.register(collidableEntity1);
        collisionDelegate.register(collidableEntity2);

        // Act
        collisionDelegate.remove(collidableEntity2);
        collisionDelegate.checkCollisions();

        // Assert
        verifyNoMoreInteractions(collidableEntity1);
    }

    private static class CollidableImpl extends YaegerEntity implements Collidable {

        /**
         * Instantiate a new {@link YaegerEntity} for the given {@link Coordinate2D} and textDelegate.
         *
         * @param initialPosition the initial {@link Coordinate2D} of this {@link YaegerEntity}
         */
        public CollidableImpl(Coordinate2D initialPosition) {
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

        @Override
        public void onCollision(List<Collidable> collidingObjects) {

        }
    }
}


