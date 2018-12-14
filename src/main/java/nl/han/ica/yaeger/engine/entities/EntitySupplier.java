package nl.han.ica.yaeger.engine.entities;

import nl.han.ica.yaeger.engine.Clearable;
import nl.han.ica.yaeger.engine.entities.entity.Entity;

import java.util.HashSet;
import java.util.Set;

/**
 * An {@code EntitySupplier} encapsulates a {@link HashSet} of instances of {@link Entity}.s
 */
public class EntitySupplier extends HashSet<Entity> implements Clearable {

    /**
     * Return a {@link Set} of instances of {@link Entity}. After this method is called,
     * the {@link EntitySupplier} is cleared.
     *
     * @return a {@link Set} containing instances of {@link Entity}
     */
    public Set<Entity> get() {
        if (isEmpty()) {
            return new HashSet<>();
        } else {
            Set<Entity> allEntities = new HashSet<>(this);
            clear();
            return allEntities;
        }
    }
}
