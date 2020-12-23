package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.entities.entity.AnchorPoint;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NewtonianTest {

    private NewtonianImpl sut;
    private static final double FRICTION_CONSTANT = 0.37;
    private static final double GRAVITATIONAL_CONSTANT = 0.42;
    private static final double GRAVITATIONAL_DIRECTION = Direction.DOWN.getValue();
    private static final boolean GRAVITATIONAL_PULL = false;

    @BeforeEach
    void setup() {
        sut = new NewtonianImpl();
    }

    @Nested
    class WithoutMotionApplierInjected {
        private EntityMotionInitBuffer buffer;

        @BeforeEach
        void setup() {
            buffer = mock(EntityMotionInitBuffer.class);
            sut.setBuffer(buffer);
        }

        @Test
        void setGravitationConstantDelegatesToBuffer() {
            // Arrange

            // Act
            sut.setGravityConstant(GRAVITATIONAL_CONSTANT);

            // Assert
            verify(buffer).setGravityConstant(GRAVITATIONAL_CONSTANT);
        }

        @Test
        void setGravitationDirectionDelegatesToBuffer() {
            // Arrange

            // Act
            sut.setGravityDirection(GRAVITATIONAL_DIRECTION);

            // Assert
            verify(buffer).setGravityDirection(GRAVITATIONAL_DIRECTION);
        }


        @Test
        void setFrictionConstantlDelegatesToBuffer() {
            // Arrange

            // Act
            sut.setFrictionConstant(FRICTION_CONSTANT);

            // Assert
            verify(buffer).setFrictionConstant(FRICTION_CONSTANT);
        }

        @Test
        void setGravitationalPullDelegatesToBuffer() {
            // Arrange

            // Act
            sut.setGravitationalPull(GRAVITATIONAL_PULL);

            // Assert
            verify(buffer).setGravitationalPull(GRAVITATIONAL_PULL);
        }
    }

    @Nested
    class WithMotionApplierInjected {
        private MotionApplier motionApplier;

        @BeforeEach
        void setup() {
            motionApplier = mock(MotionApplier.class);
            sut.setMotionApplier(motionApplier);
        }

        @Test
        void setGravitationConstantDelegatesToMotionApplier() {
            // Arrange

            // Act
            sut.setGravityConstant(GRAVITATIONAL_CONSTANT);

            // Assert
            verify(motionApplier).setGravityConstant(GRAVITATIONAL_CONSTANT);
        }

        @Test
        void setGravitationDirectionDelegatesToMotionApplier() {
            // Arrange

            // Act
            sut.setGravityDirection(GRAVITATIONAL_DIRECTION);

            // Assert
            verify(motionApplier).setGravityDirection(GRAVITATIONAL_DIRECTION);
        }


        @Test
        void setFrictionConstantlDelegatesToMotionApplier() {
            // Arrange

            // Act
            sut.setFrictionConstant(FRICTION_CONSTANT);

            // Assert
            verify(motionApplier).setFrictionConstant(FRICTION_CONSTANT);
        }

        @Test
        void setGravitationalPullDelegatesToMotionApplier() {
            // Arrange

            // Act
            sut.setGravitationalPull(GRAVITATIONAL_PULL);

            // Assert
            verify(motionApplier).setGravitationalPull(GRAVITATIONAL_PULL);
        }

        @Test
        void getGravitationConstantDelegatesToMotionApplier() {
            // Arrange
            when(motionApplier.getGravityConstant()).thenReturn(GRAVITATIONAL_CONSTANT);

            // Act
            var actual = sut.getGravityConstant();

            // Assert
            assertEquals(GRAVITATIONAL_CONSTANT, actual);
        }

        @Test
        void getGravitationDirectionDelegatesToMotionApplier() {
            // Arrange
            when(motionApplier.getGravityDirection()).thenReturn(GRAVITATIONAL_DIRECTION);

            // Act
            var actual = sut.getGravityDirection();

            // Assert
            assertEquals(GRAVITATIONAL_DIRECTION, actual);
        }

        @Test
        void getFrictionConstantDelegatesToMotionApplier() {
            // Arrange
            when(motionApplier.getFrictionConstant()).thenReturn(FRICTION_CONSTANT);

            // Act
            var actual = sut.getFrictionConstant();

            // Assert
            assertEquals(FRICTION_CONSTANT, actual);
        }

        @Test
        void isGravitationalPullDelegatesToMotionApplier() {
            // Arrange
            when(motionApplier.isGravitationalPull()).thenReturn(GRAVITATIONAL_PULL);

            // Act
            var actual = sut.isGravitationalPull();

            // Assert
            assertFalse(actual);
        }

        @Test
        void updateOnUpdatableDoesNotAddToMotionIfGravitationalPullIsFalse() {
            // Arrange
            when(motionApplier.isGravitationalPull()).thenReturn(false);
            when(motionApplier.getSpeed()).thenReturn(0d);

            var updatable = sut.addSimplePhysics();

            // Act
            updatable.update(0l);

            // Assert
            verify(motionApplier).isGravitationalPull();
        }

        @Test
        void updateOnUpdatableAddsToMotionIfGravitationalPullIsTrue() {
            // Arrange
            when(motionApplier.isGravitationalPull()).thenReturn(true);
            when(motionApplier.getGravityConstant()).thenReturn(GRAVITATIONAL_CONSTANT);
            when(motionApplier.getGravityDirection()).thenReturn(GRAVITATIONAL_DIRECTION);
            when(motionApplier.getSpeed()).thenReturn(0d);

            var updatable = sut.addSimplePhysics();

            // Act
            updatable.update(0l);

            // Assert
            verify(motionApplier).isGravitationalPull();
            verify(motionApplier).getGravityConstant();
            verify(motionApplier).getGravityDirection();
            verify(motionApplier).addToMotion(GRAVITATIONAL_CONSTANT, GRAVITATIONAL_DIRECTION);
        }

        @Test
        void updateOnUpdatableAddsFrictionIfSpeedGTZero() {
            // Arrange
            when(motionApplier.isGravitationalPull()).thenReturn(false);
            when(motionApplier.getFrictionConstant()).thenReturn(FRICTION_CONSTANT);
            when(motionApplier.getSpeed()).thenReturn(1d);

            var updatable = sut.addSimplePhysics();

            // Act
            updatable.update(0l);

            // Assert
            verify(motionApplier).isGravitationalPull();
            verify(motionApplier).getFrictionConstant();
            verify(motionApplier).incrementSpeed(-1 * FRICTION_CONSTANT);
        }
    }

    private class NewtonianImpl implements Newtonian {

        private MotionApplier motionApplier;
        private EntityMotionInitBuffer buffer;

        @Override
        public void setMotionApplier(final MotionApplier motionApplier) {
            this.motionApplier = motionApplier;
        }

        @Override
        public MotionApplier getMotionApplier() {
            return motionApplier;
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
            // Not required here
        }

        @Override
        public Coordinate2D getAnchorLocation() {
            return null;
        }

        @Override
        public void transferCoordinatesToNode() {
            // Not required here
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
        public Optional<? extends Node> getNode() {
            return Optional.empty();
        }

        @Override
        public Optional<EntityMotionInitBuffer> getBuffer() {
            if (buffer == null) {
                return Optional.empty();
            }
            return Optional.of(buffer);
        }

        public void setBuffer(final EntityMotionInitBuffer buffer) {
            this.buffer = buffer;
        }
    }
}