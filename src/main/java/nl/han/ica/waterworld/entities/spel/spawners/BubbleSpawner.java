package nl.han.ica.waterworld.entities.spel.spawners;

import nl.han.ica.waterworld.entities.spel.Air;
import nl.han.ica.waterworld.entities.spel.Poison;
import nl.han.ica.waterworld.scenes.Level;
import nl.han.ica.yaeger.entities.spawners.EntitySpawner;

import java.util.Random;

public class BubbleSpawner extends EntitySpawner {

    private final int worldWidth;
    private final int worldHeight;
    private Level waterworld;

    public BubbleSpawner(int width, int height, Level waterworld) {
        super(100);

        this.worldWidth = width;
        this.worldHeight = height;
        this.waterworld = waterworld;
    }

    private void createAir() {
        var air = new Air(generateRandomXLocation(), worldHeight - 30, generateRandomSpeed(), waterworld);
        spawn(air);
    }

    private void createPoison() {
        var poison = new Poison(generateRandomXLocation(), worldHeight - 30, generateRandomSpeed(), waterworld);
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

    private int generateRandomSpeed() {
        return new Random().nextInt(5);
    }
}
