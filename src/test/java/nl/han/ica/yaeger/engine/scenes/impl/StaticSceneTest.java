package nl.han.ica.yaeger.engine.scenes.impl;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.engine.debug.Debugger;
import nl.han.ica.yaeger.engine.entities.EntityCollection;
import nl.han.ica.yaeger.engine.entities.EntitySupplier;
import nl.han.ica.yaeger.engine.entities.entity.Entity;
import nl.han.ica.yaeger.engine.scenes.delegates.BackgroundDelegate;
import nl.han.ica.yaeger.engine.scenes.delegates.KeyListenerDelegate;
import nl.han.ica.yaeger.engine.userinput.KeyListener;
import nl.han.ica.yaeger.module.factories.DebuggerFactory;
import nl.han.ica.yaeger.module.factories.EntityCollectionFactory;
import nl.han.ica.yaeger.module.factories.SceneFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

class StaticSceneTest {
    private TestStaticScene testStaticScene;
    private SceneFactory sceneFactory;
    private DebuggerFactory debuggerFactory;
    private EntityCollectionFactory entityCollectionFactory;

    private KeyListenerDelegate keyListenerDelegate;
    private BackgroundDelegate backgroundDelegate;

    private EntityCollection entityCollection;
    private EntitySupplier entitySupplier;
    private Group root;
    private Scene scene;

    @BeforeEach
    void setup() {
        testStaticScene = new TestStaticScene();

        root = mock(Group.class);
        backgroundDelegate = mock(BackgroundDelegate.class);
        keyListenerDelegate = mock(KeyListenerDelegate.class);
        entitySupplier = mock(EntitySupplier.class);
        sceneFactory = mock(SceneFactory.class);
        debuggerFactory = mock(DebuggerFactory.class);
        entityCollectionFactory = mock(EntityCollectionFactory.class);

        testStaticScene.setDebuggerFactory(debuggerFactory);
        testStaticScene.setSceneFactory(sceneFactory);
        testStaticScene.setEntityCollectionFactory(entityCollectionFactory);
        testStaticScene.setRoot(root);
        testStaticScene.setBackgroundDelegate(backgroundDelegate);
        testStaticScene.setKeyListenerDelegate(keyListenerDelegate);
        testStaticScene.setEntitySupplier(entitySupplier);

        scene = mock(Scene.class);
        entityCollection = mock(EntityCollection.class);

        when(sceneFactory.create(root)).thenReturn(scene);
        when(entityCollectionFactory.create(root)).thenReturn(entityCollection);
    }

    @Test
    void configureCreatesAScene() {
        // Setup

        // Test
        testStaticScene.configure();

        // Verify
        verify(sceneFactory).create(root);
    }


    @Test
    void configureCreatesADebugger() {
        // Setup

        // Test
        testStaticScene.configure();

        // Verify
        verify(debuggerFactory).create(root);
    }


    @Test
    void configureCreatesAnEntityCollection() {
        // Setup

        // Test
        testStaticScene.configure();

        // Verify
        verify(entityCollectionFactory).create(root);
    }

    @Test
    void configureSetsUpAKeyListenerDelegate() {
        // Setup

        // Test
        testStaticScene.configure();

        // Verify
        verify(keyListenerDelegate).setup(any(Scene.class), any(KeyListener.class));
    }


    @Test
    void configureAddsTheDebuggerAsAStatisticsObserverToTheEntityCollection() {
        // Setup
        var debugger = mock(Debugger.class);
        when(debuggerFactory.create(root)).thenReturn(debugger);

        var entityCollection = mock(EntityCollection.class);
        when(entityCollectionFactory.create(root)).thenReturn(entityCollection);

        // Test
        testStaticScene.configure();

        // Verify
        verify(entityCollection).addStatisticsObserver(debugger);
    }

    @Test
    void destroyDelegatesDestroy() {
        // Setup
        var children = mock(ObservableList.class);
        when(root.getChildren()).thenReturn(children);

        testStaticScene.configure();

        // Test
        testStaticScene.destroy();

        // Verify
        verify(keyListenerDelegate).tearDown(scene);
        verify(backgroundDelegate).destroy();
        verify(children).clear();
    }

    @Test
    void addEntityAddsTheEntitySupplier() {
        // Setup
        testStaticScene.configure();

        var testEntity = mock(Entity.class);

        // Test
        testStaticScene.addEntity(testEntity);

        // Verify
        verify(entitySupplier).add(testEntity);
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
        testStaticScene.configure();

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

        testStaticScene.configure();

        // Test
        testStaticScene.onPressedKeysChange(input);

        // Verify
        verify(debugger).toggle();
    }

    private class TestStaticScene extends StaticScene {

        @Override
        public void setupScene() {
        }

        @Override
        public void setupEntities() {
        }

        @Override
        public void onInputChanged(Set<KeyCode> input) {
        }
    }
}
