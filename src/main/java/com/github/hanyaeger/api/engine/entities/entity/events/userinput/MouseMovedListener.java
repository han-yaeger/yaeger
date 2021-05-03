package com.github.hanyaeger.api.engine.entities.entity.events.userinput;

import com.github.hanyaeger.api.engine.annotations.OnActivation;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.GameNode;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;

/**
 * Being a {@link MouseMovedListener} enables the {@link YaegerEntity} to be notified if the mouse has been moved.
 * On movement it will receive an event that contains the x and y-coordinate.
 */
public interface MouseMovedListener extends GameNode {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse pressed event.
     *
     * @param coordinate2D the current coordinate of the mouse pointer
     */
    void onMouseMoved(final Coordinate2D coordinate2D);

    /**
     * Attach a mouse moved Listener to this entity. Note that the MouseMoved listener gets attached to the {@link javafx.scene.Scene}, which
     * results in the fact that this listener is called whenever the mouse is moved within the {@link javafx.scene.Scene}.
     */
    @OnActivation
    default void attachMouseMovedListener() {
        if (this instanceof YaegerEntity) {
            getNode().ifPresent(node -> node.getScene().setOnMouseMoved(event -> onMouseMoved(new Coordinate2D(event.getX(), event.getY()))));
        } else if (this instanceof YaegerScene) {
            getNode().ifPresent(node -> node.setOnMouseMoved(event -> onMouseMoved(new Coordinate2D(event.getX(), event.getY()))));
        }
    }
}
