package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.scenes.DimensionsProvider;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Implementing this interface exposes the {@link Bounded#getBoundingBox()} and  method, which returns the bounds, aka
 * Bounding Box, of this Entity.
 */
public interface Bounded extends DimensionsProvider, GameNode {

    /**
     * Return the {@link Bounds} (Bounding Box) within the {@link com.github.hanyaeger.api.engine.scenes.YaegerScene} after
     * all transformations have been applied. This method treats each {@link javafx.scene.Node} as if it was part of the
     * {@link javafx.scene.Scene}. In the case of a {@link CompositeEntity} the {@link javafx.scene.Node} will be part of a
     * {@link javafx.scene.Group}, meaning we get the {@link Bounds} within that {@link javafx.scene.Group} and not the
     * {@link javafx.scene.Scene}.
     *
     * @return the {@link Bounds}
     */
    default Bounds getBoundingBox() {
        if (getNode().isPresent()) {
            return getNode().get().localToScene(getNode().get().getBoundsInLocal(), true);
        } else {
            return new BoundingBox(0, 0, 0, 0);
        }
    }

    private Bounds getBounds() {
        if (getNode().isPresent()) {
            return getNode().get().getBoundsInParent();
        } else {
            return new BoundingBox(0, 0, 0, 0);
        }
    }

    @Override
    default double getWidth() {
        return getBounds().getWidth();
    }

    @Override
    default double getHeight() {
        return getBounds().getHeight();
    }
}
