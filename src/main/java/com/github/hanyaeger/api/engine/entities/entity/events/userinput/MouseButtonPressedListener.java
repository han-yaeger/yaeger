package com.github.hanyaeger.api.engine.entities.entity.events.userinput;

import com.github.hanyaeger.api.engine.annotations.OnActivation;
import com.github.hanyaeger.api.engine.entities.entity.GameNode;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import javafx.scene.input.MouseButton;

/**
 * Being a {@link MouseButtonPressedListener} enables the {@link YaegerEntity} to be notified if a {@link MouseButton} has been clicked
 * while the mouse pointer is on the {@link YaegerEntity}.
 */
public interface MouseButtonPressedListener extends GameNode {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse pressed event.
     *
     * @param button the {@link MouseButton} being pressed
     * @param x      the x-coordinate of the mouse pointer while being pressed as a {@code double}
     * @param y      the y-coordinate of the mouse pointer while being pressed as a {@code double}
     */
    void onMouseButtonPressed(final MouseButton button, final double x, final double y);

    /**
     * Attach a mousePressedListener to this entity.
     */
    @OnActivation
    default void attachMousePressedListener() {
        getNode().ifPresent(node -> node.setOnMousePressed(event -> onMouseButtonPressed(event.getButton(), event.getX(), event.getY())));
    }
}
