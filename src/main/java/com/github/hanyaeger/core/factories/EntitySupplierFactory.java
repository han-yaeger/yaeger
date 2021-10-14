package com.github.hanyaeger.core.factories;

import com.github.hanyaeger.core.entities.EntitySupplier;
import javafx.scene.layout.Pane;

/**
 * An {@code EntitySupplierFactory} is responsible for creating instances of {@link com.github.hanyaeger.core.entities.EntitySupplier}.
 */
public class EntitySupplierFactory {

    /**
     * Create an instance of {@link EntitySupplier} with the given {@link Pane}.
     *
     * @param pane The {@link Pane} that should be used for all instances of {@link com.github.hanyaeger.api.entities.YaegerEntity}
     *             that are provided by this {@code EntitySupplier}.
     * @return An instance of {@link EntitySupplier}
     */
    public EntitySupplier create(final Pane pane) {
        var supplier = new EntitySupplier();
        supplier.setPane(pane);

        return supplier;
    }
}
