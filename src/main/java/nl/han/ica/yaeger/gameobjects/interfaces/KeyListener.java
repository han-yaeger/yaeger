package nl.han.ica.yaeger.gameobjects.interfaces;

import java.util.Set;

/**
 * A KeyListener is notified when the set of pressed keys changes.
 */
public interface KeyListener {

    /**
     * Called when the set of pressed keys changes.
     *
     * @param pressedKeys A Set of String representations of the keys that are currently pressed
     */
    void onPressedKeysChange(Set<String> pressedKeys);
}
