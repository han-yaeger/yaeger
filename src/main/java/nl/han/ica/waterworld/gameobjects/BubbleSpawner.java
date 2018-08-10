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

    private void createAir() {
        var air = new Air(generateRandomXLocation(), worldHeight - 30, waterworld);
        spawn(air);
    }

    private void createPoison() {
        var poison = new Poison(generateRandomXLocation(), worldHeight - 30, waterworld);
        spawn(poison);
    }

    @Override
    public void tick() {
        if (new Random().nextInt(10) < 2) {
            createPoison();
        } else {
            createAir();
        }
    }

    private int generateRandomXLocation() {
        return new Random().nextInt(worldWidth);
    }
}
