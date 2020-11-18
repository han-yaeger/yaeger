package com.github.hanyaeger.api.engine.scenes;

import com.github.hanyaeger.api.engine.entities.EntitySupplier;

/**
 * Provide a {@link SupplierProvider#getEntitySupplier()} that returns an instance of {@link EntitySupplier}.
 */
public interface SupplierProvider {

    /**
     * Return the {@link EntitySupplier} that is provided by this {@link SupplierProvider#getEntitySupplier()}.
     *
     * @return the {@link EntitySupplier} that is provided by this {@link SupplierProvider#getEntitySupplier()}
     */
    EntitySupplier getEntitySupplier();
}
