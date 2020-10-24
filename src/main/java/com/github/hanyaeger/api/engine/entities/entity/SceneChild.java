package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.scenes.YaegerScene;

/**
 * A {@link SceneChild} is part of a {@link YaegerScene}
 * and has thus access to width and height of the {@link YaegerScene} it
 * is part of.
 */
public interface SceneChild extends GameNode {

    /**
     * Return the width of the {@link javafx.scene.Scene} that this {@code Entity}
     * is part of.
     *
     * @return the width of this {@link YaegerScene} as a {@code double}
     */
    default double getSceneWidth() {
        if (getNode().isPresent()) {
            return getNode().get().getScene().getWidth();
        } else {
            return 0;
        }
    }

    /**
     * Return the height of the {@link javafx.scene.Scene} that this {@code Entity}
     * is part of.
     *
     * @return the height of this {@link YaegerScene} as a {@code double}
     */
    default double getSceneHeight() {
        if (getNode().isPresent()) {
            return getNode().get().getScene().getHeight();
        } else {
            return 0;
        }
    }
}
