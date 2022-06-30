package com.github.hanyaeger.core.factories;

import com.github.hanyaeger.core.YaegerConfig;
import com.github.hanyaeger.core.entities.EntityCollection;

/**
 * A {@link EntityCollectionFactory} should be used to create instances of {@link EntityCollection}.
 */
public class EntityCollectionFactory {

    /**
     * Create a {@link EntityCollection}.
     *
     * @param config the {@link YaegerConfig} that should be used by the created {@link EntityCollection}
     * @return an instance of {@link EntityCollection}
     */
    public EntityCollection create(final YaegerConfig config) {
        return new EntityCollection(config);
    }
}
