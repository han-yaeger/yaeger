package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.scenes.ScrollableDynamicScene;
import com.github.hanyaeger.core.annotations.OnActivation;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.entities.GameNode;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.YaegerScene;

/**
 * A {@code MouseMovedListener} enables the {@link YaegerEntity} or {@link YaegerScene} to be
 * notified if the mouse has been moved. On movement, it will receive an event that contains a {@link Coordinate2D}
 * of the x and y-coordinate. Note that this listener is only notified of mouse movement if the user is <b>not</b> dragging a
 * {@link YaegerEntity} that implements {@link MouseDraggedListener}.
 * <p>
 * If this {@code MouseMovedListener} is implemented by a {@link com.github.hanyaeger.api.scenes.ScrollableDynamicScene},
 * the {@link Coordinate2D} that is passed to the event handler is relative to the full scene.
 */
public interface MouseMovedListener extends GameNode {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse moved event.
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
            getNode().ifPresent(node -> node.getScene().setOnMouseMoved(event ->
                    onMouseMoved(new Coordinate2D(event.getX(), event.getY()))
            ));
        } else if (this instanceof ScrollableDynamicScene scrollableDynamicScene) {
            scrollableDynamicScene.getRootPane().setOnMouseMoved(event ->
                    onMouseMoved(new Coordinate2D(event.getX(), event.getY())));
        } else if (this instanceof YaegerScene) {
            getNode().ifPresent(node -> node.setOnMouseMoved(event ->
                    onMouseMoved(new Coordinate2D(event.getX(), event.getY()))
            ));
        }
    }
}
