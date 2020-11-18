package com.github.hanyaeger.api.engine.entities.entity.events.userinput;

import javafx.scene.input.KeyCode;

import java.util.Set;

/**
 * A {@link KeyListener} is notified when the set of pressed keys changes. This interface can be implemented
 * by either an {@link com.github.hanyaeger.api.engine.entities.entity.YaegerEntity} or an {@link com.github.hanyaeger.api.engine.scenes.YaegerScene}.
 */
@FunctionalInterface
public interface KeyListener {

    /**
     * Called when the set of pressed keys changes.
     *
     * @param pressedKeys A {@link Set} of {@code KeyCode} representations of the keys that are currently pressed
     */
    void onPressedKeysChange(final Set<KeyCode> pressedKeys);
}
