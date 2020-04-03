package nl.meron.yaeger.engine.scenes.splash.timers;

import nl.meron.yaeger.engine.Timer;

/**
 * The {@link Timer} that will be used for timing the duration of a
 * {@link nl.meron.yaeger.engine.scenes.splash.SplashScene}.
 */
public class SplashSceneTimer extends Timer {

    private static final int INTERVAL_IN_MS = 3000;

    private Runnable runnable;

    /**
     * Create a new instance of {@link SplashSceneTimer}.
     *
     * @param runnable The {@link Runnable} that will be called when the {@link SplashSceneTimer} transmits
     *                 an {@link nl.meron.yaeger.engine.Timer#onAnimationUpdate(long)}.
     */
    public SplashSceneTimer(final Runnable runnable) {
        super(INTERVAL_IN_MS);
        this.runnable = runnable;
    }

    @Override
    public void onAnimationUpdate(final long timestamp) {
        runnable.run();
    }
}
