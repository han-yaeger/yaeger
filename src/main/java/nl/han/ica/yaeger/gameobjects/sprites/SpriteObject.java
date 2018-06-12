package nl.han.ica.yaeger.gameobjects.sprites;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nl.han.ica.yaeger.gameobjects.GameObject;
import nl.han.ica.yaeger.resourceconsumer.ResourceConsumer;

/**
 * A SpriteObject is a GameObject that is represented by an Image.
 */
public abstract class SpriteObject extends GameObject implements ResourceConsumer {

    protected ImageView imageView;
    protected Point2D location;
    protected double angle;


    /**
     * Create a new SpriteObject for a given Image.
     *
     * @param resource The url of the image file. Relative to the resources folder.
     * @param x        The x-coordinate at which the SpriteObject should be initially positioned.
     * @param y        The y-coordinate at which the SpriteObject should be initially positioned.
     */
    public SpriteObject(final String resource, double x, double y) {
        this(resource, x, y, 0);
    }

    /**
     * Create a new SpriteObject.
     *
     * @param resource The url of the image file. Relative to the resources folder.
     * @param x        The x-coordinate at which the SpriteObject should be initially positioned.
     * @param y        The y-coordinate at which the SpriteObject should be initially positioned.
     * @param angle    The initial angle in degrees of the SpriteObject.
     */
    public SpriteObject(final String resource, double x, double y, double angle) {
        this.location = new Point2D(x, y);
        this.angle = angle;

        String stringUrl = createPathForResource(resource);
        Image image = new Image(stringUrl);

        this.imageView = new ImageView(image);

        imageView.relocate(x, y);
        this.imageView.setRotate(angle);
    }

    /**
     * Scale this SpriteObject to the given width. The SpriteObject will preserve its aspect ratio.
     *
     * @param width The width to which this SpriteObject should be scaled.
     */
    protected void scaleToWidth(double width) {
        this.imageView.setPreserveRatio(true);
        this.imageView.setFitWidth(width);
    }

    protected double getX() {
        return location.getX();
    }

    protected double getY() {
        return location.getY();
    }

    @Override
    public Node getGameNode() {
        return imageView;
    }
}
