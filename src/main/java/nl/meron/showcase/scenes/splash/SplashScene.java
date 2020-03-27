package nl.meron.showcase.scenes.splash;

import javafx.scene.paint.Color;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.scenes.splash.timers.SplashSceneTimer;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.WithTimers;
import nl.meron.yaeger.engine.entities.entity.AnchorPoint;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.scenes.DynamicScene;

public class SplashScene extends DynamicScene implements WithTimers {

    private YaegerShowCase showCase;

    public SplashScene(final YaegerShowCase showCase) {
        this.showCase = showCase;
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
        var logo = new Logo("logo.png", new Location(getWidth() / 2, getHeight() / 2), new Size(247, 67));
        logo.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(logo);
    }

    @Override
    public void setupTimers() {
        addTimer(new SplashSceneTimer(this));
    }

    public void update() {
        showCase.setActiveScene(YaegerShowCase.SCENE_SELECTION);
    }
}
