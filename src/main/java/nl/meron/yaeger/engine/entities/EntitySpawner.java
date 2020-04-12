package nl.meron.yaeger.engine.entities;

import com.google.inject.Inject;
import nl.meron.yaeger.engine.Timer;
import nl.meron.yaeger.engine.entities.entity.Entity;

/**
 * An {@link EntitySpawner} is the abstract superclass that should be extended to create an object that
 * spawns a subclass of {@link Entity}. After each {@code interval in ms}, set through the constructor, the method
 * {@link #spawnEntities()} is called, which should be implemented in a subclass.
 */
public abstract class EntitySpawner extends Timer {

    private EntitySupplier supplier;

    /**
     * Create a new instance of {@link EntitySpawner} for the given interval in milliseconds.
     *
     * @param intervalInMs The interval in milleseconds.
     */
    public EntitySpawner(long intervalInMs) {
        super(intervalInMs);
    }

    @Override
    public void handle(long now) {
        super.handle(now);
    }

    @Override
    public void onAnimationUpdate(long timestamp) {
        spawnEntities();
    }

    /**
     * This method is called after each interval in milleseconds as passed through the constructor of this
     * {@link EntitySpawner}. It's body should be used for calling {@link #spawn(Entity)} with the required instance
     * of {@link Entity}.
     */
    protected abstract void spawnEntities();

    /**
     * Spawn an {@link Entity}. Use this method to add the spawned {@link Entity} to the
     * {@link nl.meron.yaeger.engine.scenes.YaegerScene}.
     *
     * @param entity The {@link Entity} to be spawned.
     */
    protected void spawn(final Entity entity) {
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
    public void setSupplier(EntitySupplier supplier) {
        this.supplier = supplier;
    }
}
