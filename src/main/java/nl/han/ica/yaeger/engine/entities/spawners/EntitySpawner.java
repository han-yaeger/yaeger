package nl.han.ica.yaeger.engine.entities.spawners;

import javafx.animation.AnimationTimer;
import nl.han.ica.yaeger.engine.Destructable;
import nl.han.ica.yaeger.engine.entities.entity.Entity;

import java.util.HashSet;
import java.util.Set;

/**
 * An {@code EntitiySpawner} is the abstract superclass that should be extended to create an object that
 * spawns a subclass of {@link Entity}.
 */
public abstract class EntitySpawner implements Destructable {

    private Set<Entity> spawnedEntities = new HashSet<>();
    private AnimationTimer timer;

    private long interval;

    /**
     * Create a new {@code EntitySpawner}.
     *
     * @param interval the interval at which instances of {@link Entity} should ne spawned, in milli-seconds
     */
    public EntitySpawner(long interval) {
        this.interval = interval * 1000000;
        initTimer();
    }

    /**
     * Spawn an {@link Entity}.
     *
     * @param entity the {@link Entity} to be spawned
     */
    protected void spawn(Entity entity) {
        spawnedEntities.add(entity);
    }

    /**
     * Return the {@link Set} of spawned {@code Entities}. After this method is called, the {@link Set} is cleared.
     *
     * @return a {@link Set} containing instances of {@link Entity d}
     */
    public Set<Entity> getSpawnedEntities() {
        if (spawnedEntities.isEmpty()) {
            return new HashSet<>();
        } else {
            Set<Entity> entities = new HashSet<>(spawnedEntities);
            spawnedEntities.clear();
            return entities;
        }
    }

    /**
     * Called by the {@code EntitySpawner} every time a new {@link Entity} should be spawned.
     */
    public abstract void tick();

    @Override
    public void destroy() {
        timer.stop();
    }

    private void initTimer() {
        timer = new AnimationTimer() {

            private long lastUpdate = System.currentTimeMillis();

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= interval) {

                    tick();
                    lastUpdate = now;
                }
            }
        };

        timer.start();
    }
}
