package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MouseButtonReleasedListenerTest {

    private Node node;
    private MouseButtonReleasedListeningImpl sut;

    @BeforeEach
    void setup() {
        sut = new MouseButtonReleasedListeningImpl();

        node = mock(Node.class, withSettings().withoutAnnotations());
        sut.setNode(node);
    }

    @Test
    void attachMousePressedListenerAttachesMouseListener() {
        // Arrange

        // Act
        sut.attachMouseReleasedListener();

        // Assert
        verify(node).setOnMouseReleased(any());
    }

    @Test
    void callingEventFromEventHandlerCallsonMouseButtonPressedWithCorrectCoordinates() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachMouseReleasedListener();
        verify(node).setOnMouseReleased(eventHandlerArgumentCaptor.capture());

        var x = 37D;
        var y = 42D;

        var mouseEvent = mock(MouseEvent.class);
        when(mouseEvent.getX()).thenReturn(x);
        when(mouseEvent.getY()).thenReturn(y);

        // Act
        eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

        // Assert
        assertEquals(x, sut.getReleasedCoordinates().getX());
        assertEquals(y, sut.getReleasedCoordinates().getY());
    }

    @Test
    void callingEventFromEventHandlerCallsOnMouseButtonPressedWithCorrectButton() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachMouseReleasedListener();
        verify(node).setOnMouseReleased(eventHandlerArgumentCaptor.capture());

        var mouseEvent = mock(MouseEvent.class);
        var x = 37D;
        var y = 42D;

        when(mouseEvent.getX()).thenReturn(x);
        when(mouseEvent.getY()).thenReturn(y);

        var expectedButton = MouseButton.PRIMARY;
        when(mouseEvent.getButton()).thenReturn(expectedButton);

        // Act
        eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

        // Assert
        assertEquals(expectedButton, sut.getButton());
    }

    private class MouseButtonReleasedListeningImpl implements MouseButtonReleasedListener {

        private Node node;
        private Coordinate2D releasedCoordinates;
        private MouseButton button;

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        void setNode(Node node) {
            this.node = node;
        }

        @Override
        public void onMouseButtonReleased(final MouseButton button, final Coordinate2D coordinate2D) {
            this.releasedCoordinates = coordinate2D;
            this.button = button;
        }

        public Coordinate2D getReleasedCoordinates() {
            return releasedCoordinates;
        }

        public MouseButton getButton() {
            return button;
        }
    }
}
