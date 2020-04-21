package nl.han.yaeger.engine.entities.tilemap;

import nl.han.yaeger.engine.DependencyInjector;
import nl.han.yaeger.engine.Timer;
import nl.han.yaeger.engine.annotations.OnActivation;
import nl.han.yaeger.engine.exceptions.YaegerEngineException;
import nl.han.yaeger.engine.scenes.DimensionsProvider;
import nl.han.yaeger.engine.scenes.SupplierProvider;
import nl.han.yaeger.engine.scenes.YaegerScene;

/**
 * Implementing this interface exposes the {@link #addTileMap(TileMap)} method. An {@link TileMap} that is
 * instantiated, but not registered, will not be added to the {@link YaegerScene}.
 */
public interface WithTileMaps extends SupplierProvider, TileMapListProvider, DimensionsProvider, DependencyInjector {

    /**
     * Only instances of {@link Timer} that are registered with the method {@link #addTileMap(TileMap)}
     * within this method are registered and will receive an animation update.
     */
    void setupTileMaps();

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
    default void addTileMap(TileMap tileMap) {
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
        getTileMaps().forEach(entityMap -> {
            getInjector().injectMembers(entityMap);
            entityMap.activate();
        });
    }
}
