package com.github.hanyaeger.core.scenes.splash;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.UpdateExposer;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;
import com.github.hanyaeger.api.Coordinate2D;

/**
 * A {@link SpriteEntity} that encapsulates the logo.
 */
class Logo extends DynamicSpriteEntity implements UpdateExposer {

    private static final int TOTAL_NUMBER_OF_FRAMES = 21;
    private static final long ANIMATION_INTERVAL = 100 * 1_000_000;
    private int animationStartsAfter = 100;
    private int currentFrame = 1;
    private long prevTime = 0;

    /**
     * Create a new instance of {@link Logo} with the given {@link Coordinate2D} and {@link Size}.
     *
     * @param location The {@link Coordinate2D} at which the {@link Logo} should be placed.
     */
    Logo(final Coordinate2D location) {
        super("yaegerimages/logo-yaeger.png", location, new Size(8064, 121), 1, TOTAL_NUMBER_OF_FRAMES);
    }

    @Override
    public void explicitUpdate(long timestamp) {
        if (animationStartsAfter > 0) {
            animationStartsAfter--;
            return;
        }

        if (currentFrame == TOTAL_NUMBER_OF_FRAMES) {
            return;
        }

        if (prevTime == 0) {
            prevTime = timestamp;
        }

        if ((timestamp - prevTime) >= ANIMATION_INTERVAL) {
            prevTime = timestamp;
            setCurrentFrameIndex(currentFrame++);
        }
    }
}
