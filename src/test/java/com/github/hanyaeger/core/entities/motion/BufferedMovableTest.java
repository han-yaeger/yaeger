package com.github.hanyaeger.core.entities.motion;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Direction;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class BufferedMovableTest {

    public static final int SPEED = 37;
    public static final int DIRECTION = 42;
    public static final Direction DIRECTION_ENUM = Direction.DOWN;
    private BufferedMovableImpl sut;
    private MotionApplier motionApplier;

    @BeforeEach
    void setup() {
        sut = new BufferedMovableImpl();
        motionApplier = mock(MotionApplier.class);
    }

    @Nested
    class WithBuffer {

        private EntityMotionInitBuffer buffer;

        @BeforeEach
        void setup() {
            buffer = mock(EntityMotionInitBuffer.class);
            sut.setBuffer(Optional.of(buffer));
        }

        @Test
        void ifMotionApplierIsNotSetBufferIsUsedForSpeed() {
            // Arrange

            // Act
            sut.setSpeed(SPEED);

            // Assert
            verify(buffer).setSpeed(SPEED);
        }

        @Test
        void ifMotionApplierIsNotSetBufferIsUsedForDirection() {
            // Arrange

            // Act
            sut.setDirection(DIRECTION);

            // Assert
            verify(buffer).setDirection(DIRECTION);
        }

        @Test
        void ifMotionApplierIsNotSetBufferIsUsedForMotion() {
            // Arrange

            // Act
            sut.setMotion(SPEED, DIRECTION);

            // Assert
            verify(buffer).setMotion(SPEED, DIRECTION);
        }
    }

    @Nested
    class WithoutBuffer {

        @BeforeEach
        void setup() {
            sut.setBuffer(Optional.empty());
            sut.setMotionApplier(motionApplier);
        }

        @Test
        void ifMotionApplierIsSetMotionApplierIsUsedForSpeed() {
            // Arrange

            // Act
            sut.setSpeed(SPEED);

            // Assert
            verify(motionApplier).setSpeed(SPEED);
        }

        @Test
        void ifMotionApplierIsSetMotionApplierIsUsedForDirection() {
            // Arrange

            // Act
            sut.setDirection(DIRECTION);

            // Assert
            verify(motionApplier).setDirection(DIRECTION);
        }

        @Test
        void ifMotionApplierIsSetMotionApplierIsUsedForDirectionEnum() {
            // Arrange

            // Act
            sut.setDirection(DIRECTION_ENUM);

            // Assert
            verify(motionApplier).setDirection(DIRECTION_ENUM.getValue());
        }

        @Test
        void ifMotionApplierIsSetMotionApplierIsUsedForMotion() {
            // Arrange

            // Act
            sut.setMotion(SPEED, DIRECTION);

            // Assert
            verify(motionApplier).setMotion(SPEED, DIRECTION);
        }

        @Test
        void ifMotionApplierIsSetMotionApplierIsUsedForMotionWithDirectionEnum() {
            // Arrange

            // Act
            sut.setMotion(SPEED, DIRECTION_ENUM);

            // Assert
            verify(motionApplier).setMotion(SPEED, DIRECTION_ENUM.getValue());
        }
    }

    private static class BufferedMovableImpl implements BufferedMovable {

        private Optional<EntityMotionInitBuffer> buffer;
        private MotionApplier motionApplier;

        @Override
        public Optional<EntityMotionInitBuffer> getBuffer() {
            return buffer;
        }

        @Override
        public void setMotionApplier(MotionApplier motionApplier) {
            this.motionApplier = motionApplier;
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
