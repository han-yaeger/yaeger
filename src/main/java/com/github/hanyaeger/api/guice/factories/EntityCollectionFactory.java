package com.github.hanyaeger.api.guice.factories;

import javafx.scene.Group;
import com.github.hanyaeger.api.engine.entities.EntityCollection;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;

/**
 * A {@link EntityCollectionFactory} can be used to create instances of {@link EntityCollection}.
 */
public class EntityCollectionFactory {

    /**
     * Create a {@link EntityCollection}.
     *
     * @param group The {@link Group} to which all instances of {@link YaegerEntity}s should be added.
     * @return An instance of {@link EntityCollection}
     */
    public EntityCollection create(final Group group) {
        return new EntityCollection(group);
    }
}
