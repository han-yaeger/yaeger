package com.github.hanyaeger.api.engine.userinput;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MouseButtonReleasedListenerTest {

    @Test
    void attachMousePressedListenerAttachesMouseListener() {
        // Arrange
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var mouseListeningEntity = new MouseButtonReleasedListeningInstancee();
        mouseListeningEntity.setNode(node);

        // Act
        mouseListeningEntity.attachMouseReleasedListener();

        // Assert
        verify(node).setOnMouseReleased(any());
    }

    private class MouseButtonReleasedListeningInstancee implements MouseButtonReleasedListener {

        private Node node;

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        void setNode(Node node) {
            this.node = node;
        }

        @Override
        public void onMouseButtonReleased(final MouseButton button, final Coordinate2D coordinate2D) {

        }
    }
}
