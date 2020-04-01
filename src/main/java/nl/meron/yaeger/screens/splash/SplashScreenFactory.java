package nl.meron.yaeger.screens.splash;

import nl.meron.yaeger.engine.scenes.YaegerScene;

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
    public YaegerScene create(Runnable runnable) {
        return new SplashScene(runnable);
    }
}
