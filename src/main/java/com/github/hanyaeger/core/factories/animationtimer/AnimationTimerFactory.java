package com.github.hanyaeger.core.factories.animationtimer;

import com.github.hanyaeger.core.YaegerAnimationTimer;
import com.google.inject.Singleton;
import javafx.animation.AnimationTimer;

/**
 * An {@code AnimationTimerFactory} should be used to create instances of an {@link AnimationTimer}.
 */
@Singleton
public class AnimationTimerFactory {

    /**
     * Create an {@link AnimationTimer} with the given handler.
     *
     * @param handler  an {@link AnimationTimerHandler} that should be called whenever the {@link AnimationTimer} calls its
     *                 {@code handle()} method. By default, this is done 60 times per second
     * @param limitGWU limit the Game World Update of this {@link YaegerAnimationTimer} to a max of 60 updates per second
     * @return an instance of {@link AnimationTimer}
     */
    public AnimationTimer create(final AnimationTimerHandler handler, final boolean limitGWU) {
        return
                new YaegerAnimationTimer(limitGWU) {
                    @Override
                    public void handleOn60fps(long arg0) {
                        handler.handle(arg0);
                    }
                };
    }

    /**
     * Create an {@link AnimationTimer} with the given handler for the given interval.
     *
     * @param handler  an {@link AnimationTimerHandler} that should be called whenever the {@link AnimationTimer} calls its
     *                 {@code handle()} method.
     * @param interval the given interval in milliseconds
     * @return an instance of {@link AnimationTimer}
     */
    public AnimationTimer createTimeableAnimationTimer(final TimeableAnimationTimerHandler handler, final long interval) {
        return new TimeableAnimationTimer(interval) {

            @Override
            public void handle() {
                handler.handle();
            }
        };
    }
}
