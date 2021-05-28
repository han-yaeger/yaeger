package com.github.hanyaeger.api.userinput;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class MouseExitListenerTest {

    private Node node;
    private MouseExitListeningImpl sut;

    @BeforeEach
    void setup() {
        sut = new MouseExitListeningImpl();

        node = mock(Node.class, withSettings().withoutAnnotations());
        sut.setNode(node);
    }

    @Test
    void attachMouseExitListenerAttachesMouseListener() {
        // Arrange

        // Act
        sut.attachMouseExitListener();

        // Assert
        verify(node).setOnMouseExited(any());
    }

    @Test
    void callingEventFromEventHandlerCallsConsumesOnEvent() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachMouseExitListener();
        verify(node).setOnMouseExited(eventHandlerArgumentCaptor.capture());

        var mouseEvent = mock(MouseEvent.class);

        // Act
        eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

        // Assert
        verify(mouseEvent).consume();
    }

    @Test
    void callingEventFromEventHandlerCallsOnMouseEntered() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachMouseExitListener();
        verify(node).setOnMouseExited(eventHandlerArgumentCaptor.capture());

        var mouseEvent = mock(MouseEvent.class);

        // Act
        eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

        // Assert
        assertTrue(sut.isCalled());
    }

    private static class MouseExitListeningImpl implements MouseExitListener {

        private Node node;
        private boolean called = false;

        @Override
        public void onMouseExited() {
            called = true;
        }

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        void setNode(Node node) {
            this.node = node;
        }

        public boolean isCalled() {
            return called;
        }
    }
}
