package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.entities.entity.AnchorPoint;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class BufferedMoveableTest {

    public static final int SPEED = 37;
    public static final int DIRECTION = 42;
    private BufferedMoveableImpl sut;
    private EntityMotionInitBuffer buffer;
    private DefaultMotionApplier motionApplier;

    @BeforeEach
    void setup() {
        this.sut = new BufferedMoveableImpl();
        this.motionApplier = mock(DefaultMotionApplier.class);
        this.buffer = mock(EntityMotionInitBuffer.class);
        sut.setBuffer(Optional.of(buffer));
    }

    @Test
    void ifMotionApplierIsNotSetBufferIsUsedForSpeed() {
        // Arrange

        // Act
        sut.setSpeedTo(SPEED);

        // Assert
        verify(buffer).setSpeedTo(SPEED);
    }

    @Test
    void ifMotionApplierIsNotSetBufferIsUsedForDirection() {
        // Arrange

        // Act
        sut.setDirectionTo(DIRECTION);

        // Assert
        verify(buffer).setDirectionTo(DIRECTION);
    }

    @Test
    void ifMotionApplierIsNotSetBufferIsUsedForMotion() {
        // Arrange

        // Act
        sut.setMotionTo(SPEED, DIRECTION);

        // Assert
        verify(buffer).setMotionTo(SPEED, DIRECTION);
    }

    @Test
    void ifMotionApplierIsSetMotionApplierIsUsedForSpeed() {
        // Arrange
        sut.setBuffer(Optional.empty());
        sut.setMotionApplier(motionApplier);

        // Act
        sut.setSpeedTo(SPEED);

        // Assert
        verify(motionApplier).setSpeedTo(SPEED);
    }

    @Test
    void ifMotionApplierIsSetMotionApplierIsUsedForDirection() {
        // Arrange
        sut.setBuffer(Optional.empty());
        sut.setMotionApplier(motionApplier);

        // Act
        sut.setDirectionTo(DIRECTION);

        // Assert
        verify(motionApplier).setDirectionTo(DIRECTION);
    }

    @Test
    void ifMotionApplierIsSetMotionApplierIsUsedForMotion() {
        // Arrange
        sut.setBuffer(Optional.empty());
        sut.setMotionApplier(motionApplier);

        // Act
        sut.setMotionTo(SPEED, DIRECTION);

        // Assert
        verify(motionApplier).setMotionTo(SPEED, DIRECTION);
    }

    private class BufferedMoveableImpl implements BufferedMoveable {

        private Optional<EntityMotionInitBuffer> buffer;
        private MotionApplier motionApplier;

        @Override
        public Optional<EntityMotionInitBuffer> getBuffer() {
            return buffer;
        }

        @Override
        public void setMotionApplier(DefaultMotionApplier motionApplier) {
            this.motionApplier = motionApplier;
        }

        @Override
        public MotionApplier getMotionApplier() {
            return motionApplier;
        }

        @Override
        public Optional<Node> getNode() {
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
        public void setReferenceX(double x) {
            // Not required here
        }

        @Override
        public void setReferenceY(double y) {
            // Not required here
        }

        @Override
        public void transferCoordinatesToNode() {

        }
    }
}
