package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.scenes.ScrollableDynamicScene;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MouseButtonReleasedListenerTest {

    private Node node;

    @BeforeEach
    void setup() {
        node = mock(Node.class, withSettings().withoutAnnotations());
    }

    @Nested
    class GenericMouseButtonReleasedListener {
        private MouseButtonReleasedListeningImpl sut;

        @BeforeEach
        void setup() {
            sut = new MouseButtonReleasedListeningImpl();
            sut.setNode(node);
        }

        @Test
        void attachMousePressedListenerAttachesMouseListener() {
            // Arrange

            // Act
            sut.attachMouseButtonReleasedListener();

            // Assert
            verify(node).setOnMouseReleased(any());
        }

        @Test
        void callingEventFromEventHandlerCallsOnMouseButtonPressedWithCorrectCoordinates() {
            // Arrange
            ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
            sut.attachMouseButtonReleasedListener();
            verify(node).setOnMouseReleased(eventHandlerArgumentCaptor.capture());

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
        void callingEventFromEventHandlerCallsOnMouseButtonPressedWithCorrectButton() {
            // Arrange
            ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
            sut.attachMouseButtonReleasedListener();
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
    }

    @Nested
    class ScrollingSceneMousePressedListener {

        private MouseButtonReleasedListeningScrollableSceneImpl sut;
        private Pane pane;

        @BeforeEach
        void setup() {
            pane = mock(Pane.class);

            sut = new MouseButtonReleasedListeningScrollableSceneImpl();
            sut.setNode(node);
            sut.setPane(pane);
        }

        @Test
        void attachMousePressedListenerAttachesMouseListener() {
            // Arrange

            // Act
            sut.attachMouseButtonReleasedListener();

            // Assert
            verify(pane).setOnMouseReleased(any());
        }
    }

    private static class MouseButtonReleasedListeningImpl implements MouseButtonReleasedListener {

        private Node node;
        private Coordinate2D pressedCoordinates;
        private MouseButton button;

        @Override
        public void onMouseButtonReleased(final MouseButton button, final Coordinate2D coordinate2D) {
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

    private static class MouseButtonReleasedListeningScrollableSceneImpl extends ScrollableDynamicScene implements MouseButtonReleasedListener {

        private Node node;
        private Pane pane;
        private Coordinate2D coordinate;

        public void setNode(Node node) {
            this.node = node;
        }

        void setPane(Pane pane) {
            this.pane = pane;
        }

        @Override
        public Pane getRootPane() {
            return pane;
        }

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        @Override
        public void setupScene() {

        }

        @Override
        public void setupEntities() {

        }

        public Coordinate2D getCoordinate() {
            return coordinate;
        }

        @Override
        public void onMouseButtonReleased(MouseButton button, Coordinate2D coordinate2D) {
            this.coordinate = coordinate2D;
        }
    }
}
