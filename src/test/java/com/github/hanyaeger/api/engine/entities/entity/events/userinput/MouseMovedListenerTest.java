package com.github.hanyaeger.api.engine.entities.entity.events.userinput;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MouseMovedListenerTest {

    private static final Coordinate2D DEFAULT_LOCATION = new Coordinate2D(0, 0);

    @Test
    void attachMouseExitListenerAttachesMouseListener() {
        // Arrange
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var scene = mock(Scene.class);
        var mouseMovedListener = new MouseMovedListeningEntityImpl(DEFAULT_LOCATION);
        mouseMovedListener.setNode(node);

        when(node.getScene()).thenReturn(scene);

        // Act
        mouseMovedListener.attachMouseMovedListener();

        // Assert
        verify(scene).setOnMouseMoved(any());
    }

    private class MouseMovedListeningEntityImpl extends YaegerEntity implements MouseMovedListener {

        private Node node;

        /**
         * Create a new {@link YaegerEntity} on the given {@link Coordinate2D}.
         *
         * @param initialLocation the initial {@link Coordinate2D} of this {@link YaegerEntity}
         */
        protected MouseMovedListeningEntityImpl(Coordinate2D initialLocation) {
            super(initialLocation);
        }

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
