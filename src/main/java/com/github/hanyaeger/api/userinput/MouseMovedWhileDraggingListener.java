package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.ScrollableDynamicScene;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.core.annotations.OnActivation;
import com.github.hanyaeger.core.entities.GameNode;
import javafx.scene.Node;

/**
 * Being a {@link MouseMovedWhileDraggingListener} enables the {@link YaegerEntity} or {@link YaegerScene} to be
 * notified if the mouse has been moved while it is dragging a {@link MouseDraggedListener} or nothing at all. This means
 * it can also be used to receive notification when the mouse is being moved while the mouse-button is down. In this it
 * differs from a {@link MouseMovedListener}, which does not receive those events if the button is down.
 * <p>
 * On movement it will receive an event that contains a {@link Coordinate2D} of the x and y-coordinate.
 */
public interface MouseMovedWhileDraggingListener extends GameNode {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse pressed event.
     *
     * @param coordinate2D the current coordinate of the mouse pointer
     */
    void onMouseMovedWhileDragging(final Coordinate2D coordinate2D);

    /**
     * Attach a mouse moved while dragged Listener to this entity. Note that the listener gets attached to the
     * {@link javafx.scene.Scene}, which results in the fact that this listener is called whenever the mouse is
     * moved within the {@link javafx.scene.Scene}.
     */
    @OnActivation
    default void attachMouseMovedWhileDraggedListener() {
        if (this instanceof YaegerEntity) {
            getNode().ifPresent(node -> node.getScene().setOnDragDetected(event -> node.getScene().startFullDrag()));
            getNode().ifPresent(node -> node.getScene().setOnMouseDragOver(event -> onMouseMovedWhileDragging(new Coordinate2D(event.getX(), event.getY()))));
        } else if (this instanceof ScrollableDynamicScene scrollableDynamicScene) {
            handleMouseMovedWhileDragging(scrollableDynamicScene.getRootPane());
        } else if (this instanceof YaegerScene) {
            getNode().ifPresent(this::handleMouseMovedWhileDragging);
        }
    }


    private void handleMouseMovedWhileDragging(final Node node) {
        node.setOnDragDetected(event -> node.startFullDrag());
        node.setOnMouseDragOver(event -> onMouseMovedWhileDragging(new Coordinate2D(event.getX(), event.getY())));
    }
}
