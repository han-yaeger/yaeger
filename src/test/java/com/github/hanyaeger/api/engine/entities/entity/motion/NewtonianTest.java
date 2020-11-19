package com.github.hanyaeger.api.engine.entities.entity.motion;

import com.github.hanyaeger.api.engine.entities.entity.AnchorPoint;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.guice.factories.MotionApplierFactory;
import javafx.scene.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NewtonianTest {

    private Moveable sut;

    @BeforeEach
    void setup() {
        sut = new NewtonianImpl();
    }

    @Test
    void getMotionModifierTypeReturnsNewtonian() {
        // Arrange

        // Act
        var actual = sut.getMotionModifierType();

        // Assert
        Assertions.assertEquals(MotionApplierType.NEWTONIAN, actual);
    }

    private class NewtonianImpl implements Newtonian {

        @Override
        public void injectMotionApplierFactory(MotionApplierFactory motionApplierFactory) {
            // Not required here
        }

        @Override
        public MotionApplier getMotionApplier() {
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
    }
}