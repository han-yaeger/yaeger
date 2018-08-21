package nl.han.ica.yaeger.entities.spawners;

import javafx.animation.AnimationTimer;
import nl.han.ica.yaeger.entities.Entity;

import java.util.HashSet;
import java.util.Set;

public abstract class EntitySpawner {

    private Set<Entity> spawnedEntities = new HashSet<>();

    private long interval;

    /**
     * Create a new EntitySpawner.
     *
     * @param interval The interval in milli seconds
     */
    public EntitySpawner(long interval) {
        this.interval = interval * 1000000;
        initTimer();
    }

    protected void spawn(Entity entity) {
        spawnedEntities.add(entity);
    }

    public Set<Entity> getSpawnedEntities() {
        if (spawnedEntities.isEmpty()) {
            return new HashSet<>();
        } else {
            Set<Entity> entities = new HashSet<>(spawnedEntities);
            spawnedEntities.clear();
            return entities;
        }
    }

    private void initTimer() {

        AnimationTimer animator = new AnimationTimer() {

            private long lastUpdate = System.currentTimeMillis();

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= interval) {

                    tick();
                    lastUpdate = now;
                }
            }
        };

        animator.start();
    }

    public abstract void tick();

}
