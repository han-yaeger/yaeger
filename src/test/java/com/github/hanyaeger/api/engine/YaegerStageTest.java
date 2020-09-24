package com.github.hanyaeger.api.engine;

import com.github.hanyaeger.api.engine.scenes.SceneCollection;
import com.github.hanyaeger.api.guice.factories.SceneCollectionFactory;
import com.google.inject.Injector;
import javafx.stage.Stage;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

class YaegerStageTest {

    private static final int WIDTH = 960;
    private static final int HEIGHT = 600;

    private YaegerGame yaegerGame;
    private Stage stage;
    private YaegerConfig yaegerConfig;
    private Injector injector;
    private YaegerStage sut;
    private SceneCollectionFactory sceneCollectionFactory;
    private SceneCollection sceneCollection;

    @BeforeEach
    void setUp() {
        yaegerGame = mock(YaegerGame.class);
        stage = mock(Stage.class);
        injector = mock(Injector.class);
        yaegerConfig = new YaegerConfig();
        sceneCollectionFactory = mock(SceneCollectionFactory.class);
        sceneCollection = mock(SceneCollection.class);

        sut = new YaegerStage(yaegerGame, stage, yaegerConfig);
        sut.setSceneCollectionFactory(sceneCollectionFactory);

        when(sceneCollectionFactory.create(stage, yaegerConfig)).thenReturn(sceneCollection);
    }

    @Test
    void atInitSceneCollectionFactoryCreateIsCalledWithCorrectConfig() {
        // Arrange

        // Act
        sut.init(injector);

        // Assert
        verify(sceneCollectionFactory).create(stage, yaegerConfig);
    }

    @Test
    void atInitializationSetSizeIsUsed() {
        // Arrange
        sut.setSize(new Size(WIDTH, HEIGHT));

        // Act
        sut.init(injector);

        // Assert
        verify(stage).setWidth(WIDTH);
        verify(stage).setHeight(HEIGHT);
    }

    @Test
    void atInitializationInitializeGameIsaCalled() {
        // Arrange

        // Act
        sut.init(injector);

        // Assert
        verify(yaegerGame).setupGame();
    }

    @Test
    void atInitializationSetupScenesIsCalled() {
        // Arrange

        // Act
        sut.init(injector);

        // Assert
        verify(yaegerGame).setupScenes();
    }

    @Test
    void atInitLifeCycleMethodsAreCalledInCorrectOrder() {
        // Arrange

        // Act
        sut.init(injector);

        // Assert
        InOrder inOrder = inOrder(yaegerGame, yaegerGame);
        inOrder.verify(yaegerGame).setupGame();
        inOrder.verify(yaegerGame).setupScenes();
    }

    @Test
    void atInitializationMembersAreInjectedIntoTheSceneCollection() {
        // Arrange

        // Act
        sut.init(injector);

        // Assert
        verify(injector).injectMembers(any(SceneCollection.class));
    }

    @Test
    void atInitializationPostSetupScenesIsCalled() {
        // Arrange

        // Act
        sut.init(injector);

        // Assert
        verify(sceneCollection).postSetupScenes();
    }

    @Test
    void atInitializationWidthAndHeightAreSetAfterInitializeGame() {
        // Arrange

        // Act
        sut.init(injector);

        // Assert
        InOrder inOrder = inOrder(yaegerGame, stage);
        inOrder.verify(yaegerGame).setupGame();
        inOrder.verify(stage).setWidth(anyDouble());
        inOrder.verify(stage).setHeight(anyDouble());
    }

    @Test
    void callingQuitDelegatesToStage() {
        // Arrange
        var expected = "Title";

        // Act
        sut.setTitle(expected);

        // Assert
        verify(stage).setTitle(expected);
    }

    @Test
    void setTitleDelgatesToStage() {
        // Arrange

        // Act
        sut.quit();

        // Assert
        verify(stage).close();
    }

    @Test
    void setActiveSceneDelegatesToSceneCollection() {
        // Arrange
        var expected = 1;
        sut.init(injector);

        // Act
        sut.setActiveScene(expected);

        // Assert
        verify(sceneCollection).setActive(expected);
    }

    @Test
    void addSceneDelegatesToSceneCollection() {
        // Arrange
        var expectedIndex = 1;
        var expectedScene = mock(YaegerScene.class);
        sut.init(injector);

        // Act
        sut.addScene(expectedIndex, expectedScene);

        // Assert
        verify(sceneCollection).addScene(expectedIndex, expectedScene);
    }
}
