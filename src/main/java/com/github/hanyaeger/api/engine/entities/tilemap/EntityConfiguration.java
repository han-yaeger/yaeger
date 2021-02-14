package com.github.hanyaeger.api.engine.entities.tilemap;

import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;

/**
 * A wrapper class that allows entities to be configured using a generic configuration object.
 * This configuration object can be passed to the entity's constructor for custom configuration.
 *
 * @param <C> the type of the configuration object
 */
class EntityConfiguration<C> {

    private final Class<? extends YaegerEntity> entityClass;
    private final C configuration;

    EntityConfiguration(final Class<? extends YaegerEntity> entityClass) {
        this(entityClass, null);
    }

    EntityConfiguration(final Class<? extends YaegerEntity> entityClass, final C configuration) {
        this.entityClass = entityClass;
        this.configuration = configuration;
    }

    Class<? extends YaegerEntity> getEntityClass() {
        return entityClass;
    }

    C getConfiguration() {
        return configuration;
    }
}
