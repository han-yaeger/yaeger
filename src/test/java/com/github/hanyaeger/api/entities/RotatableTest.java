package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.core.entities.motion.InitializationBuffer;
import javafx.scene.Node;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class RotatableTest {

    public static final int DEGREES = 37;

    @Test
    void setRotateDelegatesToNode() {
        // Arrange
        var sut = new RotatableImpl();
        var node = mock(Node.class, withSettings().withoutAnnotations());
        sut.setNode(node);

        // Act
        sut.setRotate(DEGREES);

        // Assert
        verify(node).setRotate(-DEGREES);
    }

    @Test
    void setRotateDelegatesToRotationBufferIfNodeNotAvailable() {
        // Arrange
        var sut = new RotatableImpl();
        var rotationBuffer = mock(InitializationBuffer.class);
        sut.setRotationBuffer(rotationBuffer);

        // Act
        sut.setRotate(DEGREES);

        // Assert
        verify(rotationBuffer).setRotation(DEGREES);
    }

    private static class RotatableImpl implements Rotatable {

        private Node node;
        private InitializationBuffer initializationBuffer;

        @Override
        public Optional<? extends Node> getNode() {
            if (node != null) {
                return Optional.of(node);
            } else {
                return Optional.empty();
            }
        }

        public void setNode(Node node) {
            this.node = node;
        }

        @Override
        public InitializationBuffer getInitializationBuffer() {
            return initializationBuffer;
        }

        public void setRotationBuffer(InitializationBuffer initializationBuffer) {
            this.initializationBuffer = initializationBuffer;
        }
    }

}
