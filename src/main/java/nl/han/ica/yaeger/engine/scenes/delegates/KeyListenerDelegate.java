package nl.han.ica.yaeger.engine.scenes.delegates;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.engine.userinput.KeyListener;

import java.util.HashSet;
import java.util.Set;

/**
 * A {@link KeyListenerDelegate} follows the Delegate pattern and embraces Composition over Inheritence.
 * <p>
 * It can be used to attach and remove keyListerners to a {@link Scene}.
 */
public class KeyListenerDelegate {

    protected Set<KeyCode> input = new HashSet<>();
    private KeyListener keyListener;

    public void setup(Scene scene, KeyListener keyListener) {
        this.keyListener = keyListener;
        scene.setOnKeyPressed(
                e -> {
                    var code = e.getCode();
                    input.add(code);
                    inputChanged(input);
                });

        scene.setOnKeyReleased(
                e -> {
                    var code = e.getCode();
                    input.remove(code);
                    inputChanged(input);
                });
    }

    public void tearDown(Scene scene) {
        keyListener = null;
        scene.setOnKeyPressed(null);
        scene.setOnKeyReleased(null);
    }

    private void inputChanged(Set<KeyCode> input) {
        keyListener.onPressedKeysChange(input);
    }
}
