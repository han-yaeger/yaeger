package nl.meron.waterworld.scenes.levels;

import nl.meron.waterworld.Waterworld;
import nl.meron.waterworld.entities.game.AnimatedShark;
import nl.meron.yaeger.engine.entities.entity.Position;

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

        var animatedSwordFish = new AnimatedShark(new Position(400, 400));
        addEntity(animatedSwordFish);
    }
}
