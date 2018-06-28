package nl.han.ica.waterworld.gameobjects;

import nl.han.ica.yaeger.gameobjects.spawners.ObjectSpawer;

import java.util.Random;

public class BubbleSpawner extends ObjectSpawer {

    private final int worldWidth;
    private final int worldHeight;

    public BubbleSpawner(int width, int height) {
        super(100);

        this.worldWidth = width;
        this.worldHeight = height;
    }

    public void createBubble() {
        var random = new Random().nextInt(worldWidth);
        var bubble = new Bubble(random, worldHeight - 30);

        spawn(bubble);
    }

    @Override
    public void tick() {
        createBubble();
    }
}
