package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.core.annotations.OnActivation;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.entities.GameNode;
import com.github.hanyaeger.api.entities.YaegerEntity;
import javafx.scene.input.MouseButton;

/**
 * Being a {@link MouseButtonPressedListener} enables the {@link YaegerEntity} to be notified if a {@link MouseButton} has been clicked
 * while the mouse pointer is on the {@link YaegerEntity}.
 */
public interface MouseButtonPressedListener extends GameNode {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse pressed event.
     *
     * @param button       the {@link MouseButton} being pressed
     * @param coordinate2D the current coordinate of the mouse pointer
     */
    void onMouseButtonPressed(final MouseButton button, final Coordinate2D coordinate2D);

    /**
     * Attach a mousePressedListener to this entity.
     */
    @OnActivation
    default void attachMousePressedListener() {
        getNode().ifPresent(node -> node.setOnMousePressed(event -> onMouseButtonPressed(event.getButton(), new Coordinate2D(event.getX(), event.getY()))));
    }
}
