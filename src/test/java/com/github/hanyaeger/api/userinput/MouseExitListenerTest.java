package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.scenes.ScrollableDynamicScene;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class MouseExitListenerTest {

    private Node node;

    @BeforeEach
    void setup() {
        node = mock(Node.class, withSettings().withoutAnnotations());
    }

    @Nested
    class GenericMouseEnterListener {
        private MouseExitListeningImpl sut;

        @BeforeEach
        void setup() {
            sut = new MouseExitListeningImpl();

            node = mock(Node.class, withSettings().withoutAnnotations());
            sut.setNode(node);
        }

        @Test
        void attachMouseEnterListenerAttachesMouseListener() {
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
    }

    @Nested
    class MouseMovedScrollableScene {
        private MouseExitListeningScrollableSceneImpl sut;
        private Pane pane;

        @BeforeEach
        void setup() {
            pane = mock(Pane.class);

            sut = new MouseExitListeningScrollableSceneImpl();

            sut.setNode(node);
            sut.setPane(pane);
        }

        @Test
        void attachMouseMovedListenerToScrollableDynamicSceneAttachesMouseListenerToRootPane() {
            // Arrange

            // Act
            sut.attachMouseExitListener();

            // Assert
            verify(pane).setOnMouseExited(any());
        }
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

    private static class MouseExitListeningScrollableSceneImpl extends ScrollableDynamicScene implements MouseExitListener {

        private Node node;
        private Pane pane;

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

        @Override
        public void onMouseExited() {

        }
    }
}
