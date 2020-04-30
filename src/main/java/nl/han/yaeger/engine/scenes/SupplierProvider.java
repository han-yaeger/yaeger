package nl.han.yaeger.engine.scenes;

import nl.han.yaeger.engine.entities.EntitySupplier;

/**
 * Provide a {@link SupplierProvider#getEntitySupplier()} that returns an instance of {@link EntitySupplier}.
 */
public interface SupplierProvider {

    /**
     * Return the {@link EntitySupplier} that is provided by this {@link SupplierProvider#getEntitySupplier()}.
     *
     * @return The {@link EntitySupplier} that is provided by this {@link SupplierProvider#getEntitySupplier()}.
     */
    EntitySupplier getEntitySupplier();
}
