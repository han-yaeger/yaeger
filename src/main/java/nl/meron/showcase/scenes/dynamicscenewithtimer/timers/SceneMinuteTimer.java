package nl.meron.showcase.scenes.dynamicscenewithtimer.timers;

import nl.meron.showcase.scenes.dynamicscenewithtimer.DynamicSceneWithTimer;
import nl.meron.showcase.scenes.textentities.entities.TimedDynamicTextEntity;
import nl.meron.yaeger.engine.Timer;

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
