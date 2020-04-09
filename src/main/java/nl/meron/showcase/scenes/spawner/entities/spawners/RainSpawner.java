package nl.meron.showcase.scenes.spawner.entities.spawners;

import nl.meron.showcase.scenes.spawner.entities.Raindrop;
import nl.meron.yaeger.engine.entities.EntitySpawner;
import nl.meron.yaeger.engine.entities.entity.Location;

import java.util.Random;

public class RainSpawner extends EntitySpawner {

    private final double worldWidth;

    /**
     * Create a new instance of {@link EntitySpawner} for the given interval in milliseconds.
     *
     * @param intervalInMs The interval in milleseconds.
     */
    public RainSpawner(final double worldWidth, final long intervalInMs) {
        super(intervalInMs);
        this.worldWidth = worldWidth;
    }

    @Override
    protected void spawnEntities() {
        var raindrop = new Raindrop(generateRandomPosition(), generateRandomSpeed());

        spawn(raindrop);
    }

    private int generateRandomSpeed() {
        return new Random().nextInt(4) + 1;
    }

    private Location generateRandomPosition() {
        int x = new Random().nextInt((int) Math.round(worldWidth));

        return new Location(x, 0);
    }
}
