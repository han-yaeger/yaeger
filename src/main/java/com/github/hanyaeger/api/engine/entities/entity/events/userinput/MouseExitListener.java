package com.github.hanyaeger.api.engine.entities.entity.events.userinput;

import com.github.hanyaeger.api.engine.annotations.OnActivation;
import com.github.hanyaeger.api.engine.entities.entity.NodeProvider;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;

/**
 * Being a {@link MouseExitListener} enables the {@link YaegerEntity} to be notified if the Mouse Cursor has
 * exited the area defined by the {@link javafx.geometry.BoundingBox} of an {@link YaegerEntity}.
 */
public interface MouseExitListener extends NodeProvider {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse exited event.
     */
    void onMouseExited();

    /**
     * Attach a {@link MouseExitListener} to this entity.
     */
    @OnActivation
    default void attachMouseExitListener() {
        getGameNode().get().setOnMouseExited(mouseEvent -> onMouseExited());
    }
}
