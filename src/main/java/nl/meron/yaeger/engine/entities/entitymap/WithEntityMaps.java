package nl.meron.yaeger.engine.entities.entitymap;

import nl.meron.yaeger.engine.Timer;
import nl.meron.yaeger.engine.annotations.Initializer;
import nl.meron.yaeger.engine.exceptions.YaegerEngineException;
import nl.meron.yaeger.engine.scenes.DimensionsProvider;
import nl.meron.yaeger.engine.scenes.WithSupplier;
import nl.meron.yaeger.engine.scenes.YaegerScene;

/**
 * Implementing this interface exposes the {@link #registerEntityMap(EntityMap)} method. An {@link EntityMap} that is
 * instantiated, but not registered, will not be added to the {@link YaegerScene}.
 */
public interface WithEntityMaps extends WithSupplier, WithEntityMapList, DimensionsProvider {

    /**
     * Only instances of {@link Timer} that are registered with the method {@link #registerEntityMap(EntityMap)}
     * within this method are registered and will receive an animation update.
     */
    void registerEntityMaps();

    @Initializer
    default void initEntityMaps() {
        getEntityMaps().clear();
        registerEntityMaps();
        configureEntityMaps();
        transferEntitiesFromMapsToScenesEntitySupplier();
    }

    /**
     * Register an {@link EntityMap}.
     *
     * @param entityMap The {@link EntityMap} that should be registered.
     */
    default void registerEntityMap(EntityMap entityMap) {
        if (getEntityMaps() != null) {
            entityMap.setDimensionsProvider(this);
            getEntityMaps().add(entityMap);
        } else {
            throw new YaegerEngineException("Can not register the EntityMap. Calling this method is only allowed from the method registerEntityMaps().");
        }
    }

    private void transferEntitiesFromMapsToScenesEntitySupplier() {
        getEntityMaps().forEach(
                entityMap -> entityMap.get().forEach(
                        entity -> getEntitySupplier().add(entity)));
    }

    private void configureEntityMaps() {
        getEntityMaps().forEach(entityMap -> entityMap.configure());
    }
}
