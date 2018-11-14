package nl.han.ica.yaeger.engine.scenes.impl;

import javafx.scene.Group;
import javafx.scene.Scene;
import nl.han.ica.yaeger.engine.debug.Debugger;
import nl.han.ica.yaeger.engine.scenes.delegates.KeyListenerDelegate;
import nl.han.ica.yaeger.engine.userinput.KeyListener;
import nl.han.ica.yaeger.module.factories.DebuggerFactory;
import nl.han.ica.yaeger.module.factories.SceneFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DynamicSceneTest {

    private TestDynamicScene testScene;
    private SceneFactory sceneFactory;
    private DebuggerFactory debuggerFactory;
    private KeyListenerDelegate keyListenerDelegate;
    private Group root;

    @BeforeEach
    void setup() {
        testScene = new TestDynamicScene();

        root = mock(Group.class);
        keyListenerDelegate = mock(KeyListenerDelegate.class);
        sceneFactory = mock(SceneFactory.class);
        debuggerFactory = mock(DebuggerFactory.class);

        testScene.setDebuggerFactory(debuggerFactory);
        testScene.setSceneFactory(sceneFactory);
        testScene.setRoot(root);
        testScene.setKeyListenerDelegate(keyListenerDelegate);

        Scene scene = mock(Scene.class);
        when(sceneFactory.create(root)).thenReturn(scene);
    }

    @Test
    void initiaizeIsCalledAfterCreation() {
        // Setup

        // Test

        // Verify
        Assertions.assertTrue(testScene.initializeCalled);
    }

    @Test
    void setupSceneDoesAllRequiredSetup() {
        // Setup
        Debugger debugger = mock(Debugger.class);
        when(debuggerFactory.create(root)).thenReturn(debugger);

        // Test
        testScene.setupScene();

        // Verify
        verify(sceneFactory).create(root);
        verify(debuggerFactory).create(root);
        verify(keyListenerDelegate).setup(any(Scene.class), any(KeyListener.class));
    }

    @Test
    void setupSpawnersIsCalledAfterSetup() {
        // Setup
        Debugger debugger = mock(Debugger.class);
        when(debuggerFactory.create(root)).thenReturn(debugger);

        // Test
        testScene.setupScene();

        // Verify
        Assertions.assertTrue(testScene.setupSpawnersCalled);
    }

    @Test
    void setupEntitiesIsCalledAfterSetup() {
        // Setup
        Debugger debugger = mock(Debugger.class);
        when(debuggerFactory.create(root)).thenReturn(debugger);

        // Test
        testScene.setupScene();

        // Verify
        Assertions.assertTrue(testScene.setupEntitiesCalled);
    }


    private class TestDynamicScene extends DynamicScene {

        private boolean setupSpawnersCalled;
        private boolean setupEntitiesCalled;
        private boolean initializeCalled;

        @Override
        protected void setupSpawners() {
            setupSpawnersCalled = true;
        }

        @Override
        protected void setupInitialEntities() {
            setupEntitiesCalled = true;
        }

        @Override
        public void initializeScene() {
            initializeCalled = true;
        }
    }
}
