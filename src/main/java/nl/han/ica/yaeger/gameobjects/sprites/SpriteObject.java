package nl.han.ica.yaeger.gameobjects.sprites;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nl.han.ica.yaeger.gameobjects.GameObject;
import nl.han.ica.yaeger.resourceconsumer.ResourceConsumer;

/**
 * A {@code SpriteObject} is a {@code GameObject} that is represented by an Image.
 */
public abstract class SpriteObject extends GameObject implements ResourceConsumer {

    protected ImageView imageView;
    protected Point2D location;
    protected double angle;


    /**
     * Create a new {@code SpriteObject} for a given Image.
     *
     * @param resource The url of the image file. Relative to the resources folder.
     * @param x        The x-coordinate at which the SpriteObject should be initially positioned.
     * @param y        The y-coordinate at which the SpriteObject should be initially positioned.
     */
    public SpriteObject(final String resource, double x, double y) {
        this(resource, x, y, 0);
    }

    /**
     * Create a new {@code SpriteObject}.
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
