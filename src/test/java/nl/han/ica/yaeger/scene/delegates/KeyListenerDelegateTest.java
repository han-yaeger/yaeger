package nl.han.ica.yaeger.scene.delegates;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class KeyListenerDelegateTest {

    private KeyListenerDelegate keyListenerDelegate;

    @BeforeEach
    void setup() {
        keyListenerDelegate = new KeyListenerDelegate();
    }

    @Test
    void setupAttachesListenersToTheScene() {
        // Setup
        Scene scene = Mockito.mock(Scene.class);

        // Test
        keyListenerDelegate.setup(scene, null);

        // Verify
        Mockito.verify(scene).setOnKeyPressed(Mockito.any(EventHandler.class));
    }

    @Test
    void tearDownRemovesListenersToTheScene() {
        // Setup
        Scene scene = Mockito.mock(Scene.class);
        keyListenerDelegate.setup(scene, null);

        // Test
        keyListenerDelegate.tearDown(scene);

        // Verify
        Mockito.verify(scene).setOnKeyPressed(null);
    }
}
