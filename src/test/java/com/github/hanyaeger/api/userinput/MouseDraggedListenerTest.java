package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.Node;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class MouseDraggedListenerTest {

    @Test
    void attachMouseEnterListenerAttachesMouseListener() {
        // Arrange
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var mouseListeningEntity = new MouseDraggedListeningImpl();
        mouseListeningEntity.setNode(node);

        // Act
        mouseListeningEntity.attachMouseDraggedListener();

        // Assert
        verify(node).setOnMouseDragged(any());
    }

    private class MouseDraggedListeningImpl implements MouseDraggedListener {

        private Node node;

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        void setNode(Node node) {
            this.node = node;
        }

        @Override
        public void onMouseDragged(Coordinate2D coordinate2D) {

        }
    }

}
