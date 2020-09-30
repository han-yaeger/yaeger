package com.github.hanyaeger.api.engine.entities.entity;

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

    private class RotatableImpl implements Rotatable {

        private Node node;

        @Override
        public Optional<Node> getNode() {
            return Optional.of(node);
        }

        public void setNode(Node node) {
            this.node = node;
        }
    }

}
