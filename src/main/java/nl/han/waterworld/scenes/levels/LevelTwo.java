package nl.han.waterworld.scenes.levels;

import nl.han.waterworld.Waterworld;
import nl.han.waterworld.entities.game.AnimatedShark;
import nl.han.yaeger.engine.entities.entity.Location;

public class LevelTwo extends Level {

    private static final String BACKGROUND_IMAGE = "waterworld/underwater3.jpg";

    public LevelTwo(Waterworld waterworld) {
        super(waterworld);
    }

    @Override
    public void setupScene() {
        super.setupScene();
        setBackgroundImage(BACKGROUND_IMAGE);
    }

    @Override
    public void setupEntities() {
        super.setupEntities();

        var animatedSwordFish = new AnimatedShark(new Location(400, 400));
        addEntity(animatedSwordFish);
    }
}
