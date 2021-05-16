package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.entities.YaegerEntity;

/**
 * A wrapper class that allows entities to be configured using a generic configuration object.
 * This configuration object can be passed to the entity's constructor for custom configuration.
 *
 * @param <C> the type of the configuration object
 */
public class EntityConfiguration<C> {

    private final Class<? extends YaegerEntity> entityClass;
    private final C configuration;

    public EntityConfiguration(final Class<? extends YaegerEntity> entityClass) {
        this(entityClass, null);
    }

    public EntityConfiguration(final Class<? extends YaegerEntity> entityClass, final C configuration) {
        this.entityClass = entityClass;
        this.configuration = configuration;
    }

    public Class<? extends YaegerEntity> getEntityClass() {
        return entityClass;
    }

    public C getConfiguration() {
        return configuration;
    }
}
