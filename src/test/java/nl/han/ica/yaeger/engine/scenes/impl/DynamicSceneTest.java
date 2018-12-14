package nl.han.ica.yaeger.engine.scenes.impl;

import com.google.inject.Injector;
import javafx.scene.Group;
import javafx.scene.Scene;
import nl.han.ica.yaeger.engine.debug.Debugger;
import nl.han.ica.yaeger.engine.entities.EntityCollection;
import nl.han.ica.yaeger.engine.entities.EntitySupplier;
import nl.han.ica.yaeger.engine.scenes.delegates.BackgroundDelegate;
import nl.han.ica.yaeger.engine.scenes.delegates.KeyListenerDelegate;
import nl.han.ica.yaeger.engine.userinput.KeyListener;
import nl.han.ica.yaeger.module.factories.DebuggerFactory;
import nl.han.ica.yaeger.module.factories.EntityCollectionFactory;
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
