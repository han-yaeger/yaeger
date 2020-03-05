package nl.meron.yaeger.engine;

import com.google.inject.Injector;
import javafx.stage.Stage;
import nl.meron.yaeger.engine.scenes.SceneCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class YaegerStageTest {

    private static final int WIDTH = 960;
    private static final int HEIGHT = 600;

    private YaegerEngine yaegerApplication;
    private Stage stage;
    private Injector injector;
    private YaegerStage sut;

    @BeforeEach
    void setUp() {
        yaegerApplication = mock(YaegerEngine.class);
        stage = mock(Stage.class);
        injector = mock(Injector.class);
        sut = new YaegerStage(yaegerApplication, stage);
    }

    @Test
    void setSizeIsUsedAtInitialization() {
        // Arrange
        sut.setSize(new Size(WIDTH, HEIGHT));

        // Act
        sut.init(injector);

        // Assert
        verify(stage).setWidth(WIDTH);
        verify(stage).setHeight(HEIGHT);
    }

    @Test
    void setActiveScene() {
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
    void atInitializationSetupScenesIsaCalled() {
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
    void addScene() {
    }
}
