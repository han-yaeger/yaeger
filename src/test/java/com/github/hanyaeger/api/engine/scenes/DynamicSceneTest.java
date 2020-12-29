package com.github.hanyaeger.api.engine.scenes;

import com.github.hanyaeger.api.engine.Timer;
import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.Updater;
import com.github.hanyaeger.api.engine.YaegerConfig;
import com.github.hanyaeger.api.engine.debug.Debugger;
import com.github.hanyaeger.api.engine.entities.EntityCollection;
import com.github.hanyaeger.api.engine.entities.EntitySupplier;
import com.github.hanyaeger.api.engine.scenes.delegates.BackgroundDelegate;
import com.github.hanyaeger.api.engine.scenes.delegates.KeyListenerDelegate;
import com.github.hanyaeger.api.guice.factories.EntityCollectionFactory;
import com.github.hanyaeger.api.guice.factories.SceneFactory;
import com.github.hanyaeger.api.javafx.animationtimer.AnimationTimerFactory;
import com.google.inject.Injector;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ObjectInputFilter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class DynamicSceneTest {

    private final long TIMESTAMP = 0L;

    private DynamicSceneImpl sut;
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
    private Pane pane;
    private YaegerConfig config;
    private Scene scene;
    private Updater updater;

    @BeforeEach
    void setup() {
        sut = new DynamicSceneImpl();

        pane = mock(Pane.class);
        backgroundDelegate = mock(BackgroundDelegate.class);
        keyListenerDelegate = mock(KeyListenerDelegate.class);
        entitySupplier = mock(EntitySupplier.class);
        sceneFactory = mock(SceneFactory.class);
        debugger = mock(Debugger.class);
        animationTimer = mock(AnimationTimer.class);
        entityCollectionFactory = mock(EntityCollectionFactory.class);
        animationTimerFactory = mock(AnimationTimerFactory.class);
        injector = mock(Injector.class);
        updater = mock(Updater.class);
        config = mock(YaegerConfig.class);

        sut.setDebugger(debugger);
        sut.setSceneFactory(sceneFactory);
        sut.setEntityCollectionFactory(entityCollectionFactory);
        sut.setPane(pane);
        sut.setBackgroundDelegate(backgroundDelegate);
        sut.setKeyListenerDelegate(keyListenerDelegate);
        sut.setEntitySupplier(entitySupplier);
        sut.setAnimationTimerFactory(animationTimerFactory);
        sut.setUpdater(updater);
        sut.setConfig(config);

        scene = mock(Scene.class);
        entityCollection = mock(EntityCollection.class);

        when(sceneFactory.create(pane)).thenReturn(scene);
        when(entityCollectionFactory.create(pane, config)).thenReturn(entityCollection);
        when(animationTimerFactory.create(any())).thenReturn(animationTimer);

        sut.init(injector);
    }

    @Test
    void getTimersReturnsAnEmptyCollection() {
        // Arrange

        // Act
        List<Timer> timers = sut.getTimers();

        // Assert
        assertNotNull(timers);
        assertTrue(timers.isEmpty());
    }

    @Test
    void destroyClearsEntityCollection() {
        // Arrange
        var children = mock(ObservableList.class);
        when(pane.getChildren()).thenReturn(children);
        sut.activate();

        // Act
        sut.destroy();

        // Assert
        verify(entityCollection).clear();
    }

    @Test
    void destroyClearsUpdaters() {
        // Arrange
        var children = mock(ObservableList.class);
        when(pane.getChildren()).thenReturn(children);

        sut.activate();

        // Act
        sut.destroy();

        // Assert
        verify(updater).clear();
    }


    @Test
    void setEntityCollectionUpdatableReturnsUpdatable() {
        // Arrange
        sut.activate();

        // Act
        var updatable = sut.entityCollectionUpdatable();

        // Assert
        assertTrue(updatable instanceof Updatable);
    }

    @Test
    void updatingEntityCollectionUpdatesEntityCollection() {
        // Arrange
        sut.activate();
        var updatable = sut.entityCollectionUpdatable();

        // Act
        updatable.update(0L);

        // Assert
        verify(entityCollection).update(0L);
    }

    @Test
    void setUpdaterIsUsed() {
        // Arrange

        // Act
        var u = sut.getUpdater();

        // Assert
        Assertions.assertEquals(updater, u);
    }

    @Test
    void updateGetsDelegated() {
        // Arrange
        var updater = mock(Updater.class);
        sut.setUpdater(updater);

        // Act
        sut.update(TIMESTAMP);

        // Assert
        verify(updater).update(TIMESTAMP);
    }

    private class DynamicSceneImpl extends DynamicScene {

        @Override
        public void setupScene() {
            // Not required here
        }

        @Override
        public void setupEntities() {
            // Not required here
        }
    }
}
