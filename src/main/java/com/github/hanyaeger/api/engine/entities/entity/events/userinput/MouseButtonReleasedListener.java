package com.github.hanyaeger.api.engine.entities.entity.events.userinput;

import com.github.hanyaeger.api.engine.annotations.OnActivation;
import com.github.hanyaeger.api.engine.entities.entity.NodeProvider;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import javafx.scene.input.MouseButton;

/**
 * Being a {@link MouseButtonReleasedListener} enables the {@link YaegerEntity} to be notified if a {@link MouseButton} has been
 * released while the mouse pointer is on the {@link YaegerEntity}.
 */
public interface MouseButtonReleasedListener extends NodeProvider {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse released event.
     *
     * @param button the {@link MouseButton} being released.
     * @param x      The x-coordinate of the mouse pointer while being released as a {@code double}.
     * @param y      The y-coordinate of the mouse pointer while being released as a {@code double}.
     */
    void onMouseButtonReleased(MouseButton button, double x, double y);

    /**
     * Attach a {@link MouseButtonReleasedListener} to this entity.
     */
    @OnActivation
    default void attachMouseReleasedListener() {
        getGameNode().get().setOnMouseReleased(event -> onMouseButtonReleased(event.getButton(), event.getX(), event.getY()));
    }
}
