package nl.meron.yaeger.engine.entities.entity.events.userinput;

import javafx.scene.input.KeyCode;

import java.util.Set;

/**
 * A KeyListener is notified when the set of pressed keys changes.
 */
@FunctionalInterface
public interface KeyListener {

    /**
     * Called when the set of pressed keys changes.
     *
     * @param pressedKeys A Set of {@code KeyCode} representations of the keys that are currently pressed
     */
    void onPressedKeysChange(Set<KeyCode> pressedKeys);
}
