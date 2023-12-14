package com.github.hanyaeger.api.scenes;

import com.github.hanyaeger.core.YaegerConfig;
import com.github.hanyaeger.core.entities.Debugger;
import com.github.hanyaeger.core.entities.EntitySupplier;
import com.github.hanyaeger.core.factories.PaneFactory;
import com.github.hanyaeger.core.repositories.DragNDropRepository;
import com.github.hanyaeger.core.scenes.delegates.BackgroundDelegate;
import com.github.hanyaeger.core.scenes.delegates.CoordinateGridDelegate;
import com.github.hanyaeger.core.scenes.delegates.KeyListenerDelegate;
import com.github.hanyaeger.core.factories.EntityCollectionFactory;
import com.github.hanyaeger.core.factories.SceneFactory;
import com.google.inject.Injector;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.github.hanyaeger.core.entities.EntityCollection;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.userinput.KeyListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaticSceneTest {
    private StaticSceneImpl sut;
    private SceneFactory sceneFactoryMock;
    private EntityCollectionFactory entityCollectionFactoryMock;

    private KeyListenerDelegate keyListenerDelegateMock;
    private BackgroundDelegate backgroundDelegateMock;
    private DragNDropRepository dragNDropRepositoryMock;
    private CoordinateGridDelegate coordinateGridDelegateMock;
    private Debugger debuggerMock;
    private Injector injectorMock;

    private EntityCollection entityCollection;
    private EntitySupplier entitySupplierMock;
    private PaneFactory paneFactoryMock;
    private Pane paneMock;
    private Scene scene;
    private Stage stageMock;
    private YaegerConfig configMock;

    @BeforeEach
    void setup() {
        sut = new StaticSceneImpl();

        paneMock = mock(Pane.class, withSettings().withoutAnnotations());
        backgroundDelegateMock = mock(BackgroundDelegate.class);
        keyListenerDelegateMock = mock(KeyListenerDelegate.class);
        coordinateGridDelegateMock = mock(CoordinateGridDelegate.class);
        dragNDropRepositoryMock = mock(DragNDropRepository.class);
        debuggerMock = mock(Debugger.class);
        entitySupplierMock = mock(EntitySupplier.class);
        sceneFactoryMock = mock(SceneFactory.class);
        entityCollectionFactoryMock = mock(EntityCollectionFactory.class);
        paneFactoryMock = mock(PaneFactory.class);
        injectorMock = mock(Injector.class);
        stageMock = mock(Stage.class);
        configMock = mock(YaegerConfig.class);

        when(paneFactoryMock.createPane()).thenReturn(paneMock);

        sut.setDebugger(debuggerMock);
        sut.setSceneFactory(sceneFactoryMock);
        sut.setEntityCollectionFactory(entityCollectionFactoryMock);
        sut.setDragNDropRepository(dragNDropRepositoryMock);
        sut.setPaneFactory(paneFactoryMock);
        sut.setBackgroundDelegate(backgroundDelegateMock);
        sut.setKeyListenerDelegate(keyListenerDelegateMock);
        sut.setCoordinateGridDelegate(coordinateGridDelegateMock);
        sut.setEntitySupplier(entitySupplierMock);
        sut.setStage(stageMock);
        sut.setConfig(configMock);

        scene = mock(Scene.class);
        entityCollection = mock(EntityCollection.class);

        when(sceneFactoryMock.create(paneMock)).thenReturn(scene);
        when(entityCollectionFactoryMock.create(configMock)).thenReturn(entityCollection);

        sut.init(injectorMock);
    }

    @Test
    void getInjectorReturnsInjectorProvidedThroughInit() {
        // Arrange

        // Act
        var actual = sut.getInjector();

        // Verify
        assertEquals(actual, injectorMock);
    }

    @Test
    void getStageReturnsSetStage() {
        // Arrange

        // Act
        var actual = sut.getStage();

        // Verify
        assertEquals(actual, stageMock);
    }

    @Test
    void getDragNDropRepositoryReturnsDragNDropRepository() {
        // Arrange

        // Act
        var actual = sut.getDragNDropRepository();

        // Assert
        assertEquals(actual, dragNDropRepositoryMock);
    }

    @Test
    void getEntityCollectionReturnsEntityCollection() {
        // Arrange
        sut.activate();

        // Act
        var actual = sut.getEntityCollection();

        // Assert
        assertEquals(actual, entityCollection);
    }

    @Test
    void getNodeReturnsOptionalOfParentNode() {
        // Arrange
        sut.activate();

        var expected = mock(Parent.class, withSettings().withoutAnnotations());
        when(sut.getScene().getRoot()).thenReturn(expected);

        // Act
        var actual = sut.getNode();

        // Assert
        assertEquals(expected, actual.get());
    }

    @Test
    void activateCreatesAScene() {
        // Arrange

        // Act
        sut.activate();

        // Verify
        verify(sceneFactoryMock).create(paneMock);
    }

    @Test
    void activateSetsPaneOnEntitySupplier() {
        // Arrange

        // Act
        sut.activate();

        // Verify
        verify(entitySupplierMock).setPane(paneMock);
    }


    @Test
    void activateSetsUpADebuggerIfConfigHasShowDebug() {
        // Arrange
        when(configMock.showDebug()).thenReturn(true);

        // Act
        sut.activate();

        // Assert
        verify(debuggerMock).setup(paneMock, scene);
    }

    @Test
    void activateSetsUpCoordinateGridDelegateIfConfigHasShowGrid() {
        // Arrange
        when(configMock.showGrid()).thenReturn(true);

        // Act
        sut.activate();

        // Assert
        verify(coordinateGridDelegateMock).setup(paneMock);
    }

    @Test
    void activateInjectDependenciesIntoCoordinateGridDelegateIfConfigHasShowGrid() {
        // Arrange
        when(configMock.showGrid()).thenReturn(true);

        // Act
        sut.activate();

        // Assert
        verify(injectorMock).injectMembers(coordinateGridDelegateMock);
    }

    @Test
    void activateCreatesAnEntityCollection() {
        // Arrange

        // Act
        sut.activate();

        // Verify
        verify(entityCollectionFactoryMock).create(configMock);
    }

    @Test
    void getPaneForDebuggerReturnsThePane() {
        // Arrange

        // Act
        var actual = sut.getPaneForDebugger();

        // Assert
        assertEquals(actual, paneMock);
    }

    @Test
    void activateInjectDependenciesIntoEntityCollection() {
        // Arrange

        // Act
        sut.activate();

        // Verify
        verify(injectorMock).injectMembers(any());
    }

    @Test
    void activateSetsUpAKeyListenerDelegate() {
        // Arrange

        // Act
        sut.activate();

        // Verify
        verify(keyListenerDelegateMock).setup(any(Scene.class), any(KeyListener.class), any(YaegerConfig.class));
    }

    @Test
    void activateAddsTheDebuggerAsAStatisticsObserverToTheEntityCollection() {
        // Arrange
        var entityCollection = mock(EntityCollection.class);
        when(entityCollectionFactoryMock.create(configMock)).thenReturn(entityCollection);

        when(configMock.showDebug()).thenReturn(true);

        // Act
        sut.activate();

        // Verify
        verify(entityCollection).addStatisticsObserver(debuggerMock);
    }

    @Test
    void destroyDelegatesDestroy() {
        // Arrange
        var children = mock(ObservableList.class);
        when(paneMock.getChildren()).thenReturn(children);

        sut.activate();

        // Act
        sut.destroy();

        // Verify
        verify(keyListenerDelegateMock).tearDown(scene);
        verify(backgroundDelegateMock).destroy();
        verify(children).clear();
    }

    @Test
    void addEntityAddsTheEntitySupplier() {
        // Arrange
        sut.activate();

        var testEntity = mock(YaegerEntity.class);

        // Act
        sut.addEntity(testEntity);

        // Verify
        verify(entitySupplierMock).add(testEntity);
    }

    @Test
    void setBackgroundAudioDelegatesToBackgroundDelegate() {
        // Arrange
        final var AUDIO_STRING = "Hello World";

        // Act
        sut.setBackgroundAudio(AUDIO_STRING);

        // Verify
        verify(backgroundDelegateMock).setBackgroundAudio(AUDIO_STRING);
    }

    @Test
    void implementingKeyListenerAddsSceneToKeyListeners() {
        // Arrange
        final var sut = new StaticSceneKeyListenerImpl();

        when(paneFactoryMock.createPane()).thenReturn(paneMock);

        sut.setDebugger(debuggerMock);
        sut.setSceneFactory(sceneFactoryMock);
        sut.setEntityCollectionFactory(entityCollectionFactoryMock);
        sut.setPaneFactory(paneFactoryMock);
        sut.setBackgroundDelegate(backgroundDelegateMock);
        sut.setKeyListenerDelegate(keyListenerDelegateMock);
        sut.setEntitySupplier(entitySupplierMock);
        sut.setStage(stageMock);
        sut.setConfig(configMock);

        scene = mock(Scene.class);
        entityCollection = mock(EntityCollection.class);

        when(sceneFactoryMock.create(paneMock)).thenReturn(scene);
        when(entityCollectionFactoryMock.create(configMock)).thenReturn(entityCollection);

        sut.init(injectorMock);

        // Act
        sut.activate();

        // Verify
        verify(entityCollection).registerKeyListener(sut);
    }

    @Test
    void setBackgroundColorDelegatesToBackgroundDelegate() {
        // Arrange
        final var color = Color.YELLOW;

        // Act
        sut.setBackgroundColor(color);

        // Verify
        verify(backgroundDelegateMock).setBackgroundColor(color);
    }

    @Test
    void setBackgroundImageDelegatesToBackgroundDelegateFullscreen() {
        // Arrange
        final var IMAGE_STRING = "Hello World";

        // Act
        sut.setBackgroundImage(IMAGE_STRING);

        // Verify
        verify(backgroundDelegateMock).setBackgroundImage(IMAGE_STRING, true);
    }

    @Test
    void setFullscreenBackgroundImageDelegatesToBackgroundDelegateFullscreen() {
        // Arrange
        final var IMAGE_STRING = "Hello World";

        // Act
        sut.setBackgroundImage(IMAGE_STRING, true);

        // Verify
        verify(backgroundDelegateMock).setBackgroundImage(IMAGE_STRING, true);
    }

    @Test
    void setTiledBackgroundImageDelegatesToBackgroundDelegateTiled() {
        // Arrange
        final var IMAGE_STRING = "Hello World";

        // Act
        sut.setBackgroundImage(IMAGE_STRING, false);

        // Verify
        verify(backgroundDelegateMock).setBackgroundImage(IMAGE_STRING, false);
    }

    @Test
    void stopBackgroundAudioVolumeDelegatesToBackgroundDelegate() {
        // Arrange

        // Act
        sut.stopBackgroundAudio();

        // Verify
        verify(backgroundDelegateMock).stopBackgroundAudio();
    }

    @Test
    void setBackgroundAudioVolumeDelegatesToBackgroundDelegate() {
        // Arrange
        final var expected = 0.37D;

        // Act
        sut.setBackgroundAudioVolume(expected);

        // Verify
        verify(backgroundDelegateMock).setVolume(expected);
    }

    @Test
    void getBackgroundAudioVolumeDelegatesToBackgroundDelegate() {
        // Arrange
        final var expected = 0.37D;

        when(backgroundDelegateMock.getVolume()).thenReturn(expected);

        // Act
        var actual = sut.getBackgroundAudioVolume();

        // Verify
        assertEquals(expected, actual);
    }

    @Test
    void getSceneReturnsExpectedScene() {
        // Arrange
        sut.activate();

        // Act
        var returnedScene = sut.getScene();

        // Verify
        assertEquals(scene, returnedScene);
    }

    @Test
    void getTileMapsReturnsNotNullArrayList() {
        // Arrange
        sut.activate();

        // Act
        var actual = sut.getTileMaps();

        // Verify
        assertNotNull(actual);
    }

    @Test
    void getEntitySpawnersReturnsExpectedEntitySpawners() {
        // Arrange
        sut.activate();

        // Act
        var actual = sut.getEntitySupplier();

        // Verify
        assertNotNull(actual);
    }

    @Test
    void onInputChangeNotifiesEntityCollection() {
        // Arrange
        var input = new HashSet<KeyCode>();
        input.add(KeyCode.A);

        sut.activate();
        ArgumentCaptor<KeyListener> captor = ArgumentCaptor.forClass(KeyListener.class);
        verify(keyListenerDelegateMock, times(1)).setup(any(), captor.capture(), any());

        // Act
        captor.getValue().onPressedKeysChange(input);

        // Verify
        verify(entityCollection).notifyGameObjectsOfPressedKeys(input);
    }

    @Test
    void postActivationMakesRequiredCalls() {
        // Arrange
        sut.activate();

        // Act
        sut.postActivate();

        // Verify
        verify(entityCollection).registerSupplier(any());
        verify(entityCollection).initialUpdate();
    }

    @Test
    void postActivationHidesAndShowsStage() {
        // Arrange
        sut.activate();

        // Act
        sut.postActivate();

        // Verify
        verify(stageMock).hide();
        verify(stageMock).show();
    }

    @Test
    void postActivationCalssPostActivationOnDebugger() {
        // Arrange
        sut.activate();
        when(configMock.showDebug()).thenReturn(true);

        // Act
        sut.postActivate();

        // Verify
        verify(debuggerMock).postActivation();
    }

    @Test
    void postActivateSetsIsActivationCompleteToTrue() {
        // Arrange
        sut.activate();

        // Act
        sut.postActivate();

        // Verify
        assertTrue(sut.isActivationComplete());
    }

    @Test
    void colorAdjustGetsSet() {
        // Arrange
        var colorAdjust = mock(ColorAdjust.class);

        // Act
        sut.setColorAdjust(colorAdjust);

        // Arrange
        assertEquals(sut.getColorAdjust(), colorAdjust);
    }

    private static class StaticSceneImpl extends StaticScene {

        @Override
        public void setupScene() {
        }

        @Override
        public void setupEntities() {
        }

        ColorAdjust getColorAdjust() {
            return this.colorAdjust;
        }
    }

    private static class StaticSceneKeyListenerImpl extends StaticScene implements KeyListener {

        @Override
        public void onPressedKeysChange(Set<KeyCode> pressedKeys) {

        }

        @Override
        public void setupScene() {

        }

        @Override
        public void setupEntities() {

        }
    }
}
