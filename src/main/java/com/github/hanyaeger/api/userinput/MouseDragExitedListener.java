package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.annotations.OnActivation;
import com.github.hanyaeger.core.entities.DragRepositoryAccessor;
import com.github.hanyaeger.core.entities.GameNode;

/**
 * Being a {@link MouseDragExitedListener} enables the {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}
 * to be notified when the area defined by its {@link javafx.geometry.BoundingBox} is being exited by something that
 * is being dragged.
 */
public interface MouseDragExitedListener extends GameNode, DragRepositoryAccessor {

    /**
     * Called when the area defined by the {@link javafx.geometry.BoundingBox} of this {@link YaegerEntity} or
     * {@link com.github.hanyaeger.api.scenes.YaegerScene} is being exited by something that is being dragged.
     *
     * @param coordinate2D the current coordinate of the mouse pointer
     * @param source       the source that has been dragged
     */
    void onDragExited(final Coordinate2D coordinate2D, final MouseDraggedListener source);

    /**
     * Attach a {@link MouseDragExitedListener} to this {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}.
     */
    @OnActivation
    default void attachDragExitedListener() {
        getNode().ifPresent(node ->
                node.setOnMouseDragExited(event -> {
                    onDragExited(new Coordinate2D(event.getX(), event.getY()), getDragNDropRepository().getDraggedObject());
                    event.consume();
                })
        );
    }
}
