package nl.meron.waterworld.entities.game.spawners;

import nl.meron.waterworld.entities.game.Air;
import nl.meron.waterworld.entities.game.Poison;
import nl.meron.waterworld.scenes.levels.Level;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.EntitySpawner;

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

    private Point generateRandomPosition() {
        int x = new Random().nextInt((int) Math.round(worldWidth));
        int y = (int) Math.round(worldHeight) - 30;

        return new Point(x, y);
    }
}
