package nl.han.ica.yaeger.gameobjects;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public abstract class SpriteObject extends GameObject {

    protected ImageView imageView;

    private Point2D location;
    private Point2D movement;
    private double angle;
    private double rotationSpeed;


    /**
     * Create a new SpriteObject.
     *
     * @param resource The url of the image file. Relative to the resources folder.
     * @param x        The x-coordinate at which the SpriteObject should be initially positioned.
     * @param y        The y-coordinate at which the SpriteObject should be initially positioned.
     */
    public SpriteObject(final String resource, double x, double y) {
        this(resource, x, y, 0, 0);
    }

    /**
     * Create a new SpriteObject.
     *
     * @param resource  The url of the image file. Relative to the resources folder.
     * @param x         The x-coordinate at which the SpriteObject should be initially positioned.
     * @param y         The y-coordinate at which the SpriteObject should be initially positioned.
     * @param direction The direction in angles in which the SpriteObject should move.
     * @param speed     The speed in pixels at which the SpriteObject should move.
     */
    public SpriteObject(final String resource, double x, double y, double direction, double speed) {
        this(resource, x, y, direction, speed, 0);
    }

    /**
     * Create a new SpriteObject.
     *
     * @param resource  The url of the image file. Relative to the resources folder.
     * @param x         The x-coordinate at which the SpriteObject should be initially positioned.
     * @param y         The y-coordinate at which the SpriteObject should be initially positioned.
     * @param direction The direction in angles in which the SpriteObject should move.
     * @param speed     The speed in pixels at which the SpriteObject should move.
     * @param angle     The initial angle in degrees of the SpriteObject.
     */
    public SpriteObject(final String resource, double x, double y, double direction, double speed, double angle) {
        this(resource, x, y, direction, speed, angle, 0);
    }

    /**
     * Create a new SpriteObject.
     *
     * @param resource      The url of the image file. Relative to the resources folder.
     * @param x             The x-coordinate at which the SpriteObject should be initially positioned.
     * @param y             The y-coordinate at which the SpriteObject should be initially positioned.
     * @param direction     The direction in angles in which the SpriteObject should move.
     * @param speed         The speed in pixels at which the SpriteObject should move.
     * @param angle         The initial angle in degrees of the SpriteObject.
     * @param rotationSpeed The speed at which the SpriteObject should rotate.
     */
    public SpriteObject(final String resource, double x, double y, double direction, double speed, double angle, double rotationSpeed) {

        this.location = new Point2D(x, y);
        this.movement = calculateMovementVector(Math.toRadians(direction - 90), speed);
        this.angle = angle;
        this.rotationSpeed = rotationSpeed;

        URL url = getClass().getClassLoader().getResource(resource);
        String stringUrl = url.toString();

        Image image = new Image(stringUrl);

        this.imageView = new ImageView(image);

        this.imageView.relocate(x, y);
        this.imageView.setRotate(angle);
    }

    /**
     * Scale this SpriteObject to the given width. The SpriteObject will preserve its aspect ratio.
     *
     * @param width The width to which this SpriteObject should be scaled.
     */
    public void scaleToWidth(double width) {
        this.imageView.setPreserveRatio(true);
        this.imageView.setFitWidth(width);
    }

    @Override
    public void update() {
        updateLocation();
        updateRotation();
    }

    @Override
    public Node getGameNode() {
        return imageView;
    }

    private void updateLocation() {
        location = location.add(movement);
        imageView.relocate(location.getX(), location.getY());
    }

    private void updateRotation() {
        angle = angle + rotationSpeed;
        imageView.setRotate(angle);
    }

    private Point2D calculateMovementVector(double angleRadians, double speed) {
        var directionVector = new Point2D(Math.cos(angleRadians), Math.sin(angleRadians));
        Point2D movementVector = directionVector.normalize().multiply(speed);

        return movementVector;
    }
}
