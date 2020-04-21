package nl.han.showcase.scenes.spriteentities.entities.timers;

import nl.han.showcase.scenes.spriteentities.entities.RugbyBall;
import nl.han.yaeger.engine.Timer;

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
