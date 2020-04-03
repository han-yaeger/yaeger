package nl.meron.yaeger.engine.scenes.splash;

import javafx.scene.paint.Color;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.WithTimers;
import nl.meron.yaeger.engine.entities.entity.AnchorPoint;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.scenes.DynamicScene;
import nl.meron.yaeger.engine.scenes.splash.entities.Logo;
import nl.meron.yaeger.engine.scenes.splash.timers.SplashSceneTimer;

/**
 * A Splash Screen, being an instance of {@link nl.meron.yaeger.engine.scenes.YaegerScene} that
 * will be shown a given number of miliseconds at the start of a {@link nl.meron.yaeger.engine.YaegerApplication}.
 */
public class SplashScene extends DynamicScene implements WithTimers {

    private Runnable runnable;

    /**
     * Create a new instance of {@link SplashScene}.
     *
     * @param runnable The {@link Runnable} that will be called when the {@link SplashSceneTimer} transmits
     *                 an {@link nl.meron.yaeger.engine.Timer#onAnimationUpdate(long)}.
     */
    public SplashScene(final Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    protected void setupSpawners() {

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
