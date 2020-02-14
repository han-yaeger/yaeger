package nl.meron.yaeger.engine.scenes.impl;

import com.google.inject.Injector;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import nl.meron.yaeger.engine.debug.Debugger;
import nl.meron.yaeger.engine.entities.EntityCollection;
import nl.meron.yaeger.engine.entities.EntitySpawner;
import nl.meron.yaeger.engine.entities.EntitySupplier;
import nl.meron.yaeger.engine.scenes.delegates.BackgroundDelegate;
import nl.meron.yaeger.engine.scenes.delegates.KeyListenerDelegate;
import nl.meron.yaeger.javafx.animationtimer.AnimationTimerFactory;
import nl.meron.yaeger.guice.factories.EntityCollectionFactory;
import nl.meron.yaeger.guice.factories.SceneFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;

import static org.mockito.Mockito.*;

class DynamicSceneTest {

    private TestDynamicScene sut;
    private SceneFactory sceneFactory;
    private Debugger debugger;
    private EntityCollectionFactory entityCollectionFactory;
    private AnimationTimer animationTimer;
    private AnimationTimerFactory animationTimerFactory;
    private Injector injector;

    private KeyListenerDelegate keyListenerDelegate;
    private BackgroundDelegate backgroundDelegate;

    private EntityCollection entityCollection;
    private EntitySupplier entitySupplier;
    private Group root;
    private Scene scene;

    @BeforeEach
    void setup() {
        sut = new TestDynamicScene();

        root = mock(Group.class);
        backgroundDelegate = mock(BackgroundDelegate.class);
        keyListenerDelegate = mock(KeyListenerDelegate.class);
        entitySupplier = mock(EntitySupplier.class);
        sceneFactory = mock(SceneFactory.class);
        debugger = mock(Debugger.class);
        animationTimer = mock(AnimationTimer.class);
        entityCollectionFactory = mock(EntityCollectionFactory.class);
        animationTimerFactory = mock(AnimationTimerFactory.class);
        injector = Mockito.mock(Injector.class);

        sut.setDebugger(debugger);
        sut.setSceneFactory(sceneFactory);
        sut.setEntityCollectionFactory(entityCollectionFactory);
        sut.setRoot(root);
        sut.setBackgroundDelegate(backgroundDelegate);
        sut.setKeyListenerDelegate(keyListenerDelegate);
        sut.setEntitySupplier(entitySupplier);
        sut.setAnimationTimerFactory(animationTimerFactory);

        scene = mock(Scene.class);
        entityCollection = mock(EntityCollection.class);

        when(sceneFactory.create(root)).thenReturn(scene);
        when(entityCollectionFactory.create(root)).thenReturn(entityCollection);
        when(animationTimerFactory.create(any())).thenReturn(animationTimer);

        sut.init(injector);
    }

    @Test
    void setupSpawnersIsCalledDuringConfiguration() {
        // Setup

        // Test
        sut.configure();

        // Verify
        Assertions.assertTrue(sut.setupSpawnersCalled);
    }

    @Test
    void registerSpawnerDelegatesToTheEntityCollection() {
        // Setup
        var injector = mock(Injector.class);
        sut.init(injector);

        var animationTimer = mock(AnimationTimer.class);
        var animationTimerFactory = mock(AnimationTimerFactory.class);
        var spawner = mock(EntitySpawner.class);
        spawner.setAnimationTimerFactory(animationTimerFactory);
        spawner.init(null);

        when(animationTimerFactory.createTimeableAnimationTimer(any(), anyLong())).thenReturn(animationTimer);

        sut.configure();

        // Test
        sut.registerSpawner(spawner);

        // Verify
        verify(entityCollection).registerSupplier(spawner);
    }

    @Test
    void destroyClearsEntityCollection() {
        // Setup
        var children = mock(ObservableList.class);
        when(root.getChildren()).thenReturn(children);
        sut.configure();

        // Test
        sut.destroy();

        // Verify
        verify(entityCollection).clear();
    }

    @Test
    void onInputChangeNotifiesEntityCollection() {
        // Setup
        var input = new HashSet<KeyCode>();
        input.add(KeyCode.F1);
        sut.configure();

        // Test
        sut.onInputChanged(input);

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
