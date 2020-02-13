package nl.meron.showcase.scenes.spriteentities.entities.timers;

import nl.meron.showcase.scenes.spriteentities.entities.RugbyBall;
import nl.meron.yaeger.engine.Timer;

public class RugbyBallTimer extends Timer {

    private RugbyBall rugbyBall;

    public RugbyBallTimer(RugbyBall rugbyBall, int intervalInMs) {
        super(intervalInMs);
        this.rugbyBall = rugbyBall;
    }

    @Override
    public void onAnimationUpdate(long timestamp) {
        rugbyBall.changeDirection();
    }
}
