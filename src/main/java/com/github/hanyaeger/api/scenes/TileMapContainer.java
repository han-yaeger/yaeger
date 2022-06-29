package com.github.hanyaeger.api.scenes;

import com.github.hanyaeger.core.DependencyInjector;
import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.core.annotations.OnActivation;
import com.github.hanyaeger.core.exceptions.YaegerEngineException;
import com.github.hanyaeger.core.scenes.DimensionsProvider;
import com.github.hanyaeger.core.scenes.SupplierProvider;
import com.github.hanyaeger.core.scenes.TileMapListProvider;

/**
 * Implementing this interface exposes the {@link #addTileMap(TileMap)} method. An {@link TileMap} that is
 * instantiated, but not registered, will not be added to the {@link YaegerScene}.
 */
public interface TileMapContainer extends SupplierProvider, TileMapListProvider, DimensionsProvider, DependencyInjector {

    /**
     * Only instances of {@link Timer} that are registered with the method {@link #addTileMap(TileMap)}
     * within this method are registered and will receive an animation update.
     */
    void setupTileMaps();

    /**
     * Initialize all instances of {@link TileMap} that were added to this {@link TileMapContainer}.
     */
    @OnActivation
    default void initTileMaps() {
        getTileMaps().clear();
        setupTileMaps();
        configureTileMaps();
        transferEntitiesFromTileMapsToScenesEntitySupplier();
    }

    /**
     * Register an {@link TileMap}.
     *
     * @param tileMap The {@link TileMap} that should be registered.
     */
    default void addTileMap(final TileMap tileMap) {
        if (getTileMaps() != null) {
            tileMap.setDimensionsProvider(this);
            getTileMaps().add(tileMap);
        } else {
            throw new YaegerEngineException("Can not register the EntityMap. Calling this method is only allowed from the method registerEntityMaps().");
        }
    }

    private void transferEntitiesFromTileMapsToScenesEntitySupplier() {
        getTileMaps().forEach(
                entityMap -> entityMap.get().forEach(
                        entity -> getEntitySupplier().add(entity)));
    }

    private void configureTileMaps() {
        getTileMaps().forEach(tileMap -> {
            getInjector().injectMembers(tileMap);
            tileMap.activate();
        });
    }
}
