package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.ScrollableDynamicScene;
import com.github.hanyaeger.core.annotations.OnActivation;
import com.github.hanyaeger.core.entities.DragRepositoryAccessor;
import com.github.hanyaeger.core.entities.GameNode;
import javafx.scene.input.MouseEvent;

/**
 * A {@link MouseDropListener} enables the {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}
 * to be notified when within the area defined by its {@link javafx.geometry.BoundingBox} something that
 * is being dragged is dropped.
 * <p>
 * If this {@code MouseDropListener} is implemented by a {@link com.github.hanyaeger.api.scenes.ScrollableDynamicScene},
 * the {@link Coordinate2D} that is passed to the event handler is relative to the full scene.
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
        if (this instanceof ScrollableDynamicScene scrollableDynamicScene) {
            scrollableDynamicScene.getRootPane().setOnMouseDragReleased(this::handleDrop);
        } else {
            getNode().ifPresent(node ->
                    node.setOnMouseDragReleased(this::handleDrop)
            );
        }
    }

    private void handleDrop(final MouseEvent event) {
        onDrop(new Coordinate2D(event.getX(), event.getY()), getDragNDropRepository().getDraggedObject());
        getDragNDropRepository().clear();
        event.consume();
    }
}
