package nl.han.ica.yaeger.engine.entities.entity.sprites.delegates;

import javafx.geometry.BoundingBox;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import nl.han.ica.yaeger.engine.entities.entity.SceneBoundaryCrosser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SceneBoundaryCrossingDelegateTest {

    public static final double SCENE_WIDTH = 40d;
    public static final double SCENE_HEIGHT = 40d;
    private SceneBoundaryCrossingDelegate sceneBoundaryCrossingDelegate;
    private SceneBoundaryCrosser sceneBoundaryCrosser;
    private ImageView imageView;

    @BeforeEach
    void setup() {
        sceneBoundaryCrosser = Mockito.mock(SceneBoundaryCrosser.class);
        sceneBoundaryCrossingDelegate = new SceneBoundaryCrossingDelegate(sceneBoundaryCrosser);

        imageView = Mockito.mock(ImageView.class);
        var bounds = new BoundingBox(0, 0, 2, 2);
        var scene = Mockito.mock(Scene.class);

        Mockito.when(imageView.getLayoutBounds()).thenReturn(bounds);
        Mockito.when(imageView.getScene()).thenReturn(scene);
        Mockito.when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        Mockito.when(scene.getHeight()).thenReturn(SCENE_HEIGHT);
    }

    @Test
    void centeredObjectCrossesNoBoundary() {
        // Setup
        Mockito.when(imageView.getX()).thenReturn(20d);
        Mockito.when(imageView.getY()).thenReturn(20d);

        // Test
        sceneBoundaryCrossingDelegate.checkSceneBoundary(imageView);

        // Verify
        Mockito.verifyNoMoreInteractions(sceneBoundaryCrosser);
    }
}
