package com.github.hanyaeger.api.engine.entities;

import com.github.hanyaeger.api.engine.Timer;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;
import com.google.inject.Inject;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;

/**
 * An {@link EntitySpawner} is the abstract superclass that should be extended to create an object that
 * spawns a subclass of {@link YaegerEntity}. After each {@code interval in ms}, set through the constructor, the method
 * {@link #spawnEntities()} is called, which should be implemented in a subclass.
 */
public abstract class EntitySpawner extends Timer {

    private EntitySupplier supplier;

    /**
     * Create a new instance of {@link EntitySpawner} for the given interval in milliseconds.
     *
     * @param intervalInMs The interval in milleseconds.
     */
    protected EntitySpawner(final long intervalInMs) {
        super(intervalInMs);
    }

    @Override
    public void handle(final long now) {
        super.handle(now);
    }

    @Override
    public void onAnimationUpdate(final long timestamp) {
        spawnEntities();
    }

    /**
     * This method is called after each interval in milleseconds as passed through the constructor of this
     * {@link EntitySpawner}. It's body should be used for calling {@link #spawn(YaegerEntity)} with the required instance
     * of {@link YaegerEntity}.
     */
    protected abstract void spawnEntities();

    /**
     * Spawn an {@link YaegerEntity}. Use this method to add the spawned {@link YaegerEntity} to the
     * {@link YaegerScene}.
     *
     * @param entity The {@link YaegerEntity} to be spawned.
     */
    protected void spawn(final YaegerEntity entity) {
        supplier.add(entity);
    }

    /**
     * Return the {@link EntitySupplier} that is part of this {@link EntitySpawner}.
     *
     * @return The {@link EntitySupplier} that is part of this {@link EntitySpawner}.
     */
    public EntitySupplier getSupplier() {
        return supplier;
    }

    @Inject
    public void setSupplier(final EntitySupplier supplier) {
        this.supplier = supplier;
    }
}
