package nl.meron.yaeger.engine;

import com.google.inject.Injector;
import javafx.stage.Stage;
import nl.meron.yaeger.engine.scenes.SceneCollection;
import nl.meron.yaeger.engine.scenes.YaegerScene;
import nl.meron.yaeger.guice.factories.SceneCollectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

class YaegerStageTest {

    private static final int WIDTH = 960;
    private static final int HEIGHT = 600;

    private YaegerApplication yaegerApplication;
    private Stage stage;
    private Injector injector;
    private YaegerStage sut;
    private SceneCollectionFactory sceneCollectionFactory;
    private SceneCollection sceneCollection;

    @BeforeEach
    void setUp() {
        yaegerApplication = mock(YaegerApplication.class);
        stage = mock(Stage.class);
        injector = mock(Injector.class);
        sceneCollectionFactory = mock(SceneCollectionFactory.class);
        sceneCollection = mock(SceneCollection.class);
        sut = new YaegerStage(yaegerApplication, stage);
        sut.setSceneCollectionFactory(sceneCollectionFactory);

        when(sceneCollectionFactory.create(stage)).thenReturn(sceneCollection);
    }

    @Test
    void atInitializationsetSizeIsUsed() {
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
        verify(yaegerApplication).initializeGame();
    }

    @Test
    void atInitializationSetupScenesIsCalled() {
        // Arrange

        // Act
        sut.init(injector);

        // Assert
        verify(yaegerApplication).setupScenes();
    }

    @Test
    void atInitLifeCycleMethodsAreCalledInCorrectOrder() {
        // Arrange

        // Act
        sut.init(injector);

        // Assert
        InOrder inOrder = inOrder(yaegerApplication, yaegerApplication);
        inOrder.verify(yaegerApplication).initializeGame();
        inOrder.verify(yaegerApplication).setupScenes();
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
