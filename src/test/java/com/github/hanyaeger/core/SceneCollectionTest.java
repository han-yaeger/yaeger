package com.github.hanyaeger.core;

import com.github.hanyaeger.core.annotations.AnnotationProcessor;
import com.github.hanyaeger.core.exceptions.YaegerSceneNotAvailableException;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.core.scenes.splash.SplashScene;
import com.github.hanyaeger.core.scenes.splash.SplashScreenFactory;
import com.google.inject.Injector;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

class SceneCollectionTest {

    private Stage stage;
    private SceneCollection sut;
    private Injector injector;
    private AnnotationProcessor annotationProcessor;
    private SplashScreenFactory splashScreenFactory;
    private SplashScene splashScene;
    private ArgumentCaptor<Runnable> loadFirstSceneCallBack;
    private YaegerConfig yaegerConfig;

    @BeforeEach
    void setup() {
        stage = mock(Stage.class);
        injector = mock(Injector.class);
        annotationProcessor = mock(AnnotationProcessor.class);
        splashScreenFactory = mock(SplashScreenFactory.class);
        splashScene = mock(SplashScene.class);

        yaegerConfig = mock(YaegerConfig.class);
        when(yaegerConfig.showSplash()).thenReturn(true);

        loadFirstSceneCallBack = ArgumentCaptor.forClass(Runnable.class);
        when(splashScreenFactory.create(loadFirstSceneCallBack.capture())).thenReturn(splashScene);
        sut = new SceneCollection(stage, yaegerConfig);
        sut.init(injector);
        sut.setAnnotationProcessor(annotationProcessor);
        sut.setSplashScreenFactory(splashScreenFactory);
    }

    @Test
    void postSetupSceneDoesNotCreatesTheSplashScreenForNoSplashConfig() {
        // Arrange
        when(yaegerConfig.showSplash()).thenReturn(false);
        // Act
        sut.postSetupScenes();

        // Assert
        verifyNoInteractions(splashScreenFactory);
    }

    @Test
    void postSetupSceneCreatesTheSplashScreenForDefaultConfig() {
        // Arrange

        // Act
        sut.postSetupScenes();

        // Assert
        verify(splashScreenFactory).create(any());
    }

    @Test
    void splashScreenReceivesInjector() {
        // Arrange

        // Act
        sut.postSetupScenes();

        // Assert
        verify(splashScene).init(injector);
    }

    @Test
    void splashScreenReceivesStage() {
        // Arrange

        // Act
        sut.postSetupScenes();

        // Assert
        verify(splashScene).setStage(stage);
    }

    @Test
    void splashScreenGetsActivated() {
        // Arrange

        // Act
        sut.postSetupScenes();

        // Assert
        verify(splashScene).postActivate();
    }

    @Test
    void firstAddedSceneLoadedAfterSplashScreen() {
        // Arrange
        var scene = mock(YaegerScene.class);
        var javaFXScene = mock(Scene.class);

        when(scene.getScene()).thenReturn(javaFXScene);
        doNothing().when(stage).setScene(javaFXScene);

        sut.addScene(0, scene);
        sut.postSetupScenes();

        // Act
        loadFirstSceneCallBack.getValue().run();

        // Verify
        Assertions.assertEquals(scene, sut.getActiveScene());
        verify(stage).setScene(javaFXScene);
        verify(scene).init(any(Injector.class));
    }

    @Test
    void yaegerExitsIfNoSceneWasAddedAfterSplashScreenFinishes() {
        // Arrange
        sut.postSetupScenes();

        // Act
        loadFirstSceneCallBack.getValue().run();

        // Verify
        verify(stage).close();
    }

    @Test
    void requestingUnavailableSceneThrowsException() {
        // Arrange
        var sceneType = 0;

        // Act
        var yaegerSceneNotAvailableException = Assertions.assertThrows(YaegerSceneNotAvailableException.class, () -> sut.setActive(sceneType));

        // Verify
        Assertions.assertEquals(sceneType, yaegerSceneNotAvailableException.getId());
    }

    @Test
    void addedScenesAreInitializedAfterAdding() {
        // Arrange
        var scene = mock(YaegerScene.class);
        var javaFXScene = mock(Scene.class);

        when(scene.getScene()).thenReturn(javaFXScene);
        doNothing().when(stage).setScene(javaFXScene);

        // Act
        sut.addScene(0, scene);

        // Verify
        verify(scene).init(any(Injector.class));
    }

