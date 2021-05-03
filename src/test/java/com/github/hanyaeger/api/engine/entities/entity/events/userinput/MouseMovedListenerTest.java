package com.github.hanyaeger.api.engine.entities.entity.events.userinput;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MouseMovedListenerTest {

    @Test
    void attachMouseExitListenerAttachesMouseListener() {
        // Arrange
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var scene = mock(Scene.class);
        var mouseMovedListener = new MouseMovedListenerImpl();
        mouseMovedListener.setNode(node);

        when(node.getScene()).thenReturn(scene);

        // Act
        mouseMovedListener.attachMouseMovedListener();

        // Assert
        verify(scene).setOnMouseMoved(any());
    }

    private class MouseMovedListenerImpl implements MouseMovedListener {

        private Node node;

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        void setNode(Node node) {
            this.node = node;
        }

        @Override
        public void onMouseMoved(Coordinate2D coordinate2D) {

        }
    }
}
