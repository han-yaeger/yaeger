package com.github.hanyaeger.api;

import com.github.hanyaeger.core.Activatable;
import com.github.hanyaeger.core.DependencyInjector;
import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.core.annotations.OnPostActivation;
import com.github.hanyaeger.core.annotations.UpdatableProvider;
import com.github.hanyaeger.core.exceptions.YaegerEngineException;
import com.github.hanyaeger.api.entities.EntitySpawner;
import com.github.hanyaeger.core.scenes.EntityCollectionSupplier;
import com.github.hanyaeger.core.scenes.EntitySpawnerListProvider;
import com.github.hanyaeger.api.scenes.YaegerScene;

/**
 * An {@code EntitySpawnerContainer} enables a {@link YaegerScene} to use instances of {@link EntitySpawner}.
 * <p>
 * When implementing this interface, the {@link #setupEntitySpawners()} method needs to be implemented The
 * body of {@link #setupEntitySpawners()} should be used to add instances of {@link EntitySpawner}, using the
 * method  {@link #addEntitySpawner(EntitySpawner)}. These spawners will then be registered and added to the GWU.
 * <p>
 * A {@link EntitySpawner} that is instantiated, but not added in this way, will not work.
 */
public interface EntitySpawnerContainer extends EntitySpawnerListProvider, EntityCollectionSupplier, DependencyInjector, Activatable {

    /**
     * Use this method to add any {@link EntitySpawner} that is required by the {@link YaegerScene}.
     *
     * @param entitySpawner the {@link EntitySpawner} that needs to be added
     */
    default void addEntitySpawner(final EntitySpawner entitySpawner) {
        if (getSpawners() != null) {
            getInjector().injectMembers(entitySpawner);
            getSpawners().add(entitySpawner);

            if (isActivationComplete()) {
                getEntityCollection().registerSupplier(entitySpawner.getSupplier());
            }
        } else {
            throw new YaegerEngineException("getSpawners() returns null, please return an instance of ArrayList<>");
        }
    }

    /**
     * Initialize all instances of {@link EntitySpawner} that were added to this {@link EntitySpawnerContainer}.
     */
    @OnPostActivation
    default void initSpawners() {
        getSpawners().clear();
        setupEntitySpawners();
        registerEntitySpawners();
    }

    /**
     * Register all instances of {@link EntitySpawner} that were added to this {@link EntitySpawnerContainer}.
     */
    default void registerEntitySpawners() {
        getSpawners().forEach(spawner -> getEntityCollection().registerSupplier(spawner.getSupplier()));
    }

    /**
     * Only instances of {@link EntitySpawner} that are registered with the method {@link #addEntitySpawner(EntitySpawner)}
     * within this method are registered and will receive an animation update.
     */
    void setupEntitySpawners();

    /**
     * Call the {@link Updatable#update(long)} of all intances of {@link EntitySpawner} that
     * were added to this {@link EntitySpawnerContainer}.
     *
     * @return an {@link Updatable} that delegates the {@link Updatable#update(long)}
     */
    @UpdatableProvider
    default Updatable callEntitySpawners() {
        return timestamp -> {
            if (getSpawners() != null && !getSpawners().isEmpty()) {
                // remove the suppliers from garbage-spawners from the entityCollection
                getSpawners().stream().filter(Timer::isGarbage).forEach(
                        entitySpawner ->
                                getEntityCollection().removeSupplier(entitySpawner.getSupplier()));
                // remove all spawners that have been marked as garbage
                getSpawners().removeIf(Timer::isGarbage);
                // call handle on all spawners
                getSpawners().forEach(
                        entitySpawner ->
                                entitySpawner.handle(timestamp));
            }
        };
    }
}
