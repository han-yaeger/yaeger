package com.github.hanyaeger.core.factories.image;

import javafx.scene.image.ImageView;
import com.github.hanyaeger.core.entities.SpriteAnimationDelegate;

/**
 * A {@code SpriteAnimationDelegateFactory} is responsible for creating an instance of a {@link SpriteAnimationDelegate}.
 */
public class SpriteAnimationDelegateFactory {

    /**
     * Create a new {@code SpriteAnimationDelegate} for the given {@link ImageView} and number of rows and columns.
     * After construction, the spriteIndex will be set to the first frame (top-left image).
     *
     * @param imageView the {@link ImageView} for which the different frames should be created
     * @param rows      the number of rows available
     * @param columns   the number of columns available
     * @return an {@link SpriteAnimationDelegate}
     */
    public SpriteAnimationDelegate create(final ImageView imageView, final int rows, final int columns) {
        return new SpriteAnimationDelegate(imageView, rows, columns);
    }
}
