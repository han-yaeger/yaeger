package com.github.hanyaeger.api.engine;

import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;

/**
 * Abstract superclass of all timers that are available for both a {@link YaegerScene}
 * and an {@link YaegerEntity}.
 * <p>
 * Extend this class and implement the method {@link #onAnimationUpdate(long)}.
 */
public abstract class Timer {

    private final long interval;
    private long prevTime = 0;

    /**
     * Create a new instance of {@link Timer} for the given interval in milliseconds.
     *
     * @param intervalInMs The interval in milleseconds.
     */
    public Timer(final long intervalInMs) {
        this.interval = intervalInMs * 1_000_000;
    }

    protected void handle(final long now) {

        if (prevTime == 0) {
            prevTime = now;
        }

        if ((now - prevTime) < interval) {
            return;
        }

        prevTime = now;

        onAnimationUpdate(now);
    }

    /**
     * Implement this method, which will be called each animation update with the given interval.
     *
     * @param timestamp The timestamp of the current frame given in nanoseconds.
     */
    public abstract void onAnimationUpdate(final long timestamp);
}