    @Test
    void addingMultipleScenesSetsFirstAsActiveScene() {
        // Arrange
        var yaegerScene1 = mock(YaegerScene.class);
        var yaegerScene2 = mock(YaegerScene.class);
        var yaegerScene3 = mock(YaegerScene.class);
        var yaegerScene4 = mock(YaegerScene.class);

        var javaFXScene = mock(Scene.class);

        when(yaegerScene1.getScene()).thenReturn(javaFXScene);
        when(yaegerScene2.getScene()).thenReturn(javaFXScene);
        when(yaegerScene3.getScene()).thenReturn(javaFXScene);
        when(yaegerScene4.getScene()).thenReturn(javaFXScene);

        // Act
        sut.addScene(0, yaegerScene1);
        sut.addScene(1, yaegerScene2);
        sut.addScene(2, yaegerScene3);
        sut.addScene(3, yaegerScene4);

        sut.postSetupScenes();
        loadFirstSceneCallBack.getValue().run();

        // Verify
        Assertions.assertEquals(yaegerScene1, sut.getActiveScene());
    }

    @Test
    void activatingASceneCallsTheAnnotationProcessor() {
        // Arrange
        var intro = mock(YaegerScene.class);

        var javaFXScene = mock(Scene.class);

        when(intro.getScene()).thenReturn(javaFXScene);

        // Act
        sut.addScene(0, intro);

        sut.postSetupScenes();
        loadFirstSceneCallBack.getValue().run();

        // Verify
        verify(annotationProcessor).invokeActivators(intro);
        verify(annotationProcessor).configureUpdateDelegators(intro);
    }

    @Test
    void selectingADifferentSceneChangesTheActiveScene() {
        // Arrange
        var intro = mock(YaegerScene.class);
        var level1 = mock(YaegerScene.class);

        var javaFXScene = mock(Scene.class);

        when(intro.getScene()).thenReturn(javaFXScene);
        when(level1.getScene()).thenReturn(javaFXScene);

        sut.addScene(0, intro);
        sut.addScene(1, level1);

        // Act
        sut.setActive(1);

        // Verify
        Assertions.assertEquals(level1, sut.getActiveScene());
        verify(level1).init(any(Injector.class));
    }

    @Test
    void selectingADifferentSceneTearsDownTheCurrentActiveScene() {
        // Arrange
        var intro = mock(YaegerScene.class);
        var level1 = mock(YaegerScene.class);

        var javaFXScene = mock(Scene.class);

        when(intro.getScene()).thenReturn(javaFXScene);
        when(level1.getScene()).thenReturn(javaFXScene);

        sut.addScene(0, intro);
        sut.addScene(1, level1);

        sut.postSetupScenes();
        loadFirstSceneCallBack.getValue().run();

        // Act
        sut.setActive(1);

        // Verify
        verify(intro).destroy();
    }

    @Test
    void equalsSucceedsWithSameInstance() {
        // Arrange

        // Act
        var equals = sut.equals(sut);

        // Verify
        Assertions.assertTrue(equals);
    }

    @Test
    void equalsFailsWithDifferentInstance() {
        // Arrange
        var stage2 = mock(Stage.class);
        SceneCollection sceneCollection2 = new SceneCollection(stage2, yaegerConfig);
        sceneCollection2.init(injector);

        // Act
        var equals = sut.equals(sceneCollection2);

        // Verify
        Assertions.assertFalse(equals);
    }

    @Test
    void equalsFailsWithNull() {
        // Arrange

        // Act
        var equals = sut.equals(null);

        // Verify
        Assertions.assertFalse(equals);
    }

    @Test
    void equalsFailsWithDifferentClass() {
        // Arrange

        // Act
        var equals = sut.equals(stage);

        // Verify
        Assertions.assertFalse(equals);
    }

    @Test
    void differentInstancesHaveDifferentHashCodes() {
        // Arrange
        var stage2 = mock(Stage.class);
        var sceneCollection2 = new SceneCollection(stage2, yaegerConfig);
        sceneCollection2.init(injector);

        // Act
        var hash1 = sut.hashCode();
        var hash2 = sceneCollection2.hashCode();

        // Verify
        Assertions.assertNotEquals(hash1, hash2);
    }
}
