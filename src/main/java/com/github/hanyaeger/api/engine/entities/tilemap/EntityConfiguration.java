package com.github.hanyaeger.api.engine.entities.tilemap;

import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;

/**
 * A wrapper class that allows entities to be configured using a generic configuration object.
 *  * This configuration object can be passed to the entity's constructor for custom configuration.
 * @param <C> the type of the configuration object
 */
class EntityConfiguration<C> {

    private Class<? extends YaegerEntity> entityClass;
    private C configuration;

    EntityConfiguration(Class<? extends YaegerEntity> entityClass) {
        this.entityClass = entityClass;
    }

    EntityConfiguration(Class<? extends YaegerEntity> entityClass, C configuration) {
        this(entityClass);
        this.configuration = configuration;
    }

    Class<? extends YaegerEntity> getEntityClass() {
        return entityClass;
    }

    void setEntityClass(Class<? extends YaegerEntity> entityClass) {
        this.entityClass = entityClass;
    }

    C getConfiguration() {
        return configuration;
    }

    void setConfiguration(C configuration) {
        this.configuration = configuration;
    }
}
