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

    /**
     * Create a new {@code EntityConfiguration} for the given {@link Class}. This {@link Class} should extend
     * {@link YaegerEntity}.
     *
     * @param entityClass a {@link Class} that extends {@link YaegerEntity}
     */
    public EntityConfiguration(final Class<? extends YaegerEntity> entityClass) {
        this(entityClass, null);
    }

    /**
     * Create a new {@code EntityConfiguration} for the given {@link Class} and configuration of type {@link C}.
     * This {@link Class} should extend{@link YaegerEntity}.
     *
     * @param entityClass   a {@link Class} that extends {@link YaegerEntity}
     * @param configuration the configuration that should be used when creating the {@link YaegerEntity}
     */
    public EntityConfiguration(final Class<? extends YaegerEntity> entityClass, final C configuration) {
        this.entityClass = entityClass;
        this.configuration = configuration;
    }

    /**
     * Return the {@link Class} of the {@link YaegerEntity}.
     *
     * @return a {@link Class} that extends {@link YaegerEntity}
     */
    public Class<? extends YaegerEntity> getEntityClass() {
        return entityClass;
    }

    /**
     * Return the configuration (of type {@link C}) that should be applied to the {@link Class}.
     *
     * @return the configuration class of type {@link C}
     */
    public C getConfiguration() {
        return configuration;
    }
}
