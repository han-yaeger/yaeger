package nl.han.ica.yaeger.engine.entities.entity.sprites.delegates;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * A {@code SpriteAnimationDelegate} holds all responsibility related to Sprites that contain multiple images.
 */
public class SpriteAnimationDelegate {

    private List<Rectangle2D> viewports = new ArrayList<>();

    /**
     * Create a new {@code SpriteAnimationDelegate} for the given {@code ImageView} and number of frames.
     * After construction, the spriteIndex will be set to the first frame.
     *
     * @param imageView The {@code ImageView} for which the different frames should be created.
     * @param frames    The number of frames available.
     */
    public SpriteAnimationDelegate(ImageView imageView, int frames) {
        createViewPorts(imageView, frames);
        setSpriteIndex(imageView, 0);
    }

    /**
     * Set the index of the sprite. Since de modulus (mod frames) is used, this can be an unbounded integer.
     *
     * @param imageView The {@link ImageView} for which the index should be set.
     * @param index     The index to select. This index will be applied modulo the total number
     *                  of frames.
     */
    public void setSpriteIndex(ImageView imageView, int index) {
        var modulus = index % viewports.size();
        imageView.setViewport(viewports.get(modulus));
    }

    private void createViewPorts(ImageView imageView, int frames) {
        var frameWidth = getFrameWidth(imageView, frames);
        var frameHeight = imageView.getImage().getHeight();

        IntStream.range(0, frames).forEach(frame -> addViewPort(frame, frameWidth, frameHeight));
    }

    private void addViewPort(int frame, double frameWidth, double frameHeight) {
        viewports.add(new Rectangle2D(frame * frameWidth, 0, frameWidth, frameHeight));
    }

    private double getFrameWidth(ImageView imageView, int frames) {
        return imageView.getImage().getWidth() / frames;
    }
}
