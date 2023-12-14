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
import com.github.hanyaeger.core.scenes.delegates.CoordinateGridDelegate;
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

    private DynamicSceneImpl sut;
    private AnimationTimer animationTimerMock;
    private EntityCollection entityCollection;
    private Pane paneMock;
    private Updater updaterMock;

    @BeforeEach
    void setup() {
        sut = new DynamicSceneImpl();

        paneMock = mock(Pane.class);
        var backgroundDelegateMock = mock(BackgroundDelegate.class);
        var keyListenerDelegateMock = mock(KeyListenerDelegate.class);
        var entitySupplierMock = mock(EntitySupplier.class);
        var sceneFactoryMock = mock(SceneFactory.class);
        var debuggerMock = mock(Debugger.class);
        animationTimerMock = mock(AnimationTimer.class);
        var entityCollectionFactoryMock = mock(EntityCollectionFactory.class);
        var animationTimerFactoryMock = mock(AnimationTimerFactory.class);
        var coordinateGridDelegateMock = mock(CoordinateGridDelegate.class);
        var paneFactoryMock = mock(PaneFactory.class);
        var injectorMock = mock(Injector.class);
        updaterMock = mock(Updater.class);
        var configMock = mock(YaegerConfig.class);

        when(paneFactoryMock.createPane()).thenReturn(paneMock);

        sut.setDebugger(debuggerMock);
        sut.setSceneFactory(sceneFactoryMock);
        sut.setEntityCollectionFactory(entityCollectionFactoryMock);
        sut.setPaneFactory(paneFactoryMock);
        sut.setBackgroundDelegate(backgroundDelegateMock);
        sut.setKeyListenerDelegate(keyListenerDelegateMock);
        sut.setCoordinateGridDelegate(coordinateGridDelegateMock);
        sut.setEntitySupplier(entitySupplierMock);
        sut.setAnimationTimerFactory(animationTimerFactoryMock);
        sut.setUpdater(updaterMock);
        sut.setConfig(configMock);

        var scene = mock(Scene.class);
        entityCollection = mock(EntityCollection.class);

        when(sceneFactoryMock.create(paneMock)).thenReturn(scene);
        when(entityCollectionFactoryMock.create(configMock)).thenReturn(entityCollection);
        when(animationTimerFactoryMock.create(any(), eq(false))).thenReturn(animationTimerMock);

        sut.init(injectorMock);
    }

    @Test
    void getTimersReturnsAnEmptyCollection() {
        // Arrange

        // Act
        var timers = sut.getTimers();

        // Assert
        assertNotNull(timers);
        assertTrue(timers.isEmpty());
    }

    @Test
    void destroyClearsEntityCollection() {
        // Arrange
        var children = mock(ObservableList.class);
        when(paneMock.getChildren()).thenReturn(children);
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
        when(paneMock.getChildren()).thenReturn(children);

        sut.activate();

        // Act
        sut.destroy();

        // Assert
        verify(updaterMock).clear();
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
        assertEquals(updaterMock, u);
    }

    @Test
    void updateGetsDelegated() {
        // Arrange
        var updater = mock(Updater.class);
        sut.setUpdater(updater);

        // Act
        var TIMESTAMP = 0L;
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
        verify(animationTimerMock).stop();
    }

    @Test
    void resumeCallsStartOnTimer() {
        // Arrange
        sut.activate();

        // Act
        sut.resume();

        // Assert
        assertTrue(sut.isActiveGWU());
        verify(animationTimerMock, times(2)).start();
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
