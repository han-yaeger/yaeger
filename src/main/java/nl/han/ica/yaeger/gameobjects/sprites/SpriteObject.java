package nl.han.ica.yaeger.gameobjects.sprites;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nl.han.ica.yaeger.gameobjects.GameObject;
import nl.han.ica.yaeger.gameobjects.sprites.delegates.SpriteAnimationDelegate;
import nl.han.ica.yaeger.resourceconsumer.ResourceConsumer;

/**
 * A {@code SpriteObject} is a {@code GameObject} that is represented by an Image.
 */
public abstract class SpriteObject extends GameObject implements ResourceConsumer {

    protected ImageView imageView;
    protected Point2D location;
    protected double angle;

    private SpriteAnimationDelegate spriteAnimationDelegate;

    /**
     * Create a new {@code SpriteObject} for a given Image.
     *
     * @param resource The url of the image file. Relative to the resources folder.
     * @param x        The x-coordinate at which the SpriteObject should be initially positioned.
     * @param y        The y-coordinate at which the SpriteObject should be initially positioned.
     */
    public SpriteObject(final String resource, final double x, final double y) {
        this(resource, 1, x, y);
    }

    /**
     * Create a new {@code SpriteObject} for a given Image.
     *
     * @param resource The url of the image file. Relative to the resources folder.
     * @param frames   The number of frames this Image contains. By default the first frame is loaded.
     * @param x        The x-coordinate at which the SpriteObject should be initially positioned.
     * @param y        The y-coordinate at which the SpriteObject should be initially positioned.
     */
    public SpriteObject(final String resource, final int frames, final double x, final double y) {
        this(resource, frames, x, y, 0);
    }

    /**
     * Create a new {@code SpriteObject}.
     *
     * @param resource The url of the image file. Relative to the resources folder.
     * @param frames   The number of frames this Image contains. By default the first frame is loaded.
     * @param x        The x-coordinate at which the {@code SpriteObject} should be initially positioned.
     * @param y        The y-coordinate at which the {@code SpriteObject} should be initially positioned.
     * @param angle    The initial angle in degrees of the {@code SpriteObject}.
     */
    public SpriteObject(final String resource, final int frames, final double x, final double y, final double angle) {

        setLocation(x, y);
        this.angle = angle;

        String stringUrl = createPathForResource(resource);
        Image image = new Image(stringUrl);

        this.imageView = new ImageView(image);

        this.imageView.relocate(x, y);

        if (frames > 1) {
            spriteAnimationDelegate = new SpriteAnimationDelegate(imageView, frames);
        }

        this.imageView.setRotate(angle);
    }

    /**
     * Set the location of this {@code SpriteObject}
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public void setLocation(double x, double y) {
        location = new Point2D(x, y);
    }

    /**
     * Set the current frame index of the Sprite image.
     *
     * @param index The index that should be shown. The index is zero based and the frame modulo index will be shown.
     */
    public void setCurrentFrameIndex(int index) {
        spriteAnimationDelegate.setSpriteIndex(imageView, index);
    }

    /**
     * Scale this {@code SpriteObject} to the given width. The {@code SpriteObject} will preserve its aspect ratio.
     *
     * @param width The width to which this {@code SpriteObject} should be scaled.
     */
    protected void scaleToWidth(double width) {
        this.imageView.setPreserveRatio(true);
        this.imageView.setFitWidth(width);
    }

    /**
     * Return the x-coordinate of this {@code SpriteObject}.
     *
     * @return the x-coordinate
     */
    protected double getX() {
        return location.getX();
    }

    /**
     * Return the y-coordinate of this {@code SpriteObject}.
     *
     * @return the y-coordinate
     */
    protected double getY() {
        return location.getY();
    }

    @Override
    public Node getGameNode() {
        return imageView;
    }
}
