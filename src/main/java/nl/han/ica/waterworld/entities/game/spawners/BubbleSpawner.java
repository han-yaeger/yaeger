package nl.han.ica.waterworld.entities.game.spawners;

import nl.han.ica.waterworld.entities.game.Air;
import nl.han.ica.waterworld.entities.game.Poison;
import nl.han.ica.waterworld.scenes.levels.Level;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.entities.EntitySpawner;

import java.util.Random;

public class BubbleSpawner extends EntitySpawner {

    private final double worldWidth;
    private final double worldHeight;
    private Level waterworld;

    public BubbleSpawner(double width, double height, Level waterworld) {
        super(1);

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

    @Override
    public void tick() {
        if (new Random().nextInt(10) < 2) {
            createPoison();
        } else {
            createAir();
        }
    }

    private int generateRandomSpeed() {
        return new Random().nextInt(5);
    }

    private Position generateRandomPosition() {
        int x = new Random().nextInt((int) Math.round(worldWidth));
        int y = (int) Math.round(worldHeight) - 30;
        return new Position(x, y);
    }
}
