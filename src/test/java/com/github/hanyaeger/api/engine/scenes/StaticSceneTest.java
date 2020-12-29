package com.github.hanyaeger.api.engine.scenes;

import com.github.hanyaeger.api.engine.YaegerConfig;
import com.github.hanyaeger.api.engine.debug.Debugger;
import com.github.hanyaeger.api.engine.entities.EntitySupplier;
import com.github.hanyaeger.api.engine.scenes.delegates.BackgroundDelegate;
import com.github.hanyaeger.api.engine.scenes.delegates.KeyListenerDelegate;
import com.github.hanyaeger.api.guice.factories.EntityCollectionFactory;
import com.github.hanyaeger.api.guice.factories.SceneFactory;
import com.google.inject.Injector;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.github.hanyaeger.api.engine.entities.EntityCollection;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.events.userinput.KeyListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaticSceneTest {
    private StaticSceneImpl sut;
    private SceneFactory sceneFactory;
    private EntityCollectionFactory entityCollectionFactory;

    private KeyListenerDelegate keyListenerDelegate;
    private BackgroundDelegate backgroundDelegate;
    private Debugger debugger;
    private Injector injector;

    private EntityCollection entityCollection;
    private EntitySupplier entitySupplier;
    private Pane pane;
    private Scene scene;
    private Stage stage;
    private YaegerConfig config;

    @BeforeEach
    void setup() {
        sut = new StaticSceneImpl();

        pane = mock(Pane.class, withSettings().withoutAnnotations());
        backgroundDelegate = mock(BackgroundDelegate.class);
        keyListenerDelegate = mock(KeyListenerDelegate.class);
        debugger = mock(Debugger.class);
        entitySupplier = mock(EntitySupplier.class);
        sceneFactory = mock(SceneFactory.class);
        entityCollectionFactory = mock(EntityCollectionFactory.class);
        injector = mock(Injector.class);
        stage = mock(Stage.class);
        config = mock(YaegerConfig.class);

        sut.setDebugger(debugger);
        sut.setSceneFactory(sceneFactory);
        sut.setEntityCollectionFactory(entityCollectionFactory);
        sut.setPane(pane);
        sut.setBackgroundDelegate(backgroundDelegate);
        sut.setKeyListenerDelegate(keyListenerDelegate);
        sut.setEntitySupplier(entitySupplier);
        sut.setStage(stage);
        sut.setConfig(config);

        scene = mock(Scene.class);
        entityCollection = mock(EntityCollection.class);

        when(sceneFactory.create(pane)).thenReturn(scene);
        when(entityCollectionFactory.create(pane, config)).thenReturn(entityCollection);

        sut.init(injector);
    }

    @Test
    void getInjectorReturnsInjectorProvidedThroughInit() {
        // Arrange

        // Act
        var actual = sut.getInjector();

        // Verify
        assertEquals(actual, injector);
    }

    @Test
    void getStageReturnsSetStage() {
        // Arrange

        // Act
        var actual = sut.getStage();

        // Verify
        assertEquals(actual, stage);
    }

    @Test
    void configureCreatesAScene() {
        // Arrange

        // Act
        sut.activate();

        // Verify
        verify(sceneFactory).create(pane);
    }


    @Test
    void configureSetsUpADebugger() {
        // Arrange

        // Act
        sut.activate();

        // Verify
        verify(debugger).setup(pane);
    }


    @Test
    void configureCreatesAnEntityCollection() {
        // Arrange

        // Act
        sut.activate();

        // Verify
        verify(entityCollectionFactory).create(pane, config);
    }

    @Test
    void configureInjectDependenciesIntoEntityCollection() {
        // Arrange

        // Act
        sut.activate();

        // Verify
        verify(injector).injectMembers(any());
    }

    @Test
    void configureSetsUpAKeyListenerDelegate() {
        // Arrange

        // Act
        sut.activate();

        // Verify
        verify(keyListenerDelegate).setup(any(Scene.class), any(KeyListener.class));
    }


    @Test
    void configureAddsTheDebuggerAsAStatisticsObserverToTheEntityCollection() {
        // Arrange
        var entityCollection = mock(EntityCollection.class);
        when(entityCollectionFactory.create(pane, config)).thenReturn(entityCollection);

        // Act
        sut.activate();

        // Verify
        verify(entityCollection).addStatisticsObserver(debugger);
    }

    @Test
    void destroyDelegatesDestroy() {
        // Arrange
        var children = mock(ObservableList.class);
        when(pane.getChildren()).thenReturn(children);

        sut.activate();

        // Act
        sut.destroy();

        // Verify
        verify(keyListenerDelegate).tearDown(scene);
        verify(backgroundDelegate).destroy();
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
        verify(entitySupplier).add(testEntity);
    }

    @Test
    void setBackgroundAudioDelegatesToBackgroundDelegate() {
        // Arrange
        final var AUDIO_STRING = "Hello World";

        // Act
        sut.setBackgroundAudio(AUDIO_STRING);

        // Verify
        verify(backgroundDelegate).setBackgroundAudio(AUDIO_STRING);
    }

    @Test
    void implementingKeyListenerAddsSceneToKeyListeners() {
        // Arrange
        final var sut = new StaticSceneKeyListenerImpl();

        sut.setDebugger(debugger);
        sut.setSceneFactory(sceneFactory);
        sut.setEntityCollectionFactory(entityCollectionFactory);
        sut.setPane(pane);
        sut.setBackgroundDelegate(backgroundDelegate);
        sut.setKeyListenerDelegate(keyListenerDelegate);
        sut.setEntitySupplier(entitySupplier);
        sut.setStage(stage);
        sut.setConfig(config);

        scene = mock(Scene.class);
        entityCollection = mock(EntityCollection.class);

        when(sceneFactory.create(pane)).thenReturn(scene);
        when(entityCollectionFactory.create(pane, config)).thenReturn(entityCollection);

        sut.init(injector);

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
        verify(backgroundDelegate).setBackgroundColor(color);
    }

    @Test
    void setBackgroundImageDelegatesToBackgroundDelegate() {
        // Arrange
        final var IMAGE_STRING = "Hello World";

        // Act
        sut.setBackgroundImage(IMAGE_STRING);

        // Verify
        verify(backgroundDelegate).setBackgroundImage(IMAGE_STRING);
    }

    @Test
    void setBrightnessDelegatesToTheColorAdjust() {
        // Arrange
        var brightness = -0.37;
        var colorAdjust = mock(ColorAdjust.class);

        sut.setColorAdjust(colorAdjust);

        // Act
        sut.setBrightness(brightness);

        // Verify
        verify(colorAdjust).setBrightness(brightness);
    }

    @Test
    void getBrightnessDelegatesToTheColorAdjust() {
        // Arrange
        var brightness = -0.37;
        var colorAdjust = mock(ColorAdjust.class);

        sut.setColorAdjust(colorAdjust);

        when(colorAdjust.getBrightness()).thenReturn(brightness);

        // Act
        double actual = sut.getBrightness();

        // Verify
        assertTrue(Double.compare(actual, brightness) == 0);
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
    void pressingF1TogglesDebugger() {
        // Arrange
        var input = new HashSet<KeyCode>();
        input.add(KeyCode.F1);

        sut.activate();
        ArgumentCaptor<KeyListener> captor = ArgumentCaptor.forClass(KeyListener.class);
        verify(keyListenerDelegate, times(1)).setup(any(), captor.capture());

        // Act
        captor.getValue().onPressedKeysChange(input);

        // Verify
        verify(debugger).toggle();
    }

    @Test
    void pressingF1NotifiesEntityCollection() {
        // Arrange
        var input = new HashSet<KeyCode>();
        input.add(KeyCode.F1);

        sut.activate();
        ArgumentCaptor<KeyListener> captor = ArgumentCaptor.forClass(KeyListener.class);
        verify(keyListenerDelegate, times(1)).setup(any(), captor.capture());

        // Act
        captor.getValue().onPressedKeysChange(input);

        // Verify
        verify(debugger).toggle();
    }

    @Test
    void onInputChangeNotifiesEntityCollection() {
        // Arrange
        var input = new HashSet<KeyCode>();
        input.add(KeyCode.A);

        sut.activate();
        ArgumentCaptor<KeyListener> captor = ArgumentCaptor.forClass(KeyListener.class);
        verify(keyListenerDelegate, times(1)).setup(any(), captor.capture());

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
        verify(debugger).toFront();
    }

    private class StaticSceneImpl extends StaticScene {

        @Override
        public void setupScene() {
        }

        @Override
        public void setupEntities() {
        }
    }

    private class StaticSceneKeyListenerImpl extends StaticScene implements KeyListener {

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
