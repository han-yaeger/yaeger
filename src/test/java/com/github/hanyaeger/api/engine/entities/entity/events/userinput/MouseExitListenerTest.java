package com.github.hanyaeger.api.engine.entities.entity.events.userinput;

import javafx.scene.Node;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class MouseExitListenerTest {

    @Test
    void attachMouseExitListenerAttachesMouseListener() {
        // Arrange
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var mouseListeningEntity = new MouseExitListeningInstancee();
        mouseListeningEntity.setNode(node);

        // Act
        mouseListeningEntity.attachMouseExitListener();

        // Assert
        verify(node).setOnMouseExited(any());
    }

    private class MouseExitListeningInstancee implements MouseExitListener {

        private Node node;

        @Override
        public void onMouseExited() {
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
