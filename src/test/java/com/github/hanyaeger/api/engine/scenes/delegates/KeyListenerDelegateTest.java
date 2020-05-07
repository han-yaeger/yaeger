package com.github.hanyaeger.api.engine.scenes.delegates;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class KeyListenerDelegateTest {

    private KeyListenerDelegate keyListenerDelegate;

    @BeforeEach
    void setup() {
        keyListenerDelegate = new KeyListenerDelegate();
    }

    @Test
    void setupAttachesListenersToTheScene() {
        // Arrange
        Scene scene = mock(Scene.class);

        // Act
        keyListenerDelegate.setup(scene, null);

        // Verify
        verify(scene).setOnKeyPressed(any(EventHandler.class));
    }

    @Test
    void tearDownRemovesListenersToTheScene() {
        // Arrange
        Scene scene = mock(Scene.class);
        keyListenerDelegate.setup(scene, null);

        // Act
        keyListenerDelegate.tearDown(scene);

        // Verify
        verify(scene).setOnKeyPressed(null);
    }
}
