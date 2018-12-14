package nl.han.ica.yaeger.engine.entities;

import javafx.animation.AnimationTimer;
import nl.han.ica.yaeger.engine.Destroyable;
import nl.han.ica.yaeger.engine.entities.entity.Entity;

/**
 * An {@code EntitiySpawner} is the abstract superclass that should be extended to create an object that
 * spawns a subclass of {@link Entity}.
 */
public abstract class EntitySpawner extends EntitySupplier implements Destroyable {

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
        add(entity);
    }

    /**
     * Called by the {@code EntitySpawner} every time a new {@link Entity} should be spawned.
     */
    public abstract void tick();

    @Override
    public void destroy() {

        clear();

        if (timer != null) {
            timer.stop();
        }
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
