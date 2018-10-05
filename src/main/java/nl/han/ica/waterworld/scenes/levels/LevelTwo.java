package nl.han.ica.waterworld.scenes.levels;

import nl.han.ica.waterworld.Waterworld;

public class LevelTwo extends Level {

    private static final String BACKGROUND_IMAGE = "underwater3.jpg";

    public LevelTwo(Waterworld waterworld) {
        super(waterworld);
    }

    @Override
    public void initializeScene() {
        super.initializeScene();
        setBackgroundImage(BACKGROUND_IMAGE);
    }

    @Override
    public void setupEntities() {
        super.setupEntities();
    }
}
