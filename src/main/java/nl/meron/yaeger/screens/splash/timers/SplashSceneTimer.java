package nl.meron.yaeger.screens.splash.timers;

import nl.meron.yaeger.engine.Timer;

public class SplashSceneTimer extends Timer {

    private static final int INTERVAL_IN_MS = 3000;

    private Runnable runnable;

    public SplashSceneTimer(Runnable runnable) {
        super(INTERVAL_IN_MS);
        this.runnable = runnable;
    }

    @Override
    public void onAnimationUpdate(long timestamp) {
        runnable.run();
    }
}
