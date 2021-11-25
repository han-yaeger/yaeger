package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.annotations.OnActivation;
import com.github.hanyaeger.core.entities.DragRepositoryAccessor;
import com.github.hanyaeger.core.entities.GameNode;
import com.github.hanyaeger.core.exceptions.YaegerEngineException;
import javafx.scene.input.MouseButton;

/**
 * Being a {@link MouseDraggedListener} enables the {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}
 * to be notified when a mouse button is pressed on this the area defined by the {@link javafx.geometry.BoundingBox} of an
 * {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}, and then dragged.
 */
public interface MouseDraggedListener extends GameNode, DragRepositoryAccessor {

    /**
     * Called when the {@link YaegerEntity} is being dragged.
     *
     * @param coordinate2D the current coordinate of the mouse pointer
     */
    void onDragged(final Coordinate2D coordinate2D);

    /**
     * Called when the {@link YaegerEntity} is being dropped.
     *
     * @param coordinate2D the current coordinate of the mouse pointer
     */
    void onDropped(final Coordinate2D coordinate2D);

    /**
     * Attach a {@code MouseDraggedListener} to this {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}.
     */
    @OnActivation
    default void attachMouseDraggedListener() {
        if (this instanceof YaegerEntity) {
            getNode().ifPresent(node -> {
                node.setOnDragDetected(event -> {
                    node.startFullDrag();
                    getDragNDropRepository().setDraggedObject(this);
                    event.consume();

                });
                node.setOnMouseDragged(event -> {
                    onDragged(new Coordinate2D(event.getX(), event.getY()));
                    event.consume();
                });
                node.setOnMouseReleased(event -> {
                    if (MouseButton.PRIMARY.equals(event.getButton())) {
                        onDropped(new Coordinate2D(event.getX(), event.getY()));
                    }
                });
            });
        } else {
            throw new YaegerEngineException("A MouseDraggedListener can only be attached to a YaegerEntity.");
        }
    }
}
