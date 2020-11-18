package com.github.hanyaeger.api.guice.factories;

import javafx.scene.image.ImageView;
import com.github.hanyaeger.api.engine.entities.entity.sprite.delegates.SpriteAnimationDelegate;

public class SpriteAnimationDelegateFactory {

    /**
     * Create a new {@code SpriteAnimationDelegate} for the given {@link ImageView} and number of frames.
     * After construction, the spriteIndex will be set to the first frame.
     *
     * @param imageView the {@link ImageView} for which the different frames should be created
     * @param frames    the number of frames available
     * @return an {@link SpriteAnimationDelegate}
     */
    public SpriteAnimationDelegate create(final ImageView imageView, final int frames) {
        return new SpriteAnimationDelegate(imageView, frames);
    }
}
