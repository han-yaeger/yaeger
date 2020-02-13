package nl.meron.showcase.scenes.textentities.entities.timers;

import nl.meron.showcase.scenes.textentities.entities.TimedDynamicTextEntity;
import nl.meron.yaeger.engine.Timer;

public class TimedDynamicTextTimer extends Timer {

    private TimedDynamicTextEntity timedDynamicTextEntity;

    /**
     * Create a new instance of {@link Timer} for the given interval in milliseconds.
     *
     * @param intervalInMs The interval in milleseconds.
     */
    public TimedDynamicTextTimer(TimedDynamicTextEntity timedDynamicTextEntity, int intervalInMs) {
        super(intervalInMs);
        this.timedDynamicTextEntity = timedDynamicTextEntity;
    }

    @Override
    public void onAnimationUpdate(long timestamp) {
        timedDynamicTextEntity.nextLine();
    }

}
