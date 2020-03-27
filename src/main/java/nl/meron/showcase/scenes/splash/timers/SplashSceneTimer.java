package nl.meron.showcase.scenes.splash.timers;

import nl.meron.showcase.scenes.splash.SplashScene;
import nl.meron.yaeger.engine.Timer;

public class SplashSceneTimer extends Timer {

    private SplashScene scene;

    public SplashSceneTimer(SplashScene scene) {
        super(2000);
        this.scene = scene;
    }

    @Override
    public void onAnimationUpdate(long timestamp) {

        scene.update();
    }

}
