package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.scenes.ScrollableDynamicScene;
import com.github.hanyaeger.core.annotations.OnActivation;
import com.github.hanyaeger.core.entities.GameNode;
import com.github.hanyaeger.api.entities.YaegerEntity;
import javafx.scene.input.MouseEvent;

/**
 * A {@link MouseExitListener} enables the {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}
 * to be notified if the Mouse Cursor has exited the area defined by the {@link javafx.geometry.BoundingBox} of the {@link YaegerEntity}
 * or {@link com.github.hanyaeger.api.scenes.YaegerScene}.
 */
public interface MouseExitListener extends GameNode {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse exited event.
     */
    void onMouseExited();

    /**
     * Attach a {@link MouseExitListener} to this {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}.
     */
    @OnActivation
    default void attachMouseExitListener() {
        if (this instanceof ScrollableDynamicScene scrollableDynamicScene) {
            scrollableDynamicScene.getRootPane().setOnMouseExited(this::handleExit);
        } else {
            getNode().ifPresent(node -> node.setOnMouseExited(this::handleExit));
        }
    }

    private void handleExit(final MouseEvent event) {
        onMouseExited();
        event.consume();
    }
}
