package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.annotations.OnActivation;
import com.github.hanyaeger.core.entities.GameNode;
import javafx.scene.input.MouseEvent;

/**
 * Being a {@link MouseDraggedListener} enables the {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}
 * to be notified when a mouse button is pressed on this the area defined by the {@link javafx.geometry.BoundingBox} of an
 * {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}, and then dragged.
 */
public interface MouseDraggedListener extends GameNode {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse exited event.
     *
     * @param coordinate2D the current coordinate of the mouse pointer
     */
    void onMouseDragged(final Coordinate2D coordinate2D);

    /**
     * Attach a {@link MouseDraggedListener} to this {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}.
     */
    @OnActivation
    default void attachMouseDraggedListener() {
        getNode().ifPresent(node -> node.setOnMouseDragged(event -> onMouseDragged(new Coordinate2D(event.getX(), event.getY()))));
        ;
    }
}
