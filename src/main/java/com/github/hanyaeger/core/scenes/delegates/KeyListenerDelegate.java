package com.github.hanyaeger.core.scenes.delegates;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import com.github.hanyaeger.api.userinput.KeyListener;

import java.util.HashSet;
import java.util.Set;

/**
 * A {@link KeyListenerDelegate} follows the Delegate pattern and embraces Composition over Inheritance.
 * <p>
 * It can be used to attach and remove keyListeners to a {@link Scene}.
 */
public class KeyListenerDelegate {

    Set<KeyCode> input = new HashSet<>();
    private KeyListener keyListener;

    /**
     * Setup this {@code KeyListenerDelegate} for a specific {@link Scene} and {@link KeyListener}.
     *
     * @param scene       the {@link Scene} to which this delegate should be attached
     * @param keyListener the {@link KeyListener} to which the events should be delegateds
     */
    public void setup(final Scene scene, final KeyListener keyListener) {
        this.keyListener = keyListener;
        scene.setOnKeyPressed(
                e -> {
                    final var code = e.getCode();
                    input.add(code);
                    inputChanged(input);
                });

        scene.setOnKeyReleased(
                e -> {
                    final var code = e.getCode();
                    input.remove(code);
                    inputChanged(input);
                });
    }

    /**
     * Tear down this {@code KeyListenerDelegate} by removing the listeners from the {@link Scene}.
     *
     * @param scene the {@link Scene} from which the listeners should be removed
     */
    public void tearDown(final Scene scene) {
        keyListener = null;
        scene.setOnKeyPressed(null);
        scene.setOnKeyReleased(null);
    }

    private void inputChanged(final Set<KeyCode> input) {
        keyListener.onPressedKeysChange(input);
    }
}
