package com.github.hanyaeger.core.scenes.splash;

import com.github.hanyaeger.api.Timer;

/**
 * The {@link Timer} that will be used for timing the duration of a
 * {@link SplashScene}.
 */
class SplashSceneTimer extends Timer {

    private static final int INTERVAL_IN_MS = 5000;

    private final Runnable runnable;

    /**
     * Create a new instance of {@link SplashSceneTimer}.
     *
     * @param runnable The {@link Runnable} that will be called when the {@link SplashSceneTimer} transmits
     *                 an {@link Timer#onAnimationUpdate(long)}.
     */
    SplashSceneTimer(final Runnable runnable) {
        super(INTERVAL_IN_MS);
        this.runnable = runnable;
    }

    @Override
    public void onAnimationUpdate(final long timestamp) {
        runnable.run();
    }
}
