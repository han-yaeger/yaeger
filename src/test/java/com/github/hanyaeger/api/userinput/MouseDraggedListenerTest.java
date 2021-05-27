package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.exceptions.YaegerEngineException;
import com.github.hanyaeger.core.repositories.DragNDropRepository;
import javafx.scene.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class MouseDraggedListenerTest {

    private static final Coordinate2D LOCATION = new Coordinate2D(0, 0);
    private MouseDraggingEntity sut;
    private Node node;

    @BeforeEach
    void setup() {
        sut = new MouseDraggingEntity(LOCATION);
        node = mock(Node.class, withSettings().withoutAnnotations());

        sut.setNode(node);
    }

    @Test
    void attachMouseEnterListenerThrowsExceptionWhenNotAttachedToEntity() {
        // Arrange
        var mouseListeningImpl = new MouseDraggedListeningImpl();
        mouseListeningImpl.setNode(node);

        // Act & Assert
        Assertions.assertThrows(YaegerEngineException.class, () -> mouseListeningImpl.attachMouseDraggedListener());
    }

    @Test
    void attachMouseEnterListenerOnEntityAttachesSetOnDragDetected() {
        // Arrange

        // Act
        sut.attachMouseDraggedListener();

        // Arrange
        verify(node).setOnDragDetected(any());
    }

    @Test
    void attachMouseEnterListenerOnEntityAttachesSetOnDragged() {
        // Arrange

        // Act
        sut.attachMouseDraggedListener();

        // Arrange
        verify(node).setOnMouseDragged(any());
    }

    @Test
    void attachMouseEnterListenerOnEntityAttachesSetOnMouseReleased() {
        // Arrange

        // Act
        sut.attachMouseDraggedListener();

        // Arrange
        verify(node).setOnMouseReleased(any());
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
        public void onDragged(Coordinate2D coordinate2D) {

        }

        @Override
        public void onDropped(Coordinate2D coordinate2D) {

        }

        @Override
        public void setDragNDropRepository(DragNDropRepository dragNDropRepository) {

        }

        @Override
        public DragNDropRepository getDragNDropRepository() {
            return null;
        }
    }
}
