package nl.meron.yaeger.engine.entities;

import nl.meron.yaeger.engine.Clearable;
import nl.meron.yaeger.engine.entities.entity.Entity;

import java.util.HashSet;
import java.util.Set;

/**
 * An {@link EntitySupplier} extends a {@link HashSet} of instances of {@link Entity}. When calling
 * {@link EntitySupplier#get()} the content will passed as the return value, after which all content is
 * cleared.
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
            var allEntities = new HashSet<>(this);
            clear();
            return allEntities;
        }
    }
}
