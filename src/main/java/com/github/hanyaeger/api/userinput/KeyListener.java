package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.YaegerScene;
import javafx.scene.input.KeyCode;

import java.util.Set;

/**
 * A {@link KeyListener} is notified when the set of pressed keys changes. Because of the way the Operating System
 * handles consecutive key-pressed events, the event handler {@link #onPressedKeysChange(Set)} is called on the first
 * GWU when a key is pressed, but is not called in several of the consecutive GWU's if the key has not been released.
 * Only after a short period, the event handler receives the consecutive calls and is the called each GWU.
 * <p>
 * If the motion of a {@link YaegerEntity} is controlled through keystrokes and this Interface is used, this behaviour
 * might cause unwanted side effects (such as input lag) and should not be used.
 * <p>
 * This interface can be implemented by either an {@link YaegerEntity} or an {@link YaegerScene}.
 */
@FunctionalInterface
public interface KeyListener {

    /**
     * Called when the set of pressed keys changes. Because of the way the Operating System
     * handles consecutive key-pressed events, this event handler is called on the
     * first GWU when a key is pressed, but is not called in several of the consecutive GWU's if the key has not been
     * released. Only after a short period, the event handler receives the consecutive calls and is the called each GWU.
     * <p>
     * If the motion of a {@link YaegerEntity} is controlled through keystrokes and this Interface is used, this
     * behaviour might cause unwanted side effects (such as input lag) and should not be used.
     *
     * @param pressedKeys A {@link Set} of {@code KeyCode} representations of the keys that are currently pressed
     */
    void onPressedKeysChange(final Set<KeyCode> pressedKeys);
}
