package nl.han.ica.waterworld.entities.game.spawners;

import nl.han.ica.waterworld.entities.game.Air;
import nl.han.ica.waterworld.entities.game.Poison;
import nl.han.ica.waterworld.scenes.levels.Level;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.entities.spawners.EntitySpawner;

import java.util.Random;

public class BubbleSpawner extends EntitySpawner {

    private final int worldWidth;
    private final int worldHeight;
    private Level waterworld;

    public BubbleSpawner(int width, int height, Level waterworld) {
        super(10);

        this.worldWidth = width;
        this.worldHeight = height;
        this.waterworld = waterworld;
    }

    private void createAir() {
        var air = new Air(new Position(generateRandomXLocation(), worldHeight - 30), generateRandomSpeed(), waterworld);

        spawn(air);
    }

    private void createPoison() {
        var poison = new Poison(new Position(generateRandomXLocation(), worldHeight - 30), generateRandomSpeed(), waterworld);

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
