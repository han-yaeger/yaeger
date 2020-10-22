package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.entities.entity.motion.Rotatable;
import com.github.hanyaeger.api.engine.entities.entity.motion.RotationBuffer;
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
        var rotationBuffer = mock(RotationBuffer.class);
        sut.setRotationBuffer(rotationBuffer);

        // Act
        sut.setRotate(DEGREES);

        // Assert
        verify(rotationBuffer).setRotation(DEGREES);
    }

    private class RotatableImpl implements Rotatable {

        private Node node;
        private RotationBuffer rotationBuffer;

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
        public RotationBuffer getRotationBuffer() {
            return rotationBuffer;
        }

        public void setRotationBuffer(RotationBuffer rotationBuffer) {
            this.rotationBuffer = rotationBuffer;
        }
    }

}
