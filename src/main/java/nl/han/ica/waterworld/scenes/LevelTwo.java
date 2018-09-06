package nl.han.ica.waterworld.scenes;

import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.waterworld.entities.game.Player;
import nl.han.ica.waterworld.entities.game.spawners.BubbleSpawner;

public class LevelTwo extends Level {

    public LevelTwo(Waterworld waterworld) {
        super(waterworld);
    }

    @Override
    public void initializeScene() {
        setBackgroundImage("underwater3.jpg");
        setBackgroundAudio("audio/waterworld.mp3");
    }

    @Override
    protected void setupSpawners() {
        var spawner = new BubbleSpawner(waterworld.getGameWidth(), waterworld.getGameHeight(), this);
        registerSpawner(spawner);
    }

    @Override
    public void setupEntities() {
        super.setupEntities();

        var player = new Player(100, 100, this, 10);
        addEntity(player);
    }
}
