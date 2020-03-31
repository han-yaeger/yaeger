package nl.meron.yaeger.screens.splash;

import javafx.scene.paint.Color;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.WithTimers;
import nl.meron.yaeger.engine.entities.entity.AnchorPoint;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.scenes.DynamicScene;
import nl.meron.yaeger.screens.splash.entities.Logo;
import nl.meron.yaeger.screens.splash.timers.SplashSceneTimer;

public class SplashScene extends DynamicScene implements WithTimers {

    private Runnable runnable;

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
        var logo = new Logo("logo.png", new Location(getWidth() / 2, getHeight() / 2), new Size(353, 115));
        logo.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(logo);
    }

    @Override
    public void setupTimers() {
        addTimer(new SplashSceneTimer(() -> runnable.run()));
    }
}
