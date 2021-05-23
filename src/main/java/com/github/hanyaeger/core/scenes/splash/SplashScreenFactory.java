package com.github.hanyaeger.core.scenes.splash;

import com.github.hanyaeger.api.scenes.YaegerScene;

/**
 * The factory to be used for creating instances of {@link SplashScene}.
 */
public class SplashScreenFactory {

    /**
     * Create an instance of {@link SplashScene}.
     *
     * @param runnable A {@link Runnable} that will be passed to the {@link SplashScene} as a
     *                 constructor parameter.
     * @return An instance of {@link SplashScene}.
     */
    public YaegerScene create(final Runnable runnable) {
        return new SplashScene(runnable);
    }
}
