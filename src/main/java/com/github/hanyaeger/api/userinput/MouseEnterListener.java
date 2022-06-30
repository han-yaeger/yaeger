package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.scenes.ScrollableDynamicScene;
import com.github.hanyaeger.core.annotations.OnActivation;
import com.github.hanyaeger.core.entities.GameNode;
import com.github.hanyaeger.api.entities.YaegerEntity;
import javafx.scene.input.MouseEvent;

/**
 * A {@link MouseEnterListener} enables the {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}
 * to be notified if the Mouse Cursor has entered the area defined by the {@link javafx.geometry.BoundingBox} of the {@link YaegerEntity} or
 * {@link com.github.hanyaeger.api.scenes.YaegerScene}.
 */
public interface MouseEnterListener extends GameNode {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse enter event.
     */
    void onMouseEntered();

    /**
     * Attach a {@link MouseEnterListener} to this {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}
     */
    @OnActivation
    default void attachMouseEnterListener() {
        if (this instanceof ScrollableDynamicScene scrollableDynamicScene) {
            scrollableDynamicScene.getRootPane().setOnMouseEntered(this::handleEnter);
        } else {
            getNode().ifPresent(node -> node.setOnMouseEntered(this::handleEnter));
        }
    }

    private void handleEnter(final MouseEvent event) {
        onMouseEntered();
        event.consume();
    }
}
