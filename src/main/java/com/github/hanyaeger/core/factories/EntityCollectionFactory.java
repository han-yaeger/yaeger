package com.github.hanyaeger.core.factories;

import com.github.hanyaeger.core.YaegerConfig;
import com.github.hanyaeger.core.entities.EntityCollection;
import com.github.hanyaeger.api.entities.YaegerEntity;
import javafx.scene.layout.Pane;

/**
 * A {@link EntityCollectionFactory} should be used to create instances of {@link EntityCollection}.
 */
public class EntityCollectionFactory {

    /**
     * Create a {@link EntityCollection}.
     *
     * @param pane   the {@link Pane} to which all instances of {@link YaegerEntity}s should be added
     * @param config the {@link YaegerConfig} that should be used by the created {@link EntityCollection}
     * @return an instance of {@link EntityCollection}
     */
    public EntityCollection create(final Pane pane, final YaegerConfig config) {
        return new EntityCollection(pane, config);
    }
}
