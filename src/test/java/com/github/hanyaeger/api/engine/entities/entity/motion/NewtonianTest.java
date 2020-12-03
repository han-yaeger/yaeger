package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.entities.entity.AnchorPoint;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NewtonianTest {

    private NewtonianImpl sut;
    private MotionApplier motionApplier;

    @BeforeEach
    void setup() {
        sut = new NewtonianImpl();

        motionApplier = mock(MotionApplier.class);
        sut.setMotionApplier(motionApplier);
    }

    @Test
    void setGravitationalPullDelegatesToMotionApplier() {
        // Arrange

        // Act
        sut.setGravitationalPull(true);

        // Assert
        verify(motionApplier).setGravitationalPull(true);
    }

    @Test
    void isGravitationalPullDelegatesToMotionApplier() {
        // Arrange
        when(motionApplier.isGravitationalPull()).thenReturn(false);

        // Act
        var actual = sut.isGravitationalPull();

        // Assert
        assertFalse(actual);
    }

    private class NewtonianImpl implements Newtonian {

        private MotionApplier motionApplier;

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
        public void addToMotion(double speed, double direction) {
            // Not required here
        }

        @Override
        public void addToMotion(double speed, Direction direction) {
            // Not required here
        }
    }
}