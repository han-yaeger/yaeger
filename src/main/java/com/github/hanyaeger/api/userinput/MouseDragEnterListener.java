package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.ScrollableDynamicScene;
import com.github.hanyaeger.core.annotations.OnActivation;
import com.github.hanyaeger.core.entities.DragRepositoryAccessor;
import com.github.hanyaeger.core.entities.GameNode;
import javafx.scene.input.MouseEvent;

/**
 * A {@code MouseDragEnterListener} enables the {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}
 * to be notified when the area defined by its {@link javafx.geometry.BoundingBox} is being entered by something that
 * is being dragged.
 * <p>
 * If this {@code MouseButtonPressedListener} is implemented by a {@link com.github.hanyaeger.api.scenes.ScrollableDynamicScene},
 * the {@link Coordinate2D} that is passed to the event handler is relative to the full scene.
 */
public interface MouseDragEnterListener extends GameNode, DragRepositoryAccessor {

    /**
     * Called when the area defined by the {@link javafx.geometry.BoundingBox} of this {@link YaegerEntity} or
     * {@link com.github.hanyaeger.api.scenes.YaegerScene} is being entered by something that is being dragged.
     *
     * @param coordinate2D the current coordinate of the mouse pointer
     * @param source       the source that has been dragged
     */
    void onDragEntered(final Coordinate2D coordinate2D, final MouseDraggedListener source);

    /**
     * Attach a {@code MouseDragEnterListener} to this {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}.
     */
    @OnActivation
    default void attachDragEnteredListener() {
        if (this instanceof ScrollableDynamicScene scrollableDynamicScene) {
            scrollableDynamicScene.getRootPane().setOnMouseDragEntered(this::handleDragEnter);
        } else {
            getNode().ifPresent(node -> node.setOnMouseDragEntered(this::handleDragEnter));
        }
    }

    private void handleDragEnter(final MouseEvent event) {
        onDragEntered(new Coordinate2D(event.getX(), event.getY()), getDragNDropRepository().getDraggedObject());
        event.consume();
    }
}
