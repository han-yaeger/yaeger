package com.github.hanyaeger.api.engine.scenes.splash.timers;

import com.github.hanyaeger.api.engine.Timer;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;

public class FadeInTimer extends Timer {

    private static final int INTERVAL_IN_MS = 10;

    private final YaegerScene yaegerScene;

    /**
     * Create a new instance of {@link FadeInTimer}.
     *
     * @param yaegerScene The {@link YaegerScene} that will be called when the {@link SplashSceneTimer} transmits
     *                    an {@link Timer#onAnimationUpdate(long)}.
     */
    public FadeInTimer(final YaegerScene yaegerScene) {
        super(INTERVAL_IN_MS);
        this.yaegerScene = yaegerScene;
    }

    @Override
    public void onAnimationUpdate(long timestamp) {
        if (yaegerScene.getBrightness() < 0d) {
            yaegerScene.setBrightness(yaegerScene.getBrightness() + 0.01);
        }
    }
}
