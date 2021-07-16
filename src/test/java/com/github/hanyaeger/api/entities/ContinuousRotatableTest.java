package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.core.entities.motion.InitializationBuffer;
import javafx.scene.Node;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class ContinuousRotatableTest {

    public static final int ROTATION_ANGLE = 37;

    @Test
    void setRotationAngleIsUsedForRotationIncrement() {
        // Arrange
        var sut = new ContinuousRotatableImpl();
        var node = mock(Node.class, withSettings().withoutAnnotations());
        sut.setNode(node);
        sut.setRotationSpeed(ROTATION_ANGLE);
        when(node.getRotate()).thenReturn(0d);

        // Act
        var updatable = sut.applyRotation();
        updatable.update(1L);

        // Assert
        verify(node).setRotate(-37d);
    }

    private static class ContinuousRotatableImpl implements ContinuousRotatable {

        private double rotationAngle;

        @Override
        public double getRotationSpeed() {
            return rotationAngle;
        }

        private Node node;

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        public void setNode(Node node) {
            this.node = node;
        }

        @Override
        public void setRotationSpeed(double rotationAngle) {
            this.rotationAngle = rotationAngle;
        }

        @Override
        public InitializationBuffer getInitializationBuffer() {
            return null;
        }
    }
}
