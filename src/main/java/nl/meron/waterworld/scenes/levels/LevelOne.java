package nl.meron.waterworld.scenes.levels;

import nl.meron.waterworld.Waterworld;
import nl.meron.yaeger.engine.scenes.SceneType;
import nl.meron.waterworld.Waterworld;

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
