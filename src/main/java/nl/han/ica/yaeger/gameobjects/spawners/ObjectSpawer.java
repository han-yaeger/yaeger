package nl.han.ica.yaeger.gameobjects.spawners;

import javafx.animation.AnimationTimer;
import nl.han.ica.yaeger.gameobjects.GameObject;

import java.util.HashSet;
import java.util.Set;

public abstract class ObjectSpawer {

    private Set<GameObject> spawnedGameObjects = new HashSet<>();

    private long interval;

    /**
     * Create a new ObjectSpawner.
     *
     * @param interval The interval in milli seconds
     */
    public ObjectSpawer(long interval) {
        this.interval = interval * 1000000;
        initTimer();
    }

    protected void spawn(GameObject gameObject) {
        spawnedGameObjects.add(gameObject);
    }

    public Set<GameObject> getSpawnedGameObjects() {
        if (spawnedGameObjects.isEmpty()) {
            return new HashSet<>();
        } else {
            Set<GameObject> gameObjects = new HashSet<>(spawnedGameObjects);
            spawnedGameObjects.clear();
            return gameObjects;
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
