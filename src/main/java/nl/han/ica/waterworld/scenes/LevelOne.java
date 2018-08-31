package nl.han.ica.waterworld.scenes;

import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.waterworld.entities.spel.Player;
import nl.han.ica.waterworld.entities.spel.Swordfish;
import nl.han.ica.waterworld.entities.spel.spawners.BubbleSpawner;
import nl.han.ica.yaeger.scene.SceneType;

public class LevelOne extends Level {


    public LevelOne(final Waterworld waterworld) {
        super(waterworld);
    }

    @Override
    public void setupScene() {

        super.setupScene();

        setBackgroundImage("underwater2.jpg");
        setBackgroundAudio("audio/waterworld.mp3");
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

        var swordFish = new Swordfish(200, 200);
        addEntity(swordFish);

        var player = new Player(100, 100, this, 10);
        addEntity(player);
    }
}
