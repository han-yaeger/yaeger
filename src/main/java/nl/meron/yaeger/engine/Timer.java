package nl.meron.yaeger.engine;

/**
 * Abstract superclass of all timers that are available for bot a {@link nl.meron.yaeger.engine.scenes.YaegerScene}
 * and an {@link nl.meron.yaeger.engine.entities.entity.Entity}.
 * <p>
 * Extend this class and implement the method {@link #onAnimationUpdate(long)} the call the actual update method.
 */
public abstract class Timer {

    private long interval;
    private long prevTime = 0;

    /**
     * Create a new instance of {@link Timer} for the given interval in milliseconds.
     *
     * @param intervalInMs The interval in milleseconds.
     */
    public Timer(int intervalInMs) {
        this.interval = intervalInMs * 1_000_000;
    }

    void handle(final long now) {

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
    public abstract void onAnimationUpdate(long timestamp);
}
