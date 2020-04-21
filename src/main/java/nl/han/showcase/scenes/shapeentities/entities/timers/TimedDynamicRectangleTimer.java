package nl.han.showcase.scenes.shapeentities.entities.timers;

import nl.han.showcase.scenes.shapeentities.entities.TimedDynamicRectangle;
import nl.han.yaeger.engine.Timer;

public class TimedDynamicRectangleTimer extends Timer {

    private TimedDynamicRectangle timedDynamicRectangleint;

    /**
     * Create a new instance of {@link Timer} for the given interval in milliseconds.
     *
     * @param intervalInMs The interval in milleseconds.
     */
    public TimedDynamicRectangleTimer(TimedDynamicRectangle imedDynamicRectangleint, int intervalInMs) {
        super(intervalInMs);
        this.timedDynamicRectangleint = imedDynamicRectangleint;
    }

    @Override
    public void onAnimationUpdate(long timestamp) {
        timedDynamicRectangleint.setArc();
    }
}
