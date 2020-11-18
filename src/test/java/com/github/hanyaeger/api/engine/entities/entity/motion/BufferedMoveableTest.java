package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.entities.entity.AnchorPoint;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.guice.factories.MotionApplierFactory;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class BufferedMoveableTest {

    public static final int SPEED = 37;
    public static final int DIRECTION = 42;
    public static final Direction DIRECTION_ENUM = Direction.DOWN;
    private BufferedMoveableImpl sut;
    private EntityMotionInitBuffer buffer;
    private MotionApplierFactory motionApplierFactory;
    private MotionApplier motionApplier;

    @BeforeEach
    void setup() {
        sut = new BufferedMoveableImpl();
        motionApplierFactory = mock(MotionApplierFactory.class);
        motionApplier = mock(DefaultMotionApplier.class);
        buffer = mock(EntityMotionInitBuffer.class);

        when(motionApplierFactory.create(any(MotionApplierType.class))).thenReturn(motionApplier);

        sut.setBuffer(Optional.of(buffer));
    }

    @Test
    void ifMotionApplierIsNotSetBufferIsUsedForSpeed() {
        // Arrange

        // Act
        sut.setSpeed(SPEED);

        // Assert
        verify(buffer).setSpeedTo(SPEED);
    }

    @Test
    void ifMotionApplierIsNotSetBufferIsUsedForDirection() {
        // Arrange

        // Act
        sut.setDirection(DIRECTION);

        // Assert
        verify(buffer).setDirectionTo(DIRECTION);
    }

    @Test
    void ifMotionApplierIsNotSetBufferIsUsedForMotion() {
        // Arrange

        // Act
        sut.setMotion(SPEED, DIRECTION);

        // Assert
        verify(buffer).setMotionTo(SPEED, DIRECTION);
    }

    @Test
    void ifMotionApplierIsSetMotionApplierIsUsedForSpeed() {
        // Arrange
        sut.setBuffer(Optional.empty());
        sut.injectMotionApplierFactory(motionApplierFactory);

        // Act
        sut.setSpeed(SPEED);

        // Assert
        verify(motionApplier).setSpeed(SPEED);
    }

    @Test
    void ifMotionApplierIsSetMotionApplierIsUsedForDirection() {
        // Arrange
        sut.setBuffer(Optional.empty());
        sut.injectMotionApplierFactory(motionApplierFactory);

        // Act
        sut.setDirection(DIRECTION);

        // Assert
        verify(motionApplier).setDirection(DIRECTION);
    }

    @Test
    void ifMotionApplierIsSetMotionApplierIsUsedForDirectionEnum() {
        // Arrange
        sut.setBuffer(Optional.empty());
        sut.injectMotionApplierFactory(motionApplierFactory);

        // Act
        sut.setDirection(DIRECTION_ENUM);

        // Assert
        verify(motionApplier).setDirection(DIRECTION_ENUM.getValue());
    }

    @Test
    void ifMotionApplierIsSetMotionApplierIsUsedForMotion() {
        // Arrange
        sut.setBuffer(Optional.empty());
        sut.injectMotionApplierFactory(motionApplierFactory);

        // Act
        sut.setMotion(SPEED, DIRECTION);

        // Assert
        verify(motionApplier).setMotion(SPEED, DIRECTION);
    }

    @Test
    void ifMotionApplierIsSetMotionApplierIsUsedForMotionWithDirectionEnum() {
        // Arrange
        sut.setBuffer(Optional.empty());
        sut.injectMotionApplierFactory(motionApplierFactory);

        // Act
        sut.setMotion(SPEED, DIRECTION_ENUM);

        // Assert
        verify(motionApplier).setMotion(SPEED, DIRECTION_ENUM.getValue());
    }

    private class BufferedMoveableImpl implements BufferedMoveable {

        private Optional<EntityMotionInitBuffer> buffer;
        private MotionApplier motionApplier;

        @Override
        public Optional<EntityMotionInitBuffer> getBuffer() {
            return buffer;
        }

        @Override
        public void injectMotionApplierFactory(MotionApplierFactory motionApplierFactory) {
            this.motionApplier = motionApplierFactory.create(MotionApplierType.DEFAULT);
        }

        @Override
        public MotionApplier getMotionApplier() {
            return motionApplier;
        }

        @Override
        public Optional<? extends Node> getNode() {
            return null;
        }

        public void setBuffer(Optional<EntityMotionInitBuffer> buffer) {
            this.buffer = buffer;
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
        public void setAnchorLocationX(double x) {
            // Not required here.
        }

        @Override
        public void setAnchorLocationY(double y) {
            // Not required here.
        }

        @Override
        public void setAnchorLocation(Coordinate2D anchorLocation) {
            // Not required here.
        }

        @Override
        public Coordinate2D getAnchorLocation() {
            // Not required here.
            return null;
        }

        @Override
        public void transferCoordinatesToNode() {
            // Not required here.
        }
    }
}
