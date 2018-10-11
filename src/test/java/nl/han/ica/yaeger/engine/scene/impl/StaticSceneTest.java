package nl.han.ica.yaeger.engine.scene.impl;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.engine.scene.delegates.KeyListenerDelegate;
import nl.han.ica.yaeger.engine.userinput.KeyListener;
import nl.han.ica.yaeger.module.factories.DebuggerFactory;
import nl.han.ica.yaeger.module.factories.SceneFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.mockito.Mockito.*;

class StaticSceneTest {

    @Test
    void initiaizeIsCalledAfterCreation() {
        // Setup

        // Test
        TestStaticScene testStaticScene = new TestStaticScene();

        // Verify
        Assertions.assertTrue(testStaticScene.initializeCalled);
    }

    @Test
    void setupSceneDoesAllRequiredSetup() {

        // Setup
        Group root = mock(Group.class);
        Scene scene = mock(Scene.class);
        KeyListenerDelegate keyListenerDelegate = mock(KeyListenerDelegate.class);
        SceneFactory sceneFactory = mock(SceneFactory.class);
        DebuggerFactory debuggerFactory = mock(DebuggerFactory.class);

        TestStaticScene testStaticScene = new TestStaticScene();
        testStaticScene.setDebuggerFactory(debuggerFactory);
        testStaticScene.setSceneFactory(sceneFactory);
        testStaticScene.setRoot(root);
        testStaticScene.setKeyListenerDelegate(keyListenerDelegate);

        when(sceneFactory.create(root)).thenReturn(scene);

        // Test
        testStaticScene.setupScene();

        // Verify
        verify(sceneFactory).create(root);
        verify(debuggerFactory).create(root);
        verify(keyListenerDelegate).setup(any(Scene.class), any(KeyListener.class));
    }


    private class TestStaticScene extends StaticScene {

        private boolean initializeCalled;

        @Override
        public void onInputChanged(Set<KeyCode> input) {

        }

        @Override
        public void initializeScene() {
            initializeCalled = true;
        }
    }


}
