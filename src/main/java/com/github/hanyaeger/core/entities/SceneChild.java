package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.scenes.YaegerScene;

/**
 * A {@link SceneChild} is part of a {@link YaegerScene}
 * and has thus acces to width and height of the {@link YaegerScene} it
 * is part of.
 */
public interface SceneChild extends GameNode {

    /**
     * Return the width of the {@link YaegerScene} that this {@code Entity}
     * is part of. For both a {@link com.github.hanyaeger.api.scenes.StaticScene} and
     * {@link com.github.hanyaeger.api.scenes.DynamicScene}, the full width is always visible.
     * In case of a {@link com.github.hanyaeger.api.scenes.ScrollableDynamicScene}, this is not the case, since
     * it can be larger that the viewable area.
     *
     * @return the width of this {@link YaegerScene} as a {@code double}
     */
    double getSceneWidth();

    /**
     * Return the height of the {@link YaegerScene} that this {@code Entity}
     * is part of. For both a {@link com.github.hanyaeger.api.scenes.StaticScene} and
     * {@link com.github.hanyaeger.api.scenes.DynamicScene}, the full height is always visible.
     * In case of a {@link com.github.hanyaeger.api.scenes.ScrollableDynamicScene}, this is not the case, since
     * it can be larger that the viewable area.
     *
     * @return the height of this {@link YaegerScene} as a {@code double}
     */
    double getSceneHeight();
}
