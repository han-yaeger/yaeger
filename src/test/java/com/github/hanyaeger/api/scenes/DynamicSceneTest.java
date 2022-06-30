package com.github.hanyaeger.api.scenes;

import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.core.Updater;
import com.github.hanyaeger.core.YaegerConfig;
import com.github.hanyaeger.core.entities.Debugger;
import com.github.hanyaeger.core.entities.EntityCollection;
import com.github.hanyaeger.core.entities.EntitySupplier;
import com.github.hanyaeger.core.factories.PaneFactory;
import com.github.hanyaeger.core.scenes.delegates.BackgroundDelegate;
import com.github.hanyaeger.core.scenes.delegates.KeyListenerDelegate;
import com.github.hanyaeger.core.factories.EntityCollectionFactory;
import com.github.hanyaeger.core.factories.SceneFactory;
import com.github.hanyaeger.core.factories.animationtimer.AnimationTimerFactory;
import com.google.inject.Injector;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DynamicSceneTest {

    private final long TIMESTAMP = 0L;

    private DynamicSceneImpl sut;
    private SceneFactory sceneFactory;
    private Debugger debugger;
    private EntityCollectionFactory entityCollectionFactory;
    private AnimationTimer animationTimer;
    private AnimationTimerFactory animationTimerFactory;
    private PaneFactory paneFactory;
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
        paneFactory = mock(PaneFactory.class);
        injector = mock(Injector.class);
        updater = mock(Updater.class);
        config = mock(YaegerConfig.class);

        when(paneFactory.createPane()).thenReturn(pane);

        sut.setDebugger(debugger);
        sut.setSceneFactory(sceneFactory);
        sut.setEntityCollectionFactory(entityCollectionFactory);
        sut.setPaneFactory(paneFactory);
        sut.setBackgroundDelegate(backgroundDelegate);
        sut.setKeyListenerDelegate(keyListenerDelegate);
        sut.setEntitySupplier(entitySupplier);
        sut.setAnimationTimerFactory(animationTimerFactory);
        sut.setUpdater(updater);
        sut.setConfig(config);

        scene = mock(Scene.class);
        entityCollection = mock(EntityCollection.class);

        when(sceneFactory.create(pane)).thenReturn(scene);
        when(entityCollectionFactory.create(config)).thenReturn(entityCollection);
        when(animationTimerFactory.create(any(), eq(false))).thenReturn(animationTimer);

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

    @Test
    void pauseCallsStopOnTimer() {
        // Arrange
        sut.activate();

        // Act
        sut.pause();

        // Assert
        assertFalse(sut.isActiveGWU());
        verify(animationTimer).stop();
    }

    @Test
    void resumeCallsStartOnTimer() {
        // Arrange
        sut.activate();

        // Act
        sut.resume();

        // Assert
        assertTrue(sut.isActiveGWU());
        verify(animationTimer, times(2)).start();
    }

    private static class DynamicSceneImpl extends DynamicScene {

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
