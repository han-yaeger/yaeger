package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class MouseButtonPressedListenerTest {

    @Test
    void attachMousePressedListenerAttachesMouseListener() {
        // Arrange
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var mouseListeningEntity = new MouseButtonPressedListeningInstancee();
        mouseListeningEntity.setNode(node);

        // Act
        mouseListeningEntity.attachMousePressedListener();

        // Assert
        verify(node).setOnMousePressed(any());
    }

    private class MouseButtonPressedListeningInstancee implements MouseButtonPressedListener {

        private Node node;

        @Override
        public void onMouseButtonPressed(final MouseButton button, final Coordinate2D coordinate2D) {
        }

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        void setNode(Node node) {
            this.node = node;
        }
    }
}
