package com.github.hanyaeger.core;

import javafx.animation.AnimationTimer;

/**
 * A specific implementation of {@link AnimationTimer} that limits the call
 * to {@link #handle(long)} to 60 times per second. Since the call is normally
 * tied to the framerate, it is not a constant. This implementation ties the call
 * to 60fps to ensure all Yaeger Games run at the same max speed.
 */
public abstract class YaegerAnimationTimer extends AnimationTimer {

    private static final long MIN_INTERVAL = 16_667_000;
    private long prevTime = 0;
    private final boolean limitGWU;

    /**
     * Create a new {@code YaegerAnimationTimer}, which adds the option of maximizing the timing interval
     * to 60 times per second, as opposed to the default {@link AnimationTimer}.
     *
     * @param limitGWU a boolean that indicates if the timing interval should be limited
     */
    protected YaegerAnimationTimer(final boolean limitGWU) {
        this.limitGWU = limitGWU;
    }

    @Override
    public void handle(final long now) {

        if (limitGWU) {
            if (prevTime == 0) {
                prevTime = now;
            }
            if ((now - prevTime) < MIN_INTERVAL) {
                return;
            }

            prevTime = now;
        }

        handleOn60fps(now);
    }

    /**
     * This method needs to be overridden by extending classes. It is going to be called
     * in every frame while the AnimationTimer is active.
     *
     * @param now the timestamp of the current frame given in nanoseconds
     */
    public abstract void handleOn60fps(final long now);
}
