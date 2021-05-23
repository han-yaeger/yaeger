package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.scenes.YaegerScene;

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
        return getNode().map(node -> node.getScene().getWidth()).orElse(0D);
    }

    /**
     * Return the height of the {@link javafx.scene.Scene} that this {@code Entity}
     * is part of.
     *
     * @return the height of this {@link YaegerScene} as a {@code double}
     */
    default double getSceneHeight() {
        return getNode().map(node -> node.getScene().getHeight()).orElse(0D);
    }
}
