package nl.meron.showcase.scenes.dynamicscenewithtimer;

import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.showcase.scenes.ShowCaseScene;
import nl.meron.yaeger.engine.WithTimers;

public class DynamicSceneWithTimer extends ShowCaseScene implements WithTimers {

    private YaegerShowCase showCase;

    public DynamicSceneWithTimer(YaegerShowCase showCase) {
        this.showCase = showCase;
    }

    @Override
    public void registerTimers() {
        System.out.println("Register Timers called on the DynamicSceneWithTimer");
    }

    @Override
    protected void setupSpawners() {

    }

    @Override
    public void setupScene() {
        setBackgroundImage("showcase/backgrounds/milky-way.jpg");
    }

    @Override
    public void setupEntities() {
        var backButton = new Back(showCase);
        addEntity(backButton);

    }

    @Override
    public YaegerShowCase getShowCase() {
        return showCase;
    }
}
