package nl.han.ica.waterworld.scenes.levels;

import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.yaeger.engine.scenes.SceneType;

public class LevelOne extends Level {

    private static final String BACKGROUND_IMAGE = "underwater2.jpg";

    public LevelOne(final Waterworld waterworld) {
        super(waterworld);
    }

    @Override
    public void setupScene() {
        super.setupScene();
        setBackgroundImage(BACKGROUND_IMAGE);
    }

    @Override
    public void increaseBubblesPopped() {
        super.increaseBubblesPopped();

        if (bubblesPopped > 2) {
            waterworld.nextScene(SceneType.LEVEL_TWO);
        }
    }
}
