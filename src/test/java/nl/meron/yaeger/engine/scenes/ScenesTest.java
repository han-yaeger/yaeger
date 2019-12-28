package nl.meron.yaeger.engine.scenes;

import com.google.inject.Injector;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.meron.yaeger.engine.exceptions.YaegerSceneNotAvailableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ScenesTest {

    private Stage stage;
    private Scenes sut;
    private Injector injector;

    @BeforeEach
    void setup() {
        stage = mock(Stage.class);
        sut = new Scenes(stage);
        injector = mock(Injector.class);
    }

    @Test
    void addingOneSceneSetAsActiveScene() {
        // Setup
        YaegerScene scene = mock(YaegerScene.class);
        Scene javaFXScene = mock(Scene.class);

        when(scene.getScene()).thenReturn(javaFXScene);
        doNothing().when(stage).setScene(javaFXScene);

        // Test
        sut.addScene(0, scene);

        // Verify
        Assertions.assertEquals(scene, sut.getActiveScene());
        verify(stage).setScene(javaFXScene);
        verify(scene).init(any(Injector.class));
    }

    @Test
    void requestingUnavailableSceneThrowsException() {
        // Setup
        var sceneType = 0;

        // Test
        var yaegerSceneNotAvailableException = Assertions.assertThrows(YaegerSceneNotAvailableException.class, () -> sut.setActive(sceneType));

        // Verify
        Assertions.assertEquals(sceneType, yaegerSceneNotAvailableException.getType());
    }

    @Test
    void addedScenesAreInitializedAfterAdding() {
        // Setup
        YaegerScene scene = mock(YaegerScene.class);
        Scene javaFXScene = mock(Scene.class);

        when(scene.getScene()).thenReturn(javaFXScene);
        doNothing().when(stage).setScene(javaFXScene);

        // Test
        sut.addScene(0, scene);

        // Verify
        verify(scene).init(any(Injector.class));
    }

    @Test
    void addingMultipleScenesSetsFirstAsActiveScene() {
        // Setup
        YaegerScene yaegerScene1 = mock(YaegerScene.class);
        YaegerScene yaegerScene2 = mock(YaegerScene.class);
        YaegerScene yaegerScene3 = mock(YaegerScene.class);
        YaegerScene yaegerScene4 = mock(YaegerScene.class);

        Scene javaFXScene = mock(Scene.class);

        when(yaegerScene1.getScene()).thenReturn(javaFXScene);
        when(yaegerScene2.getScene()).thenReturn(javaFXScene);
        when(yaegerScene3.getScene()).thenReturn(javaFXScene);
        when(yaegerScene4.getScene()).thenReturn(javaFXScene);

        // Test
        sut.addScene(0, yaegerScene1);
        sut.addScene(1, yaegerScene2);
        sut.addScene(2, yaegerScene3);
        sut.addScene(3, yaegerScene4);

        // Verify
        Assertions.assertEquals(yaegerScene1, sut.getActiveScene());
    }

    @Test
    void selectingADifferentSceneChangesTheActiveScene() {
        // Setup
        YaegerScene intro = mock(YaegerScene.class);
        YaegerScene level1 = mock(YaegerScene.class);

        Scene javaFXScene = mock(Scene.class);

        when(intro.getScene()).thenReturn(javaFXScene);
        when(level1.getScene()).thenReturn(javaFXScene);

        sut.addScene(0, intro);
        sut.addScene(1, level1);

        // Test
        sut.setActive(1);

        // Verify
        Assertions.assertEquals(level1, sut.getActiveScene());
        verify(level1).init(any(Injector.class));
    }

    @Test
    void selectingADifferentSceneTearsDownTheCurrentActiveScene() {
        // Setup
        YaegerScene intro = mock(YaegerScene.class);
        YaegerScene level1 = mock(YaegerScene.class);

        Scene javaFXScene = mock(Scene.class);

        when(intro.getScene()).thenReturn(javaFXScene);
        when(level1.getScene()).thenReturn(javaFXScene);

        sut.addScene(0, intro);
        sut.addScene(1, level1);

        // Test
        sut.setActive(1);

        // Verify
        verify(intro).destroy();
    }

    @Test
    void equalsSucceedsWithSameInstance() {
        // Setup

        // Test
        boolean equals = sut.equals(sut);

        // Verify
        Assertions.assertTrue(equals);
    }

    @Test
    void equalsFailsWithDifferentInstance() {
        // Setup
        Stage stage2 = mock(Stage.class);
        Scenes scenes2 = new Scenes(stage2);

        // Test
        boolean equals = sut.equals(scenes2);

        // Verify
        Assertions.assertFalse(equals);
    }

    @Test
    void equalsFailsWithNull() {
        // Setup

        // Test
        boolean equals = sut.equals(null);

        // Verify
        Assertions.assertFalse(equals);
    }

    @Test
    void equalsFailsWithDifferentClass() {
        // Setup

        // Test
        boolean equals = sut.equals(stage);

        // Verify
        Assertions.assertFalse(equals);
    }

    @Test
    void differentInstancesHaveDifferentHashCodes() {
        // Setup
        Stage stage2 = mock(Stage.class);
        Scenes scenes2 = new Scenes(stage2);

        // Test
        int hash1 = sut.hashCode();
        int hash2 = scenes2.hashCode();

        // Verify
        Assertions.assertNotEquals(hash1, hash2);
    }
}
