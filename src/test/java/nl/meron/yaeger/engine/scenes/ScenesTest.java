package nl.meron.yaeger.engine.scenes;

import com.google.inject.Injector;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
    void addingOneSceneSetActiveScene() {
        // Setup
        YaegerScene scene = mock(YaegerScene.class);
        Scene javaFXScene = mock(Scene.class);

        when(scene.getScene()).thenReturn(javaFXScene);
        doNothing().when(stage).setScene(javaFXScene);

        // Test
        sut.addScene(SceneType.INTRO, scene);

        // Verify
        Assertions.assertEquals(scene, sut.getActiveScene());
        verify(stage).setScene(javaFXScene);
        verify(scene).init(any(Injector.class));
    }

    @Test
    void addedScenesAreInitializedAfterAdding() {
        // Setup
        YaegerScene scene = mock(YaegerScene.class);
        Scene javaFXScene = mock(Scene.class);

        when(scene.getScene()).thenReturn(javaFXScene);
        doNothing().when(stage).setScene(javaFXScene);

        // Test
        sut.addScene(SceneType.INTRO, scene);

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
        sut.addScene(SceneType.INTRO, yaegerScene1);
        sut.addScene(SceneType.LEVEL_ONE, yaegerScene2);
        sut.addScene(SceneType.LEVEL_TWO, yaegerScene3);
        sut.addScene(SceneType.LEVEL_THREE, yaegerScene4);

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

        sut.addScene(SceneType.INTRO, intro);
        sut.addScene(SceneType.LEVEL_ONE, level1);

        // Test
        sut.setActive(SceneType.LEVEL_ONE);

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

        sut.addScene(SceneType.INTRO, intro);
        sut.addScene(SceneType.LEVEL_ONE, level1);

        // Test
        sut.setActive(SceneType.LEVEL_ONE);

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
