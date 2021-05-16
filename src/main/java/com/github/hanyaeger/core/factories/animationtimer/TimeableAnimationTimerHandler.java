package com.github.hanyaeger.core.factories.animationtimer;

/**
 * A Functional Interface to be used for the handler of a {@link javafx.animation.AnimationTimer}.
 */
@FunctionalInterface
public interface TimeableAnimationTimerHandler {
    /**
     * Called when a {@link javafx.animation.AnimationTimer} calls its internal {@link javafx.animation.AnimationTimer#handle(long)} ()}
     */
    void handle();
}
