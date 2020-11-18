package com.github.hanyaeger.api.guice.factories;

import com.github.hanyaeger.api.engine.entities.EntityCollection;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import javafx.scene.layout.Pane;

/**
 * A {@link EntityCollectionFactory} should be used to create instances of {@link EntityCollection}.
 */
public class EntityCollectionFactory {

    /**
     * Create a {@link EntityCollection}.
     *
     * @param pane the {@link Pane} to which all instances of {@link YaegerEntity}s should be added
     * @return an instance of {@link EntityCollection}
     */
    public EntityCollection create(final Pane pane) {
        return new EntityCollection(pane);
    }
}
