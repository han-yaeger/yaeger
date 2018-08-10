package nl.han.ica.waterworld.gameobjects;

import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.yaeger.gameobjects.spawners.ObjectSpawner;

import java.util.Random;

public class BubbleSpawner extends ObjectSpawner {

    private final int worldWidth;
    private final int worldHeight;
    private Waterworld waterworld;

    public BubbleSpawner(int width, int height, Waterworld waterworld) {
        super(100);

        this.worldWidth = width;
        this.worldHeight = height;
        this.waterworld = waterworld;
    }

    public void createBubble() {
        var random = new Random().nextInt(worldWidth);
        var bubble = new Bubble(random, worldHeight - 30, waterworld);

        spawn(bubble);
    }

    @Override
    public void tick() {
        createBubble();
    }
}
