package nl.han.ica.yaeger.engine.scenes.impl;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.engine.entities.EntityCollection;
import nl.han.ica.yaeger.engine.entities.EntitySpawner;
import nl.han.ica.yaeger.engine.entities.EntitySupplier;
import nl.han.ica.yaeger.engine.scenes.delegates.BackgroundDelegate;
import nl.han.ica.yaeger.engine.scenes.delegates.KeyListenerDelegate;
import nl.han.ica.yaeger.module.factories.DebuggerFactory;
import nl.han.ica.yaeger.module.factories.EntityCollectionFactory;
import nl.han.ica.yaeger.module.factories.SceneFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.mockito.Mockito.*;

class DynamicSceneTest {

    private TestDynamicScene testScene;
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
        testScene = new TestDynamicScene();

        root = mock(Group.class);
        backgroundDelegate = mock(BackgroundDelegate.class);
        keyListenerDelegate = mock(KeyListenerDelegate.class);
        entitySupplier = mock(EntitySupplier.class);
        sceneFactory = mock(SceneFactory.class);
        debuggerFactory = mock(DebuggerFactory.class);
        entityCollectionFactory = mock(EntityCollectionFactory.class);

        testScene.setDebuggerFactory(debuggerFactory);
        testScene.setSceneFactory(sceneFactory);
        testScene.setEntityCollectionFactory(entityCollectionFactory);
        testScene.setRoot(root);
        testScene.setBackgroundDelegate(backgroundDelegate);
        testScene.setKeyListenerDelegate(keyListenerDelegate);
        testScene.setEntitySupplier(entitySupplier);

        scene = mock(Scene.class);
        entityCollection = mock(EntityCollection.class);

        when(sceneFactory.create(root)).thenReturn(scene);
        when(entityCollectionFactory.create(root)).thenReturn(entityCollection);
    }

    @Test
    void setupSpawnersIsCalledDuringConfiguration() {
        // Setup

        // Test
        testScene.configure();

        // Verify
        Assertions.assertTrue(testScene.setupSpawnersCalled);
    }

    @Test
    void registerSpawnerDelegatesToTheEntityCollection() {
        // Setup
        var spawner = mock(EntitySpawner.class);
        testScene.configure();

        // Test
        testScene.registerSpawner(spawner);

        // Verify
        verify(entityCollection).registerSupplier(spawner);
    }

    @Test
    void destroyClearsEntityCollection() {
        // Setup
        var children = mock(ObservableList.class);
        when(root.getChildren()).thenReturn(children);
        testScene.configure();

        // Test
        testScene.destroy();

        // Verify
        verify(entityCollection).clear();
    }

    @Test
    void onInputChangeNotifiesEntityCollection() {
        // Setup
        var input = new HashSet<KeyCode>();
        input.add(KeyCode.F1);
        testScene.configure();

        // Test
        testScene.onInputChanged(input);

        // Verify
        verify(entityCollection).notifyGameObjectsOfPressedKeys(input);
    }

    private class TestDynamicScene extends DynamicScene {

        private boolean setupSpawnersCalled;

        @Override
        protected void setupSpawners() {
            setupSpawnersCalled = true;
        }


        @Override
        public void setupScene() {

        }

        @Override
        public void setupEntities() {

        }
    }
}
