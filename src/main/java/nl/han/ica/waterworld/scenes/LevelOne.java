package nl.han.ica.waterworld.scenes;

import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.waterworld.entities.game.Swordfish;
import nl.han.ica.waterworld.entities.game.spawners.BubbleSpawner;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.scene.SceneType;

public class LevelOne extends Level {


    private static final String BACKGROUND_IMAGE = "underwater2.jpg";

    public LevelOne(final Waterworld waterworld) {
        super(waterworld);
    }

    @Override
    public void initializeScene() {
        super.initializeScene();
        setBackgroundImage(BACKGROUND_IMAGE);
    }

    @Override
    protected void setupSpawners() {
        var spawner = new BubbleSpawner(waterworld.getGameWidth(), waterworld.getGameHeight(), this);
        registerSpawner(spawner);
    }

    @Override
    public void increaseBubblesPopped() {
        super.increaseBubblesPopped();

        if (bubblesPopped > 9) {
            waterworld.nextScene(SceneType.LEVEL_TWO);
        }
    }

    @Override
    protected void setupEntities() {
        super.setupEntities();

        var swordFish = new Swordfish(new Position(200, 200));
        addEntity(swordFish);
    }
}
