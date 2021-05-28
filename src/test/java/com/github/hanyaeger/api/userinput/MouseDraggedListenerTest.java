package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.exceptions.YaegerEngineException;
import com.github.hanyaeger.core.repositories.DragNDropRepository;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MouseDraggedListenerTest {

    private static final Coordinate2D LOCATION = new Coordinate2D(0, 0);
    private MouseDraggingEntity sut;
    private DragNDropRepository dragNDropRepository;
    private Node node;

    @BeforeEach
    void setup() {
        sut = new MouseDraggingEntity(LOCATION);
        node = mock(Node.class, withSettings().withoutAnnotations());
        dragNDropRepository = mock(DragNDropRepository.class);

        sut.setNode(node);
        sut.setDragNDropRepository(dragNDropRepository);
    }

    @Test
    void attachMouseEnterListenerThrowsExceptionWhenNotAttachedToEntity() {
        // Arrange
        var mouseListeningImpl = new MouseDraggedListeningImpl();
        mouseListeningImpl.setNode(node);

        // Act & Assert
        assertThrows(YaegerEngineException.class, () -> mouseListeningImpl.attachMouseDraggedListener());
    }

    @Test
    void attachMouseEnterListenerOnEntityAttachesSetOnDragDetected() {
        // Arrange

        // Act
        sut.attachMouseDraggedListener();

        // Assert
        verify(node).setOnDragDetected(any());
    }

    @Test
    void attachMouseEnterListenerOnEntityAttachesSetOnDragged() {
        // Arrange

        // Act
        sut.attachMouseDraggedListener();

        // Assert
        verify(node).setOnMouseDragged(any());
    }

    @Test
    void attachMouseEnterListenerOnEntityAttachesSetOnMouseReleased() {
        // Arrange

        // Act
        sut.attachMouseDraggedListener();

        // Assert
        verify(node).setOnMouseReleased(any());
    }

    @Test
    void callingEventFromOnDragDetectedEventHandlerStartsFullDrag() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachMouseDraggedListener();
        verify(node).setOnDragDetected(eventHandlerArgumentCaptor.capture());

        var mouseEvent = mock(MouseEvent.class);

        // Act
        eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

        // Assert
        verify(node).startFullDrag();
    }

    @Test
    void callingEventFromOnDragDetectedEventHandlerAddsEntityToTheDragNDropRepository() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachMouseDraggedListener();
        verify(node).setOnDragDetected(eventHandlerArgumentCaptor.capture());

        var mouseEvent = mock(MouseEvent.class);

        // Act
        eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

        // Assert
        verify(dragNDropRepository).setDraggedObject(sut);
    }

    @Test
    void callingEventFromOnDragDetectedEventHandlerConsumesEvent() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachMouseDraggedListener();
        verify(node).setOnDragDetected(eventHandlerArgumentCaptor.capture());

        var mouseEvent = mock(MouseEvent.class);

        // Act
        eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

        // Assert
        verify(mouseEvent).consume();
    }

    @Test
    void callingEventFromOnMouseDraggedHandlerCallsOnDragged() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachMouseDraggedListener();
        verify(node).setOnMouseDragged(eventHandlerArgumentCaptor.capture());

        var x = 37D;
        var y = 42D;

        var mouseEvent = mock(MouseEvent.class);
        when(mouseEvent.getX()).thenReturn(x);
        when(mouseEvent.getY()).thenReturn(y);

        // Act
        eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

        // Assert
        assertEquals(x, sut.getDraggedLocation().getX());
        assertEquals(y, sut.getDraggedLocation().getY());
    }

    @Test
    void callingEventFromOnMouseDraggedHandlerConsumesEvent() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachMouseDraggedListener();
        verify(node).setOnMouseDragged(eventHandlerArgumentCaptor.capture());

        var mouseEvent = mock(MouseEvent.class);

        // Act
        eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

        // Assert
        verify(mouseEvent).consume();
    }

    @Test
    void callingEventFromOnMouseReleasedHandlerCallsOnDraggedForPrimaryButton() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachMouseDraggedListener();
        verify(node).setOnMouseReleased(eventHandlerArgumentCaptor.capture());

        var x = 37D;
        var y = 42D;

        var mouseEvent = mock(MouseEvent.class);
        when(mouseEvent.getX()).thenReturn(x);
        when(mouseEvent.getY()).thenReturn(y);
        when(mouseEvent.getButton()).thenReturn(MouseButton.PRIMARY);

        // Act
        eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

        // Assert
        assertEquals(x, sut.getDroppedLocation().getX());
        assertEquals(y, sut.getDroppedLocation().getY());
    }

    @Test
    void callingEventFromOnMouseReleasedHandlerDoesNotingForSecondaryButton() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachMouseDraggedListener();
        verify(node).setOnMouseReleased(eventHandlerArgumentCaptor.capture());

        var x = 37D;
        var y = 42D;

        var mouseEvent = mock(MouseEvent.class);
        when(mouseEvent.getX()).thenReturn(x);
        when(mouseEvent.getY()).thenReturn(y);
        when(mouseEvent.getButton()).thenReturn(MouseButton.SECONDARY);

        // Act
        eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

        // Assert
        assertNull(sut.getDroppedLocation());
    }

    private static class MouseDraggedListeningImpl implements MouseDraggedListener {

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
