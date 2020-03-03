package nl.meron.yaeger.engine.scenes;

import com.google.inject.Injector;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.meron.yaeger.engine.annotations.AnnotationProcessor;
import nl.meron.yaeger.engine.exceptions.YaegerSceneNotAvailableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class SceneCollectionTest {

    private Stage stage;
    private SceneCollection sut;
    private Injector injector;
    private AnnotationProcessor annotationProcessor;

    @BeforeEach
    void setup() {
        stage = mock(Stage.class);
        injector = mock(Injector.class);
        annotationProcessor = mock(AnnotationProcessor.class);
        sut = new SceneCollection(stage, injector);
        sut.setAnnotationProcessor(annotationProcessor);
    }

    @Test
    void addingOneSceneSetAsActiveScene() {
        // Arrange
        YaegerScene scene = mock(YaegerScene.class);
        Scene javaFXScene = mock(Scene.class);

        when(scene.getScene()).thenReturn(javaFXScene);
        doNothing().when(stage).setScene(javaFXScene);

        // Act
        sut.addScene(0, scene);

        // Verify
        Assertions.assertEquals(scene, sut.getActiveScene());
        verify(stage).setScene(javaFXScene);
        verify(scene).init(any(Injector.class));
    }

    @Test
    void requestingUnavailableSceneThrowsException() {
        // Arrange
        var sceneType = 0;

        // Act
        var yaegerSceneNotAvailableException = Assertions.assertThrows(YaegerSceneNotAvailableException.class, () -> sut.setActive(sceneType));

        // Verify
        Assertions.assertEquals(sceneType, yaegerSceneNotAvailableException.getType());
    }

    @Test
    void addedScenesAreInitializedAfterAdding() {
        // Arrange
        YaegerScene scene = mock(YaegerScene.class);
        Scene javaFXScene = mock(Scene.class);

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
        YaegerScene yaegerScene1 = mock(YaegerScene.class);
        YaegerScene yaegerScene2 = mock(YaegerScene.class);
        YaegerScene yaegerScene3 = mock(YaegerScene.class);
        YaegerScene yaegerScene4 = mock(YaegerScene.class);

        Scene javaFXScene = mock(Scene.class);

        when(yaegerScene1.getScene()).thenReturn(javaFXScene);
        when(yaegerScene2.getScene()).thenReturn(javaFXScene);
        when(yaegerScene3.getScene()).thenReturn(javaFXScene);
        when(yaegerScene4.getScene()).thenReturn(javaFXScene);

        // Act
        sut.addScene(0, yaegerScene1);
        sut.addScene(1, yaegerScene2);
        sut.addScene(2, yaegerScene3);
        sut.addScene(3, yaegerScene4);

        // Verify
        Assertions.assertEquals(yaegerScene1, sut.getActiveScene());
    }

    @Test
    void activatingASceneCallsTheAnnotationProcessor() {
        // Arrange
        YaegerScene intro = mock(YaegerScene.class);

        Scene javaFXScene = mock(Scene.class);

        when(intro.getScene()).thenReturn(javaFXScene);

        // Act
        sut.addScene(0, intro);

        // Verify
        verify(annotationProcessor).invokeInitializers(intro);
        verify(annotationProcessor).configureUpdateDelegators(intro);
    }

    @Test
    void selectingADifferentSceneChangesTheActiveScene() {
        // Arrange
        YaegerScene intro = mock(YaegerScene.class);
        YaegerScene level1 = mock(YaegerScene.class);

        Scene javaFXScene = mock(Scene.class);

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
        YaegerScene intro = mock(YaegerScene.class);
        YaegerScene level1 = mock(YaegerScene.class);

        Scene javaFXScene = mock(Scene.class);

        when(intro.getScene()).thenReturn(javaFXScene);
        when(level1.getScene()).thenReturn(javaFXScene);

        sut.addScene(0, intro);
        sut.addScene(1, level1);

        // Act
        sut.setActive(1);

        // Verify
        verify(intro).destroy();
    }

    @Test
    void equalsSucceedsWithSameInstance() {
        // Arrange

        // Act
        boolean equals = sut.equals(sut);

        // Verify
        Assertions.assertTrue(equals);
    }

    @Test
    void equalsFailsWithDifferentInstance() {
        // Arrange
        Stage stage2 = mock(Stage.class);
        SceneCollection sceneCollection2 = new SceneCollection(stage2, injector);

        // Act
        boolean equals = sut.equals(sceneCollection2);

        // Verify
        Assertions.assertFalse(equals);
    }

    @Test
    void equalsFailsWithNull() {
        // Arrange

        // Act
        boolean equals = sut.equals(null);

        // Verify
        Assertions.assertFalse(equals);
    }

    @Test
    void equalsFailsWithDifferentClass() {
        // Arrange

        // Act
        boolean equals = sut.equals(stage);

        // Verify
        Assertions.assertFalse(equals);
    }

    @Test
    void differentInstancesHaveDifferentHashCodes() {
        // Arrange
        Stage stage2 = mock(Stage.class);
        SceneCollection sceneCollection2 = new SceneCollection(stage2, injector);

        // Act
        int hash1 = sut.hashCode();
        int hash2 = sceneCollection2.hashCode();

        // Verify
        Assertions.assertNotEquals(hash1, hash2);
    }
}
