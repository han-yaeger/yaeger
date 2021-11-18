package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.scenes.ScrollableDynamicScene;
import com.github.hanyaeger.core.annotations.OnActivation;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.entities.GameNode;
import com.github.hanyaeger.api.entities.YaegerEntity;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * A {@code MouseButtonPressedListener} enables the {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}
 * to be notified if a {@link MouseButton} has been clicked while the mouse pointer is on the {@link YaegerEntity} or
 * {@link com.github.hanyaeger.api.scenes.YaegerScene}.
 * <p>
 * If this {@code MouseButtonPressedListener} is implemented by a {@link com.github.hanyaeger.api.scenes.ScrollableDynamicScene},
 * the {@link Coordinate2D} that is passed to the event handler is relative to the full scene.
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
     * Attach a mousePressedListener to this {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}.
     */
    @OnActivation
    default void attachMouseButtonPressedListener() {
        if (this instanceof ScrollableDynamicScene scrollableDynamicScene) {
            scrollableDynamicScene.getRootPane().setOnMousePressed(this::handleEvent);
        } else {
            getNode().ifPresent(node -> node.setOnMousePressed(this::handleEvent));
        }
    }

    private void handleEvent(final MouseEvent event) {
        onMouseButtonPressed(event.getButton(), new Coordinate2D(event.getX(), event.getY()));
        event.consume();
    }
}
