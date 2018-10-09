package nl.han.ica.waterworld.scenes.levels;

import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.waterworld.entities.game.AnimatedSwordFish;
import nl.han.ica.yaeger.engine.entities.entity.Position;

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

        var animatedSwordFish = new AnimatedSwordFish(new Position(400, 200));
        addEntity(animatedSwordFish);

        super.setupEntities();
    }
}
