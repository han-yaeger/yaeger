package nl.meron.showcase.scenes.startstoptimerscene;

import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.scenes.ShowCaseScene;
import nl.meron.showcase.scenes.startstoptimerscene.timer.StartStopTimer;

public class StartStopTimerScene extends ShowCaseScene {

    private YaegerShowCase showCase;


    @Override
    public void setupScene() {
        setBackgroundImage("showcase/backgrounds/milky-way.jpg");
    }

    @Override
    protected void setupSpawners() {

    }

    @Override
    public void setupEntities() {
        StartStopTimer s = new StartStopTimer();

        s.startThread();

        while (true)
        {
            int[] curTime = s.getTime();
            System.out.println(curTime[0] + " : " + curTime[1] + " : " + curTime[2] + " : " + curTime[3]);
        }
    }

    @Override
    public YaegerShowCase getShowCase() {
        return showCase;
    }
}
