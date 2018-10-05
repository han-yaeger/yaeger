package nl.han.ica.waterworld.scenes;

import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.waterworld.entities.game.Player;
import nl.han.ica.waterworld.entities.game.spawners.BubbleSpawner;
import nl.han.ica.yaeger.engine.entities.entity.Position;

public class LevelTwo extends Level {

    public static final String BACKGROUND_IMAGE = "underwater3.jpg";

    public LevelTwo(Waterworld waterworld) {
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
    public void setupEntities() {
        super.setupEntities();
    }
}
