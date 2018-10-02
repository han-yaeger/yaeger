package nl.han.ica.yaeger.engine.entities.sprites;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nl.han.ica.yaeger.engine.entities.Entity;
import nl.han.ica.yaeger.engine.entities.sprites.delegates.SpriteAnimationDelegate;
import nl.han.ica.yaeger.engine.resourceconsumer.ResourceConsumer;

/**
 * A {@code SpriteEntity} is a {@code Entity} that is represented by an Image.
 */
public abstract class SpriteEntity extends Entity implements ResourceConsumer {

    protected ImageView imageView;
    protected Point2D location = new Point2D(0, 0);
    protected double angle;

    private SpriteAnimationDelegate spriteAnimationDelegate;

    /**
     * Create a new {@code SpriteEntity} for a given Image.
     *
     * @param resource          The url of the image file. Relative to the resources folder.
     * @param boundingBoxWidth  The bounding box width of this SpriteEntity.
     * @param boundingBoxHeight The bounding box height of this SpriteEntity.
     */
    public SpriteEntity(final String resource, final double boundingBoxWidth, final double boundingBoxHeight) {
        this(resource, 1, boundingBoxWidth, boundingBoxHeight);
    }

    /**
     * Create a new {@code SpriteEntity} for a given Image.
     *
     * @param resource          The url of the image file. Relative to the resources folder.
     * @param frames            The number of frames this Image contains. By default the first frame is loaded.
     * @param boundingBoxWidth  The bounding box width of this SpriteEntity.
     * @param boundingBoxHeight The bounding box height of this SpriteEntity.
     */
    public SpriteEntity(final String resource, final int frames, final double boundingBoxWidth, final double boundingBoxHeight) {
        this(resource, frames, boundingBoxWidth, boundingBoxHeight, 0);
    }

    /**
     * Create a new {@code SpriteEntity}.
     *
     * @param resource          The url of the image file. Relative to the resources folder.
     * @param frames            The number of frames this Image contains. By default the first frame is loaded.
     * @param boundingBoxWidth  The bounding box width of this SpriteEntity.
     * @param boundingBoxHeight The bounding box height of this SpriteEntity.
     * @param angle             The initial angle in degrees of the {@code SpriteEntity}.
     */
    public SpriteEntity(final String resource, final int frames, final double boundingBoxWidth, final double boundingBoxHeight, final double angle) {

        this.angle = angle;

        var stringUrl = createPathForResource(resource);
        var image = new Image(stringUrl, boundingBoxWidth, boundingBoxHeight, true, false);
        imageView = new ImageView(image);
        imageView.setManaged(false);

        if (frames > 1) {
            spriteAnimationDelegate = new SpriteAnimationDelegate(imageView, frames);
        }

        imageView.setRotate(angle);
    }

    /**
     * Set the location of this {@code SpriteEntity}
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
     * Return the x-coordinate of this {@code SpriteEntity}.
     *
     * @return the x-coordinate
     */
    protected double getX() {
        return location.getX();
    }

    /**
     * Return the y-coordinate of this {@code SpriteEntity}.
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

    @Override
    public void remove() {
        imageView.setImage(null);
        imageView.setVisible(false);
        super.remove();
    }
}
