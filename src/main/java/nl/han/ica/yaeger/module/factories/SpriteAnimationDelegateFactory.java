package nl.han.ica.yaeger.module.factories;

import javafx.scene.image.ImageView;
import nl.han.ica.yaeger.engine.entities.entity.sprites.delegates.SpriteAnimationDelegate;

public class SpriteAnimationDelegateFactory {

    /**
     * Create a new {@code SpriteAnimationDelegate} for the given {@link ImageView} and number of frames.
     * After construction, the spriteIndex will be set to the first frame.
     *
     * @param imageView The {@link ImageView} for which the different frames should be created
     * @param frames    The number of frames available
     * @return an {@link SpriteAnimationDelegate}
     */
    public SpriteAnimationDelegate create(ImageView imageView, int frames) {
        return new SpriteAnimationDelegate(imageView, frames);
    }
}
