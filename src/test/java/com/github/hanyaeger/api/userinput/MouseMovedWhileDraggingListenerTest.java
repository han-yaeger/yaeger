package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.core.YaegerConfig;
import com.github.hanyaeger.core.repositories.DragNDropRepository;
import com.google.inject.Injector;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MouseMovedWhileDraggingListenerTest {

    private static final Coordinate2D DEFAULT_LOCATION = new Coordinate2D(0, 0);

    private Node node;

    @BeforeEach
    void setup() {
        node = mock(Node.class, withSettings().withoutAnnotations());
    }

    @Nested
    class MouseMovedListeningEntity {

        private MouseMovedWhileDraggingListeningEntityImpl sut;
        private Scene scene;

        @BeforeEach
        void setup() {

            scene = mock(Scene.class);
            sut = new MouseMovedWhileDraggingListeningEntityImpl(DEFAULT_LOCATION);
            sut.setNode(node);

            when(node.getScene()).thenReturn(scene);
        }

        @Test
        void attachMouseMovedListenerAttachesOnDragDetectedListenerToEntity() {
            // Arrange

            // Act
            sut.attachMouseMovedWhileDraggedListener();

            // Assert
            verify(scene).setOnDragDetected(any());
        }

        @Test
        void attachMouseMovedListenerAttachesOnDragOverListenerToEntity() {
            // Arrange

            // Act
            sut.attachMouseMovedWhileDraggedListener();

            // Assert
            verify(scene).setOnMouseDragOver(any());
        }

        @Test
        void callingEventFromEventHandlerCallsOnMouseButtonPressedWithCorrectCoordinates() {
            // Arrange
            ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
            sut.attachMouseMovedWhileDraggedListener();
            verify(scene).setOnMouseDragOver(eventHandlerArgumentCaptor.capture());

            var x = 37D;
            var y = 42D;

            var mouseEvent = mock(MouseDragEvent.class);
            when(mouseEvent.getX()).thenReturn(x);
            when(mouseEvent.getY()).thenReturn(y);

            // Act
            eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

            // Assert
            assertEquals(x, sut.getCoordinate().getX());
            assertEquals(y, sut.getCoordinate().getY());
        }
    }

    @Nested
    class MouseMovedListeningScene {

        private MouseMovedWhileDraggingListeningSceneImpl sut;

        @BeforeEach
        void setup() {

            sut = new MouseMovedWhileDraggingListeningSceneImpl();
            sut.setNode(node);
        }

        @Test
        void attachMouseMovedListenerAttachesOnDragDetectedListenerToEntity() {
            // Arrange

            // Act
            sut.attachMouseMovedWhileDraggedListener();

            // Assert
            verify(node).setOnDragDetected(any());
        }

        @Test
        void attachMouseMovedListenerAttachesOnDragOverListenerToEntity() {
            // Arrange

            // Act
            sut.attachMouseMovedWhileDraggedListener();

            // Assert
            verify(node).setOnMouseDragOver(any());
        }

        @Test
        void callingEventFromEventHandlerCallsOnMouseButtonPressedWithCorrectCoordinates() {
            // Arrange
            ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
            sut.attachMouseMovedWhileDraggedListener();
            verify(node).setOnMouseDragOver(eventHandlerArgumentCaptor.capture());

            var x = 37D;
            var y = 42D;

            var mouseEvent = mock(MouseDragEvent.class);
            when(mouseEvent.getX()).thenReturn(x);
            when(mouseEvent.getY()).thenReturn(y);

            // Act
            eventHandlerArgumentCaptor.getValue().handle(mouseEvent);

            // Assert
            assertEquals(x, sut.getCoordinate().getX());
            assertEquals(y, sut.getCoordinate().getY());
        }
    }

    @Test
    void attachMouseMovedListenerToOtherDoesNothing() {
        // Arrange
        var mouseMovedListener = new MouseMovedWhileDraggingListeningImpl();
        mouseMovedListener.setNode(node);

        // Act
        mouseMovedListener.attachMouseMovedWhileDraggedListener();

        // Assert
        verifyNoInteractions(node);
    }

    private static class MouseMovedWhileDraggingListeningImpl implements MouseMovedWhileDraggingListener {

        private Node node;
        private Coordinate2D releasedCoordinates;

        public void setNode(Node node) {
            this.node = node;
        }

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.empty();
        }

        @Override
        public void onMouseMovedWhileDragging(Coordinate2D coordinate2D) {

        }
    }

    private static class MouseMovedWhileDraggingListeningSceneImpl implements YaegerScene, MouseMovedWhileDraggingListener {

        private Node node;
        private Coordinate2D coordinate;

        @Override
        public void clear() {

        }

        @Override
        public void destroy() {

        }

        @Override
        public void setBrightness(double brightness) {

        }

        @Override
        public double getBrightness() {
            return 0;
        }

        @Override
        public void setContrast(double contrast) {

        }

        @Override
        public double getContrast() {
            return 0;
        }

        @Override
        public void setHue(double hue) {

        }

        @Override
        public double getHue() {
            return 0;
        }

        @Override
        public void setSaturation(double saturation) {

        }

        @Override
        public double getSaturation() {
            return 0;
        }

        @Override
        public void init(Injector injector) {

        }

        public void setNode(Node node) {
            this.node = node;
        }

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        @Override
        public void setupScene() {
            // Not required here
        }

        @Override
        public void setupEntities() {
            // Not required here
        }

        @Override
        public void postActivate() {
            // Not required here
        }

        @Override
        public void setBackgroundColor(Color color) {
            // Not required here
        }

        @Override
        public void setBackgroundImage(String url) {
            // Not required here
        }

        @Override
        public void setBackgroundAudio(String url) {
            // Not required here
        }

        @Override
        public void setBackgroundAudioVolume(double volume) {
            // Not required here
        }

        @Override
        public double getBackgroundAudioVolume() {
            return 0;
        }

        @Override
        public void stopBackgroundAudio() {
            // Not required here
        }

        @Override
        public Scene getScene() {
            return null;
        }

        @Override
        public Stage getStage() {
            return null;
        }

        @Override
        public void setStage(Stage stage) {
            // Not required here
        }

        @Override
        public void setConfig(YaegerConfig yaegerConfig) {
            // Not required here
        }

        @Override
        public void onMouseMovedWhileDragging(Coordinate2D coordinate2D) {
            this.coordinate = coordinate2D;
        }

        @Override
        public void setDragNDropRepository(DragNDropRepository dragNDropRepository) {
            // Not required here
        }

        @Override
        public DragNDropRepository getDragNDropRepository() {
            return null;
        }

        public Coordinate2D getCoordinate() {
            return coordinate;
        }
    }

    private static class MouseMovedWhileDraggingListeningEntityImpl extends YaegerEntity implements MouseMovedWhileDraggingListener {

        private Node node;
        private Coordinate2D coordinate;

        protected MouseMovedWhileDraggingListeningEntityImpl(Coordinate2D initialLocation) {
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
        public void onMouseMovedWhileDragging(Coordinate2D coordinate2D) {
            this.coordinate = coordinate2D;
        }

        public Coordinate2D getCoordinate() {
            return coordinate;
        }
    }
}
