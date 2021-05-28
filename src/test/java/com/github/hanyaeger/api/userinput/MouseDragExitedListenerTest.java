package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.repositories.DragNDropRepository;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseDragEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class MouseDragExitedListenerTest {

    private MouseDragExitedListeningImpl sut;
    private DragNDropRepository dragNDropRepository;
    private Node node;

    @BeforeEach
    void setup() {
        sut = new MouseDragExitedListeningImpl();
        node = mock(Node.class, withSettings().withoutAnnotations());
        dragNDropRepository = mock(DragNDropRepository.class);

        sut.setNode(node);
        sut.setDragNDropRepository(dragNDropRepository);
    }

    @Test
    void attachDragEnteredListenerThrowsExceptionWhenNotAttachedToEntity() {
        // Arrange

        // Act
        sut.attachDragExitedListener();

        // Assert
        verify(node).setOnMouseDragExited(any());
    }

    @Test
    void callingEventFromEventHandlerClearsRepository() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachDragExitedListener();
        verify(node).setOnMouseDragExited(eventHandlerArgumentCaptor.capture());

        var mouseEvent = mock(MouseDragEvent.class);

        // Act
        eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

        // Assert
        verify(dragNDropRepository).getDraggedObject();
    }

    @Test
    void callingEventFromEventHandlerConsumesEvent() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachDragExitedListener();
        verify(node).setOnMouseDragExited(eventHandlerArgumentCaptor.capture());

        var mouseEvent = mock(MouseDragEvent.class);

        // Act
        eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

        // Assert
        verify(mouseEvent).consume();
    }

    @Test
    void callingEventFromEventHandlerCallsOnDraggedWithEventCoordinates() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachDragExitedListener();
        verify(node).setOnMouseDragExited(eventHandlerArgumentCaptor.capture());

        var x = 37D;
        var y = 42D;

        var mouseEvent = mock(MouseDragEvent.class);
        when(mouseEvent.getX()).thenReturn(x);
        when(mouseEvent.getY()).thenReturn(y);

        // Act
        eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

        // Assert
        assertEquals(x, sut.getDroppedCoordinate2D().getX());
        assertEquals(y, sut.getDroppedCoordinate2D().getY());
    }

    @Test
    void callingEventFromEventHandlerCallsOnDraggedWithCorrectSource() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachDragExitedListener();
        verify(node).setOnMouseDragExited(eventHandlerArgumentCaptor.capture());

        var x = 37D;
        var y = 42D;

        var mouseEvent = mock(MouseDragEvent.class);
        when(mouseEvent.getX()).thenReturn(x);
        when(mouseEvent.getY()).thenReturn(y);

        var expectedSource = mock(MouseDraggedListener.class);
        when(dragNDropRepository.getDraggedObject()).thenReturn(expectedSource);

        // Act
        eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

        // Assert
        assertEquals(expectedSource, sut.getSource());
    }

    private static class MouseDragExitedListeningImpl implements MouseDragExitedListener {

        private Node node;
        private DragNDropRepository dragNDropRepository;
        private Coordinate2D droppedCoordinate2D;
        private MouseDraggedListener source;

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        void setNode(Node node) {
            this.node = node;
        }

        @Override
        public void setDragNDropRepository(DragNDropRepository dragNDropRepository) {
            this.dragNDropRepository = dragNDropRepository;
        }

        @Override
        public DragNDropRepository getDragNDropRepository() {
            return dragNDropRepository;
        }

        @Override
        public void onDragExited(Coordinate2D coordinate2D, MouseDraggedListener source) {
            this.droppedCoordinate2D = coordinate2D;
            this.source = source;
        }

        public Coordinate2D getDroppedCoordinate2D() {
            return droppedCoordinate2D;
        }

        public MouseDraggedListener getSource() {
            return source;
        }
    }
}


