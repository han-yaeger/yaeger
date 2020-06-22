package com.github.hanyaeger.api.engine.scenes.splash;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.Timer;
import com.github.hanyaeger.api.engine.TimerContainer;
import com.github.hanyaeger.api.engine.YaegerGame;
import com.github.hanyaeger.api.engine.scenes.DynamicScene;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;
import com.github.hanyaeger.api.engine.scenes.splash.timers.SplashSceneTimer;
import javafx.scene.paint.Color;
import com.github.hanyaeger.api.engine.entities.entity.AnchorPoint;
import com.github.hanyaeger.api.engine.entities.entity.Location;
import com.github.hanyaeger.api.engine.scenes.splash.entities.Logo;

/**
 * A Splash Screen, being an instance of {@link YaegerScene} that
 * will be shown a given number of miliseconds at the start of a {@link YaegerGame}.
 */
public class SplashScene extends DynamicScene implements TimerContainer {

    private Runnable runnable;

    /**
     * Create a new instance of {@link SplashScene}.
     *
     * @param runnable The {@link Runnable} that will be called when the {@link SplashSceneTimer} transmits
     *                 an {@link Timer#onAnimationUpdate(long)}.
     */
    public SplashScene(final Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void setupScene() {
        setBackgroundColor(Color.BLACK);
    }

    @Override
    public void setupEntities() {
        var logo = new Logo(new Location(getWidth() / 2, getHeight() / 2), new Size(353, 115));
        logo.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(logo);
    }

    @Override
    public void setupTimers() {
        addTimer(new SplashSceneTimer(() -> runnable.run()));
    }
}
