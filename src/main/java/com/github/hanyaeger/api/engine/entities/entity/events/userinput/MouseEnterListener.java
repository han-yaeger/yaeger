package com.github.hanyaeger.api.engine.entities.entity.events.userinput;

import com.github.hanyaeger.api.engine.annotations.OnActivation;
import com.github.hanyaeger.api.engine.entities.entity.NodeProvider;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;

/**
 * Being a {@link MouseEnterListener} enables the {@link YaegerEntity} to be notified if the Mouse Cursor has
 * entered the area defined by the {@link javafx.geometry.BoundingBox} of an {@link YaegerEntity}.
 */
public interface MouseEnterListener extends NodeProvider {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse enter event.
     */
    void onMouseEntered();

    /**
     * Attach a {@link MouseEnterListener} to this entity.
     */
    @OnActivation
    default void attachMouseEnterListener() {
        getGameNode().get().setOnMouseEntered(mouseEvent -> onMouseEntered());
    }
}
