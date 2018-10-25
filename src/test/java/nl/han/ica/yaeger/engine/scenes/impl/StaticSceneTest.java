package nl.han.ica.yaeger.engine.scenes.impl;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.engine.debug.Debugger;
import nl.han.ica.yaeger.engine.entities.entity.Entity;
import nl.han.ica.yaeger.engine.scenes.delegates.KeyListenerDelegate;
import nl.han.ica.yaeger.engine.userinput.KeyListener;
import nl.han.ica.yaeger.module.factories.DebuggerFactory;
import nl.han.ica.yaeger.module.factories.SceneFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

class StaticSceneTest {
    private TestStaticScene testStaticScene;
    private SceneFactory sceneFactory;
    private DebuggerFactory debuggerFactory;
    private KeyListenerDelegate keyListenerDelegate;
    private Group root;
    private Scene scene;

    @BeforeEach
    void setup() {
        testStaticScene = new TestStaticScene();

        root = mock(Group.class);
        keyListenerDelegate = mock(KeyListenerDelegate.class);
        sceneFactory = mock(SceneFactory.class);
        debuggerFactory = mock(DebuggerFactory.class);

        testStaticScene.setDebuggerFactory(debuggerFactory);
        testStaticScene.setSceneFactory(sceneFactory);
        testStaticScene.setRoot(root);
        testStaticScene.setKeyListenerDelegate(keyListenerDelegate);


        scene = mock(Scene.class);
        when(sceneFactory.create(root)).thenReturn(scene);
    }

    @Test
    void initializeIsCalledAfterCreation() {
        // Setup

        // Test

        // Verify
        Assertions.assertTrue(testStaticScene.initializeCalled);
    }

    @Test
    void setupSceneDoesAllRequiredSetup() {
        // Setup

        // Test
        testStaticScene.setupScene();

        // Verify
        verify(sceneFactory).create(root);
        verify(debuggerFactory).create(root);
        verify(keyListenerDelegate).setup(any(Scene.class), any(KeyListener.class));
    }

    @Test
    void destroyDelegatesDestroy() {
        // Setup
        var children = mock(ObservableList.class);
        when(root.getChildren()).thenReturn(children);

        testStaticScene.setupScene();

        // Test
        testStaticScene.destroy();

        // Verify
        verify(keyListenerDelegate).tearDown(scene);
        verify(children).clear();
        verify(scene).setFill(null);
    }

    @Test
    void addEntityAddsTheEntityToTheGroup() {
        // Setup
        var children = mock(ObservableList.class);
        when(root.getChildren()).thenReturn(children);

        var testEntity = mock(Entity.class);
        var gameNode = mock(Node.class);
        when(testEntity.getGameNode()).thenReturn(gameNode);

        testStaticScene.setupScene();

        // Test
        testStaticScene.addEntity(testEntity);

        // Verify
        verify(children).add(gameNode);
    }

    @Test
    void pressingKeyDoesNotTogglesDebugger() {
        // Setup
        var input = new HashSet<KeyCode>();
        input.add(KeyCode.Y);
        input.add(KeyCode.A);
        input.add(KeyCode.E);
        input.add(KeyCode.G);
        input.add(KeyCode.E);
        input.add(KeyCode.R);


        Debugger debugger = mock(Debugger.class);
        when(debuggerFactory.create(root)).thenReturn(debugger);
        testStaticScene.setupScene();

        // Test
        testStaticScene.onPressedKeysChange(input);

        // Verify
        verifyNoMoreInteractions(debugger);
    }

    @Test
    void pressingF1TogglesDebugger() {
        // Setup
        var input = new HashSet<KeyCode>();
        input.add(KeyCode.F1);

        var debugger = mock(Debugger.class);
        when(debuggerFactory.create(root)).thenReturn(debugger);

        testStaticScene.setupScene();

        // Test
        testStaticScene.onPressedKeysChange(input);

        // Verify
        verify(debugger).toggle();
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
