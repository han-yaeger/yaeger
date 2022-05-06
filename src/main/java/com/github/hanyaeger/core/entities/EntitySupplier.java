package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.core.Clearable;
import com.github.hanyaeger.api.entities.YaegerEntity;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link EntitySupplier} extends a {@link ArrayList} of instances of {@link YaegerEntity}. When calling
 * {@link EntitySupplier#get()} the content will passed as the return value, after which all content is
 * cleared.
 */
public class EntitySupplier extends ArrayList<YaegerEntity> implements Clearable {

    /**
     * The {@link Pane} that should be used when adding all instances of {@link YaegerEntity} that were part
     * of this {@link EntitySupplier}.
     */
    private transient Pane pane;

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

    /**
     * Get the {@link Pane} on which each {@link javafx.scene.Node} that is part of the
     * {@link YaegerEntity}, should be added.
     *
     * @return an instance of {@link Pane}
     */
    public Pane getPane() {
        return pane;
    }

    /**
     * Set the {@link Pane} on which each {@link javafx.scene.Node} that is part of the
     * {@link YaegerEntity}, should be added.
     *
     * @param pane an instance of {@link Pane}
     */
    public void setPane(final Pane pane) {
        this.pane = pane;
    }
}
