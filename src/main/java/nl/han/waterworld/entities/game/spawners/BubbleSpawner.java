package nl.han.waterworld.entities.game.spawners;

import nl.han.waterworld.entities.game.Air;
import nl.han.waterworld.entities.game.Poison;
import nl.han.waterworld.scenes.levels.Level;
import nl.han.yaeger.engine.entities.EntitySpawner;
import nl.han.yaeger.engine.entities.entity.Location;

import java.util.Random;

public class BubbleSpawner extends EntitySpawner {

    private final double worldWidth;
    private final double worldHeight;
    private Level waterworld;

    public BubbleSpawner(double width, double height, Level waterworld) {
        super(100);

        this.worldWidth = width;
        this.worldHeight = height;
        this.waterworld = waterworld;
    }

    private void createAir() {
        var air = new Air(generateRandomPosition(), generateRandomSpeed(), waterworld);

        spawn(air);
    }

    private void createPoison() {
        var poison = new Poison(generateRandomPosition(), generateRandomSpeed(), waterworld);

        spawn(poison);
    }

    private int generateRandomSpeed() {
        return new Random().nextInt(4) + 1;
    }

    private Location generateRandomPosition() {
        int x = new Random().nextInt((int) Math.round(worldWidth));
        int y = (int) Math.round(worldHeight);

        return new Location(x, y);
    }

    @Override
    protected void spawnEntities() {
        if (new Random().nextInt(10) < 2) {
            createPoison();
        } else {
            createAir();
        }
    }
}
