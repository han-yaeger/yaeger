package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.scenes.DimensionsProvider;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;

/**
 * Implementing this interface exposes the {@link Bounded#getBoundingBox()} and  method, which returns the bounds, aka
 * Bounding Box, of this Entity.
 */
public interface Bounded extends DimensionsProvider, GameNode {

    BoundingBox EMPTY_BB = new BoundingBox(0, 0, 0, 0);

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
        return getNode().map(node -> node.localToScene(node.getBoundsInLocal(), true)).orElse(EMPTY_BB);
    }

    private Bounds getBounds() {
        return getNode().map(Node::getBoundsInParent).orElse(EMPTY_BB);
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
