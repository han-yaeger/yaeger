package com.github.hanyaeger.core.scenes;

import com.github.hanyaeger.core.entities.EntitySupplier;

/**
 * Provide a {@link SupplierProvider#getEntitySupplier()} that returns an instance of {@link EntitySupplier}.
 */
public interface SupplierProvider {

    /**
     * Return the {@link EntitySupplier} that is provided by this {@link SupplierProvider}.
     *
     * @return the {@link EntitySupplier} that is provided by this {@link SupplierProvider}
     */
    EntitySupplier getEntitySupplier();
}
