package nl.han.showcase.scenes.dynamicscenewithtimer.timers;

import nl.han.showcase.scenes.dynamicscenewithtimer.DynamicSceneWithTimer;
import nl.han.yaeger.engine.Timer;

public class SceneMinuteTimer extends Timer {

    private DynamicSceneWithTimer scene;

    public SceneMinuteTimer(DynamicSceneWithTimer scene) {
        super(1000);
        this.scene = scene;
    }

    @Override
    public void onAnimationUpdate(long timestamp) {
        scene.update();
    }

}
