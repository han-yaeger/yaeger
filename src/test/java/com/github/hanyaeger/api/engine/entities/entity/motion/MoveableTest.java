package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.entities.entity.AnchorPoint;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MoveableTest {

    private MotionApplier motionApplier;
    private Moveable sut;

    private static final double DELTA = 0.00001d;
    private static final long TIMESTAMP = 0;
    private static final double SPEED = 3.7;
    private static final Direction DIRECTION_ENUM = Direction.RIGHT;
    private static final double DIRECTION = 42;

    @BeforeEach
    void setup() {
        motionApplier = mock(MotionApplier.class);

        sut = new MoveableImpl();
        sut.setMotionApplier(motionApplier);
    }

    @Test
    void setMotionDelegatesToMotionApplier() {
        // Arrange

        // Act
        sut.setMotion(SPEED, DIRECTION);

        // Assert
        verify(motionApplier).setMotion(SPEED, DIRECTION);
    }

    @Test
    void setMotionWithEnumDelegatesToMotionApplier() {
        // Arrange

        // Act
        sut.setMotion(SPEED, DIRECTION_ENUM);

        // Assert
        verify(motionApplier).setMotion(SPEED, DIRECTION_ENUM.getValue());
    }

    @Test
    void multiplySpeedDelegatesToMotionApplier() {
        // Arrange

        // Act
        sut.multiplySpeed(SPEED);

        // Assert
        verify(motionApplier).multiplySpeed(SPEED);
    }

    @Test
    void setSpeedDelegatesToMotionApplier() {
        // Arrange

        // Act
        sut.setSpeed(SPEED);

        // Assert
        verify(motionApplier).setSpeed(SPEED);
    }

    @Test
    void getSpeedDelegatesToMotionApplier() {
        // Arrange
        when(motionApplier.getSpeed()).thenReturn(SPEED);

        // Act
        double speed = sut.getSpeed();

        // Assert
        assertEquals(SPEED, speed, DELTA);
    }

    @Test
    void setDirectionDelegatesToMotionApplier() {
        // Arrange

        // Act
        sut.setDirection(DIRECTION);

        // Assert
        verify(motionApplier).setDirection(DIRECTION);
    }

    @Test
    void setDirectionEnumDelegatesToMotionApplier() {
        // Arrange

        // Act
        sut.setDirection(DIRECTION_ENUM);

        // Assert
        verify(motionApplier).setDirection(DIRECTION_ENUM.getValue());
    }

    @Test
    void alterSpeedDelegatesToMotionApplier() {
        // Arrange

        // Act
        sut.incrementSpeed(SPEED);

        // Assert
        verify(motionApplier).incrementSpeed(SPEED);
    }

    @Test
    void changeDirectionDelegatesToMotionApplier() {
        // Arrange

        // Act
        sut.changeDirection(DIRECTION);

        // Assert
        verify(motionApplier).changeDirection(DIRECTION);
    }

    @Test
    void addToMotionDelegatesToMotionApplier() {
        // Arrange

        // Act
        sut.addToMotion(SPEED, DIRECTION);

        // Assert
        verify(motionApplier).addToMotion(SPEED, DIRECTION);
    }

    @Test
    void addToMotionWithEnumDelegatesToMotionApplier() {
        // Arrange

        // Act
        sut.addToMotion(SPEED, DIRECTION_ENUM);

        // Assert
        verify(motionApplier).addToMotion(SPEED, DIRECTION_ENUM.getValue());
    }

    @Test
    void getDirectionDelegatesToMotionApplier() {
        // Arrange
        when(motionApplier.getDirection()).thenReturn(DIRECTION);

        // Act
        double direction = sut.getDirection();

        // Assert
        assertEquals(DIRECTION, direction, DELTA);
    }

    @Test
    void updateLocationReturnsAnUpdatable() {
        // Arrange

        // Act
        Updatable updatable = sut.updateLocation();

        // Assert
        assertNotNull(updatable);
    }

    @Test
    void callingTheUpdatableModifiesLocation() {
        // Arrange
        var anchorLocation = new Coordinate2D(37, 42);
        sut.setAnchorLocation(anchorLocation);
        Updatable updatable = sut.updateLocation();
        when(motionApplier.getSpeed()).thenReturn(1d);

        // Act
        updatable.update(TIMESTAMP);

        // Assert
        verify(motionApplier).updateLocation(any(Coordinate2D.class));
    }

    @Test
    void callingTheUpdatableWithZeroSpeedDoesNotDoAnything() {
        // Arrange
        Updatable updatable = sut.updateLocation();
        Node node = mock(Node.class, withSettings().withoutAnnotations());
        Bounds bounds = new BoundingBox(0, 0, 10, 10);
        when(node.getBoundsInParent()).thenReturn(bounds);
        when(motionApplier.getSpeed()).thenReturn(0d);

        ((MoveableImpl) sut).setGameNode(node);

        // Act
        updatable.update(TIMESTAMP);

        // Assert
        verify(motionApplier, never()).updateLocation(any(Point2D.class));
    }

    private class MoveableImpl implements Moveable {
        Coordinate2D anchorLocation;
        MotionApplier motionApplier;
        Node node;

        @Override
        public void setMotionApplier(final MotionApplier motionApplier) {
            this.motionApplier = motionApplier;
        }

        @Override
        public MotionApplier getMotionApplier() {
            return motionApplier;
        }

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        public void setGameNode(Node node) {
            this.node = node;
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
        public void setAnchorLocationX(double x) {
            // Not required here
        }

        @Override
        public void setAnchorLocationY(double y) {
            // Not required here
        }

        @Override
        public void setAnchorLocation(Coordinate2D anchorLocation) {
            this.anchorLocation = anchorLocation;
        }

        @Override
        public Coordinate2D getAnchorLocation() {
            return anchorLocation;
        }

        @Override
        public void transferCoordinatesToNode() {
            // Not required here
        }
    }
}

