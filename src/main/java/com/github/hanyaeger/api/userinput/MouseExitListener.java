package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.core.annotations.OnActivation;
import com.github.hanyaeger.core.entities.GameNode;
import com.github.hanyaeger.api.entities.YaegerEntity;

/**
 * Being a {@link MouseExitListener} enables the {@link YaegerEntity} or {@link com.github.hanyaeger.api.scenes.YaegerScene}
 * to be notified if the Mouse Cursor has exited the area defined by the {@link javafx.geometry.BoundingBox} of an {@link YaegerEntity}
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
        getNode().ifPresent(node -> node.setOnMouseExited(event -> {
            onMouseExited();
            event.consume();
        }));
    }
}
