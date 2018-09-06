package nl.han.ica.yaeger.scene;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ScenesTest {

    private Stage stage;
    private Scenes scenes;

    @BeforeEach
    void setup() {
        stage = mock(Stage.class);
        scenes = new Scenes(stage);
    }

    @Test
    void addingOneSceneSetActiveScene() {
        // Setup
        YaegerScene yaegerScene = mock(YaegerScene.class);
        Scene javaFXScene = mock(Scene.class);

        when(yaegerScene.getScene()).thenReturn(javaFXScene);
        doNothing().when(stage).setScene(javaFXScene);

        // Test
        scenes.addScene(SceneType.INTRO, yaegerScene);

        // Verify
        Assertions.assertEquals(yaegerScene, scenes.getActiveScene());
        verify(stage).setScene(javaFXScene);
        verify(yaegerScene).setupScene();
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
        scenes.addScene(SceneType.INTRO, yaegerScene1);
        scenes.addScene(SceneType.LEVEL_ONE, yaegerScene2);
        scenes.addScene(SceneType.LEVEL_TWO, yaegerScene3);
        scenes.addScene(SceneType.LEVEL_THREE, yaegerScene4);

        // Verify
        Assertions.assertEquals(yaegerScene1, scenes.getActiveScene());
    }

    @Test
    void selectingADifferentSceneChangesTheActiveScene() {
        // Setup
        YaegerScene intro = mock(YaegerScene.class);
        YaegerScene level1 = mock(YaegerScene.class);

        Scene javaFXScene = mock(Scene.class);

        when(intro.getScene()).thenReturn(javaFXScene);
        when(level1.getScene()).thenReturn(javaFXScene);

        scenes.addScene(SceneType.INTRO, intro);
        scenes.addScene(SceneType.LEVEL_ONE, level1);

        // Test
        scenes.setActive(SceneType.LEVEL_ONE);

        // Verify
        Assertions.assertEquals(level1, scenes.getActiveScene());
        verify(level1).setupScene();
    }

    @Test
    void selectingADifferentSceneTearsDownTheCurrentActiveScene() {
        // Setup
        YaegerScene intro = mock(YaegerScene.class);
        YaegerScene level1 = mock(YaegerScene.class);

        Scene javaFXScene = mock(Scene.class);

        when(intro.getScene()).thenReturn(javaFXScene);
        when(level1.getScene()).thenReturn(javaFXScene);

        scenes.addScene(SceneType.INTRO, intro);
        scenes.addScene(SceneType.LEVEL_ONE, level1);

        // Test
        scenes.setActive(SceneType.LEVEL_ONE);

        // Verify
        verify(intro).tearDownScene();
    }
}
