package com.github.hanyaeger.core.factories.animationtimer;

import javafx.animation.AnimationTimer;

/**
 * An {@link AnimationTimer} that calls its {@code handle()} method on the given interval, and not the default 60 times
 * per second.
 */
public abstract class TimeableAnimationTimer extends AnimationTimer {

    private final long interval;
    private long prevTime = 0;

    /**
     * Creat a new {@code TimeableAnimationTimer} for the given interval time in milliseconds.
     *
     * @param interval The interval time in milliseconds.
     */
    public TimeableAnimationTimer(final long interval) {
        this.interval = interval * 1_000_000;
    }

    @Override
    public void handle(final long now) {

        // some delay
        if ((now - prevTime) < interval) {
            return;
        }

        prevTime = now;

        handle();
    }

    /**
     * Method called whenever the interval time is reached
     */
    public abstract void handle();

}
