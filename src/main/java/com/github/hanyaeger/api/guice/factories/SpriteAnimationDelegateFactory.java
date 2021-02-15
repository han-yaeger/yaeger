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
    @Deprecated
    public SpriteAnimationDelegate create(final ImageView imageView, final int frames) {
        return create(imageView, 1, frames);
    }

    /**
     * Create a new {@code SpriteAnimationDelegate} for the given {@link ImageView} and number of rows and columns.
     * After construction, the spriteIndex will be set to the first frame (top-left image).
     *
     * @param imageView the {@link ImageView} for which the different frames should be created
     * @param rows    the number of rows available
     * @param columns the number of columns available
     * @return an {@link SpriteAnimationDelegate}
     */
    public SpriteAnimationDelegate create(final ImageView imageView, final int rows, final int columns) {
        return new SpriteAnimationDelegate(imageView, rows, columns);
    }
}
