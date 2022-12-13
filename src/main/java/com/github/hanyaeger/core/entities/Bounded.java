package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.CompositeEntity;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.core.scenes.DimensionsProvider;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Implementing this interface exposes the {@link Bounded#getBoundingBox()} and  method, which returns the bounds, aka
 * Bounding Box, of this Entity.
 */
public interface Bounded extends DimensionsProvider, GameNode {

    /**
     * Return the {@link Bounds} (Bounding Box) within the {@link YaegerScene} after
     * all transformations have been applied. This method treats each {@link javafx.scene.Node} as if it was part of the
     * {@link javafx.scene.Scene}. In the case of a {@link CompositeEntity} the {@link javafx.scene.Node} will be part of a
     * {@link javafx.scene.Group}, meaning we get the {@link Bounds} within that {@link javafx.scene.Group} and not the
     * {@link javafx.scene.Scene}.
     *
     * @return the {@link Bounds}
     */
    default Bounds getBoundingBox() {

        return getNode().map(node -> {
            var newLocation = calculateLocation(node);
            return new BoundingBox(newLocation.getX(),
                    newLocation.getY(),
                    node.getBoundsInParent().getWidth(),
                    node.getBoundsInParent().getHeight());
        }).orElse(new BoundingBox(0, 0, 0, 0));
    }

    private Bounds getBounds() {
        return getNode().map(Node::getBoundsInParent).orElse(new BoundingBox(0, 0, 0, 0));
    }

    @Override
    default double getWidth() {
        return getBounds().getWidth();
    }

    @Override
    default double getHeight() {
        return getBounds().getHeight();
    }

    /**
     * Since a {@link Node} can be either part of the {@link Pane}, or of a {@link javafx.scene.Group}, which itself
     * can also be part of the {@link Pane} or another {@link javafx.scene.Group}, which itself can be... , this
     * method recursively calculates ({@link #translateCoordinates(Node, Coordinate2D)} does the actual recursive work)
     * the coordinate of the {@link} projected on the {@link Pane}. This way it is
     * possible to use the {@link Node#intersects(Bounds)} method to perform collision detection.
     *
     * @param node that {@link Node} for which the coordinates need to be calculated
     * @return the {@link Coordinate2D} of the node, as projected on the {@link Pane}
     */
    private Coordinate2D calculateLocation(final Node node) {
        if (node == null) {
            return new Coordinate2D(10, 10);
        }

        return translateCoordinates(node, new Coordinate2D());
    }

    private Coordinate2D translateCoordinates(final Node node, final Coordinate2D translatedCoordinate) {
        if (node == null || node instanceof Pane) {
            return translatedCoordinate;
        } else {
            return translateCoordinates(node.getParent(), translatedCoordinate.add(new Coordinate2D(node.getBoundsInParent().getMinX(), node.getBoundsInParent().getMinY())));
        }
    }
}
