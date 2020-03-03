package nl.meron.yaeger.engine.scenes;

import com.google.inject.Injector;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import nl.meron.yaeger.engine.debug.Debugger;
import nl.meron.yaeger.engine.entities.EntityCollection;
import nl.meron.yaeger.engine.entities.EntitySupplier;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.scenes.StaticScene;
import nl.meron.yaeger.engine.scenes.delegates.BackgroundDelegate;
import nl.meron.yaeger.engine.scenes.delegates.KeyListenerDelegate;
import nl.meron.yaeger.engine.entities.entity.events.userinput.KeyListener;
import nl.meron.yaeger.guice.factories.EntityCollectionFactory;
import nl.meron.yaeger.guice.factories.SceneFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

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
        injector = Mockito.mock(Injector.class);

        sut.setDebugger(debugger);
        sut.setSceneFactory(sceneFactory);
        sut.setEntityCollectionFactory(entityCollectionFactory);
        sut.setRoot(root);
        sut.setBackgroundDelegate(backgroundDelegate);
        sut.setKeyListenerDelegate(keyListenerDelegate);
        sut.setEntitySupplier(entitySupplier);

        scene = mock(Scene.class);
        entityCollection = mock(EntityCollection.class);

        when(sceneFactory.create(root)).thenReturn(scene);
        when(entityCollectionFactory.create(root)).thenReturn(entityCollection);

        sut.init(injector);
    }

    @Test
    void configureCreatesAScene() {
        // Arrange

        // Act
        sut.configure();

        // Verify
        verify(sceneFactory).create(root);
    }


    @Test
    void configureSetsUpADebugger() {
        // Arrange

        // Act
        sut.configure();

        // Verify
        verify(debugger).setup(root);
    }


    @Test
    void configureCreatesAnEntityCollection() {
        // Arrange

        // Act
        sut.configure();

        // Verify
        verify(entityCollectionFactory).create(root);
    }

    @Test
    void configureInjectDependenciesIntoEntityCollection() {
        // Arrange

        // Act
        sut.configure();

        // Verify
        verify(injector).injectMembers(any());
    }

    @Test
    void configureSetsUpAKeyListenerDelegate() {
        // Arrange

        // Act
        sut.configure();

        // Verify
        verify(keyListenerDelegate).setup(any(Scene.class), any(KeyListener.class));
    }


    @Test
    void configureAddsTheDebuggerAsAStatisticsObserverToTheEntityCollection() {
        // Arrange
        var entityCollection = mock(EntityCollection.class);
        when(entityCollectionFactory.create(root)).thenReturn(entityCollection);

        // Act
        sut.configure();

        // Verify
        verify(entityCollection).addStatisticsObserver(debugger);
    }

    @Test
    void destroyDelegatesDestroy() {
        // Arrange
        var children = mock(ObservableList.class);
        when(root.getChildren()).thenReturn(children);

        sut.configure();

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
        sut.configure();

        var testEntity = mock(Entity.class);

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
        sut.configure();

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
        sut.configure();

        // Act
        var returnedScene = sut.getScene();

        // Verify
        Assertions.assertEquals(scene, returnedScene);
    }

    @Test
    void postActivationMakeRequiredCalls() {
        // Arrange
        sut.configure();

        // Act
        sut.postActivation();

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
