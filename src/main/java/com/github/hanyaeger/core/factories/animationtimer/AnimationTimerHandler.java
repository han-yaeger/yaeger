package com.github.hanyaeger.core.factories.animationtimer;


/**
 * A Functional Interface to be used for the default handler of a {@link javafx.animation.AnimationTimer}.
 */
@FunctionalInterface
public interface AnimationTimerHandler {
    /**
     * Called when a {@link javafx.animation.AnimationTimer} calls its internal {@code handle()}
     *
     * @param interval  the interval in milliseconds at which the {@link javafx.animation.AnimationTimer} should
     *                  call its internal {@code handle()}
     */
    void handle(final long interval);
}
