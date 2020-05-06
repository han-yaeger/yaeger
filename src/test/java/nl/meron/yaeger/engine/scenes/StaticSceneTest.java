package nl.meron.yaeger.engine.scenes;

import com.google.inject.Injector;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nl.meron.yaeger.engine.debug.Debugger;
import nl.meron.yaeger.engine.entities.EntityCollection;
import nl.meron.yaeger.engine.entities.EntitySupplier;
import nl.meron.yaeger.engine.entities.entity.YaegerEntity;
import nl.meron.yaeger.engine.entities.tilemap.TileMap;
import nl.meron.yaeger.engine.scenes.delegates.BackgroundDelegate;
import nl.meron.yaeger.engine.scenes.delegates.KeyListenerDelegate;
import nl.meron.yaeger.engine.entities.entity.events.userinput.KeyListener;
import nl.meron.yaeger.guice.factories.EntityCollectionFactory;
import nl.meron.yaeger.guice.factories.SceneFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaticSceneTest {
    private TestStaticScene sut;
    private SceneFactory sceneFactory;
    private EntityCollectionFactory entityCollectionFactory;

    private KeyListenerDelegate keyListenerDelegate;
    private BackgroundDelegate backgroundDelegate;
    private Debugger debugger;
    private Injector injector;

    private EntityCollection entityCollection;
    private EntitySupplier entitySupplier;
    private Group root;
    private Scene scene;
    private Stage stage;

    @BeforeEach
    void setup() {
        sut = new TestStaticScene();

        root = mock(Group.class);
        backgroundDelegate = mock(BackgroundDelegate.class);
        keyListenerDelegate = mock(KeyListenerDelegate.class);
        debugger = mock(Debugger.class);
        entitySupplier = mock(EntitySupplier.class);
        sceneFactory = mock(SceneFactory.class);
        entityCollectionFactory = mock(EntityCollectionFactory.class);
        injector = mock(Injector.class);
        stage = mock(Stage.class);

        sut.setDebugger(debugger);
        sut.setSceneFactory(sceneFactory);
        sut.setEntityCollectionFactory(entityCollectionFactory);
        sut.setRoot(root);
        sut.setBackgroundDelegate(backgroundDelegate);
        sut.setKeyListenerDelegate(keyListenerDelegate);
        sut.setEntitySupplier(entitySupplier);
        sut.setStage(stage);

        scene = mock(Scene.class);
        entityCollection = mock(EntityCollection.class);

        when(sceneFactory.create(root)).thenReturn(scene);
        when(entityCollectionFactory.create(root)).thenReturn(entityCollection);

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
    void getCollectionReturnsCollection() {
        sut = new TestStaticScene();
        entityCollectionFactory = mock(EntityCollectionFactory.class);
        entityCollection = mock(EntityCollection.class);
        // Act
        sut.setEntityCollectionFactory(entityCollectionFactory);
        sut.entityCollection = entityCollection;
        when(entityCollectionFactory.create(root)).thenReturn(entityCollection);
        // Verify
        Assertions.assertEquals(entityCollection, sut.getEntityCollection());

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
        verify(sceneFactory).create(root);
    }


    @Test
    void configureSetsUpADebugger() {
        // Arrange

        // Act
        sut.activate();

        // Verify
        verify(debugger).setup(root);
    }


    @Test
    void configureCreatesAnEntityCollection() {
        // Arrange

        // Act
        sut.activate();

        // Verify
        verify(entityCollectionFactory).create(root);
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
        when(entityCollectionFactory.create(root)).thenReturn(entityCollection);

        // Act
        sut.activate();

        // Verify
        verify(entityCollection).addStatisticsObserver(debugger);
    }

    @Test
    void destroyDelegatesDestroy() {
        // Arrange
        var children = mock(ObservableList.class);
        when(root.getChildren()).thenReturn(children);

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
    void pressingF1TogglesDebugger() {
        // Arrange
        var input = new HashSet<KeyCode>();
        input.add(KeyCode.F1);
        sut.activate();

        // Act
        sut.onPressedKeysChange(input);

        // Verify
        verify(debugger).toggle();
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
    void getSceneReturnsExpectedScene() {
        // Arrange
        sut.activate();

        // Act
        var returnedScene = sut.getScene();

        // Verify
        assertEquals(scene, returnedScene);
    }

    @Test
    void postActivationMakeRequiredCalls() {
        // Arrange
        sut.activate();

        // Act
        sut.postActivate();

        // Verify
        verify(entityCollection).registerSupplier(any());
        verify(entityCollection).initialUpdate();
        verify(debugger).toFront();
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
