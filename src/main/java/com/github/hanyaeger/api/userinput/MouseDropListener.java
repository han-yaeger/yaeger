package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.annotations.OnActivation;
import com.github.hanyaeger.core.entities.DragRepositoryAccessor;
import com.github.hanyaeger.core.entities.GameNode;

/**
 * Being a {@link MouseDropListener} enables the {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}
 * to be notified when within the area defined by its {@link javafx.geometry.BoundingBox} something that
 * is being dragged is dropped.
 */
public interface MouseDropListener extends GameNode, DragRepositoryAccessor {

    /**
     * Called when within the area defined by the {@link javafx.geometry.BoundingBox} of this {@link YaegerEntity} or
     * {@link com.github.hanyaeger.api.scenes.YaegerScene} something that is being dragged is dropped.
     *
     * @param coordinate2D the current coordinate of the mouse pointer
     * @param source       the source that has been dragged
     */
    void onDrop(final Coordinate2D coordinate2D, final MouseDraggedListener source);

    /**
     * Attach a {@link MouseDropListener} to this {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}.
     */
    @OnActivation
    default void attachDropListener() {
        getNode().ifPresent(node ->
                node.setOnMouseDragReleased(event -> {
                    onDrop(new Coordinate2D(event.getX(), event.getY()), getDragNDropRepository().getDraggedObject());
                    getDragNDropRepository().clear();
                    event.consume();
                })
        );
    }
}
