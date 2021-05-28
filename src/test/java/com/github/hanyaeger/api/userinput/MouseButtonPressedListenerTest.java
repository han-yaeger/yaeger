package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MouseButtonPressedListenerTest {

    private Node node;
    private MouseButtonPressedListeningImpl sut;

    @BeforeEach
    void setup() {
        sut = new MouseButtonPressedListeningImpl();

        node = mock(Node.class, withSettings().withoutAnnotations());
        sut.setNode(node);
    }

    @Test
    void attachMousePressedListenerAttachesMouseListener() {
        // Arrange

        // Act
        sut.attachMousePressedListener();

        // Assert
        verify(node).setOnMousePressed(any());
    }

    @Test
    void callingEventFromEventHandlerCallsonMouseButtonPressedWithCorrectCoordinates() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachMousePressedListener();
        verify(node).setOnMousePressed(eventHandlerArgumentCaptor.capture());

        var x = 37D;
        var y = 42D;

        var mouseEvent = mock(MouseEvent.class);
        when(mouseEvent.getX()).thenReturn(x);
        when(mouseEvent.getY()).thenReturn(y);

        // Act
        eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

        // Assert
        assertEquals(x, sut.getPressedCoordinates().getX());
        assertEquals(y, sut.getPressedCoordinates().getY());
    }

    @Test
    void callingEventFromEventHandlerCallsonMouseButtonPressedWithCorrectButton() {
        // Arrange
        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        sut.attachMousePressedListener();
        verify(node).setOnMousePressed(eventHandlerArgumentCaptor.capture());


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

    private static class MouseButtonPressedListeningImpl implements MouseButtonPressedListener {

        private Node node;
        private Coordinate2D pressedCoordinates;
        private MouseButton button;

        @Override
        public void onMouseButtonPressed(final MouseButton button, final Coordinate2D coordinate2D) {
            this.pressedCoordinates = coordinate2D;
            this.button = button;
        }

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        void setNode(Node node) {
            this.node = node;
        }

        public Coordinate2D getPressedCoordinates() {
            return pressedCoordinates;
        }

        public MouseButton getButton() {
            return button;
        }
    }
}
