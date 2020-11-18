package com.github.hanyaeger.api.engine.entities;

import com.github.hanyaeger.api.engine.Clearable;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link EntitySupplier} extends a {@link ArrayList} of instances of {@link YaegerEntity}. When calling
 * {@link EntitySupplier#get()} the content will passed as the return value, after which all content is
 * cleared.
 */
public class EntitySupplier extends ArrayList<YaegerEntity> implements Clearable {

    /**
     * Return a {@link List} of instances of {@link YaegerEntity}. After this method is called,
     * the {@link EntitySupplier} is cleared.
     *
     * @return a {@link List} containing instances of {@link YaegerEntity}
     */
    public List<YaegerEntity> get() {
        if (isEmpty()) {
            return new ArrayList<>();
        } else {
            var allEntities = new ArrayList<>(this);
            clear();
            return allEntities;
        }
    }

    @Override
    public boolean equals(final Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
