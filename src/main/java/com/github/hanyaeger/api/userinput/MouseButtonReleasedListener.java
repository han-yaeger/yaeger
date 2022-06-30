package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.scenes.ScrollableDynamicScene;
import com.github.hanyaeger.core.annotations.OnActivation;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.entities.GameNode;
import com.github.hanyaeger.api.entities.YaegerEntity;
import javafx.scene.input.MouseButton;

/**
 * A {@code MouseButtonReleasedListener} enables the {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}
 * to be notified if a {@link MouseButton} has been released while the mouse pointer is on the {@link YaegerEntity} or
 * {@link com.github.hanyaeger.api.scenes.YaegerScene}.
 * <p>
 * If this {@code MouseButtonReleasedListener} is implemented by a {@link com.github.hanyaeger.api.scenes.ScrollableDynamicScene},
 * the {@link Coordinate2D} that is passed to the event handler is relative to the full scene.
 */
public interface MouseButtonReleasedListener extends GameNode {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse released event.
     *
     * @param button       the {@link MouseButton} being released
     * @param coordinate2D the current coordinate of the mouse pointer
     */
    void onMouseButtonReleased(final MouseButton button, final Coordinate2D coordinate2D);

    /**
     * Attach a {@link MouseButtonReleasedListener} to this {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}.
     */
    @OnActivation
    default void attachMouseButtonReleasedListener() {
        if (this instanceof ScrollableDynamicScene scrollableDynamicScene) {
            scrollableDynamicScene.getRootPane().setOnMouseReleased(event ->
                    onMouseButtonReleased(event.getButton(), new Coordinate2D(event.getX(), event.getY()))
            );
        } else {
            getNode().ifPresent(node -> node.setOnMouseReleased(event ->
                    onMouseButtonReleased(event.getButton(), new Coordinate2D(event.getX(), event.getY())))
            );
        }
    }
}
