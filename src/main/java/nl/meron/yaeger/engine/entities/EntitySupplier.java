package nl.meron.yaeger.engine.entities;

import nl.meron.yaeger.engine.Clearable;
import nl.meron.yaeger.engine.entities.entity.YaegerEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * An {@link EntitySupplier} extends a {@link HashSet} of instances of {@link YaegerEntity}. When calling
 * {@link EntitySupplier#get()} the content will passed as the return value, after which all content is
 * cleared.
 */
public class EntitySupplier extends HashSet<YaegerEntity> implements Clearable {

    /**
     * Return a {@link Set} of instances of {@link YaegerEntity}. After this method is called,
     * the {@link EntitySupplier} is cleared.
     *
     * @return a {@link Set} containing instances of {@link YaegerEntity}
     */
    public Set<YaegerEntity> get() {
        if (isEmpty()) {
            return new HashSet<>();
        } else {
            var allEntities = new HashSet<>(this);
            clear();
            return allEntities;
        }
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
