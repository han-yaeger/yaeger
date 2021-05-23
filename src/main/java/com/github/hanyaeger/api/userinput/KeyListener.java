package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.YaegerScene;
import javafx.scene.input.KeyCode;

import java.util.Set;

/**
 * A {@link KeyListener} is notified when the set of pressed keys changes. This interface can be implemented
 * by either an {@link YaegerEntity} or an {@link YaegerScene}.
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
