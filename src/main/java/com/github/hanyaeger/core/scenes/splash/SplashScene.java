package com.github.hanyaeger.core.scenes.splash;

import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.YaegerGame;
import com.github.hanyaeger.api.scenes.DynamicScene;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;

/**
 * A Splash Screen, being an instance of {@link YaegerScene} that
 * will be shown a given number of milliseconds at the start of a {@link YaegerGame}.
 */
public class SplashScene extends DynamicScene implements TimerContainer {

    private final Runnable runnable;

    /**
     * Create a new instance of {@link SplashScene}.
     *
     * @param runnable The {@link Runnable} that will be called when the {@link SplashSceneTimer} transmits
     *                 an {@link Timer#onAnimationUpdate(long)}.
     */
    SplashScene(final Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void setupScene() {
        setBrightness(-1);
        setBackgroundImage("yaegerimages/splash-bg.jpg");
    }

    @Override
    public void setupEntities() {
        var logo = new Logo(new Coordinate2D(getWidth() / 2, getHeight() / 2));
        logo.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(logo);
    }

    @Override
    public void setupTimers() {
        addTimer(new SplashSceneTimer(runnable));
        addTimer(new FadeInTimer(this));
    }
}
