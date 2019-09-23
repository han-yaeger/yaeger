package nl.meron.yaeger.engine.scenes.delegates;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class KeyListenerDelegateTest {

    private KeyListenerDelegate keyListenerDelegate;

    @BeforeEach
    void setup() {
        keyListenerDelegate = new KeyListenerDelegate();
    }

    @Test
    void setupAttachesListenersToTheScene() {
        // Setup
        Scene scene = mock(Scene.class);

        // Test
        keyListenerDelegate.setup(scene, null);

        // Verify
        verify(scene).setOnKeyPressed(any(EventHandler.class));
    }

    @Test
    void tearDownRemovesListenersToTheScene() {
        // Setup
        Scene scene = mock(Scene.class);
        keyListenerDelegate.setup(scene, null);

        // Test
        keyListenerDelegate.tearDown(scene);

        // Verify
        verify(scene).setOnKeyPressed(null);
    }
}
