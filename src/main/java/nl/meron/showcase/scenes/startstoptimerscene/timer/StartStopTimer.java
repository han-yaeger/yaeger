package nl.meron.showcase.scenes.startstoptimerscene.timer;

import nl.meron.showcase.scenes.startstoptimerscene.StartStopTimerScene;

public class StartStopTimer extends Thread {

    private long startTime;
    private boolean started;
    private StartStopTimerScene scene;

    public void startThread(){
        this.startTime = System.currentTimeMillis();
        this.started = true;
    }

    public void run() {
        while (started) {
            this.start();
        }
    }

    public int[] getTime() {
        long milliTime = System.currentTimeMillis() - this.startTime;
        int[] out = new int[]{0, 0, 0, 0};
        out[0] = (int)(milliTime / 3600000      );
        out[1] = (int)(milliTime / 60000        ) % 60;
        out[2] = (int)(milliTime / 1000         ) % 60;
        out[3] = (int)(milliTime)                 % 1000;

        return out;
    }


    public void stopThread() {
        this.started = false;
    }

}
