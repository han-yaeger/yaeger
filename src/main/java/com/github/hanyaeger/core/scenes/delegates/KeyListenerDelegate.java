package com.github.hanyaeger.core.scenes.delegates;

import com.github.hanyaeger.core.YaegerConfig;
import com.github.hanyaeger.core.factories.animationtimer.AnimationTimerFactory;
import com.google.inject.Inject;
import javafx.animation.AnimationTimer;
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

    Set<KeyCode> delayedInput = new HashSet<>();
    Set<KeyCode> input = new HashSet<>();
    private KeyListener keyListener;
    private AnimationTimerFactory animationTimerFactory;
    private AnimationTimer animationTimer;

    /**
     * Setup this {@code KeyListenerDelegate} for a specific {@link Scene} and {@link KeyListener}.
     *
     * @param scene       the {@link Scene} to which this delegate should be attached
     * @param keyListener the {@link KeyListener} to which the events should be delegates
     * @param config      the {@link YaegerConfig}
     */
    public void setup(final Scene scene, final KeyListener keyListener, final YaegerConfig config) {
        this.keyListener = keyListener;
        this.animationTimer = animationTimerFactory.create(this::triggerManualEvent, config.limitGWU());

        scene.setOnKeyPressed(e -> {
            final var code = e.getCode();
            if (input.contains(code)) {
                delayedInput.remove(code);
            } else {
                delayedInput.add(code);
                input.add(code);
            }
            inputChanged(input);
        });

        scene.setOnKeyReleased(e -> {
            final var code = e.getCode();
            input.remove(code);
            delayedInput.remove(code);
            inputChanged(input);
        });
    }

    private void triggerManualEvent(final long timestamp) {
        inputChanged(input);
    }

    /**
     * Tear down this {@code KeyListenerDelegate} by removing the listeners from the {@link Scene}.
     *
     * @param scene the {@link Scene} from which the listeners should be removed
     */
    public void tearDown(final Scene scene) {
        animationTimer.stop();
        scene.setOnKeyPressed(null);
        scene.setOnKeyReleased(null);
        keyListener = null;
    }

    private void inputChanged(final Set<KeyCode> input) {
        if (delayedInput.isEmpty()) {
            animationTimer.stop();
        } else {
            animationTimer.start();
        }
        keyListener.onPressedKeysChange(input);
    }

    /**
     * The {@link AnimationTimer} will be used to generate events during the time that a key is
     * pressed for the first time, and the key-press-delay is active.
     *
     * @param animationTimerFactory the {@link AnimationTimerFactory} that will be used when creating
     *                              an instance of {@link AnimationTimer}
     */
    @Inject
    public void setAnimationTimerFactory(final AnimationTimerFactory animationTimerFactory) {
        this.animationTimerFactory = animationTimerFactory;
    }
}
