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
    private double width;
    private double height;
    private double rotation;
    private double rotationSpeed;

    /**
     * Create a new SpriteObject.
     *
     * @param resource      The url of the image file. Relative to the resources folder.
     * @param x             The x-coordinate at which the SpriteObject should be initially positioned.
     * @param y             The y-coordinate at which the SpriteObject should be initially positioned.
     * @param width         The width of the SpriteObject. The width of the image will be scaled.
     *                      If only the width is given, the SpriteObject will preserve
     *                      the aspect ration of the image and scale the height accordingly.
     * @param height        The height of the SpriteObject. The height of the image will be scaled.
     *                      If only the height is given, the SpriteObject will preserve
     *                      the aspect ration of the image and scale the width accordingly
     * @param direction     The direction in angles in which the SpriteObject should move.
     * @param speed         The speed in pixels at which the SpriteObject should move.
     * @param rotation      The initial rotation angle in degrees of the SpriteObject.
     * @param rotationSpeed The speed at which the SpriteObject should rotate.
     */
    public SpriteObject(final String resource, double x, double y, double width, double height, double direction, double speed, double rotation, double rotationSpeed) {

        this.location = new Point2D(x, y);
        this.movement = calculateMovementVector(Math.toRadians(direction - 90), speed);
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.rotationSpeed = rotationSpeed;

        URL url = getClass().getClassLoader().getResource(resource);
        String stringUrl = url.toString();

        Image image = new Image(stringUrl);

        this.imageView = new ImageView(image);

        if (width != 0d) {
            this.imageView.setPreserveRatio(true);
            this.imageView.setFitWidth(width);
        }

        this.imageView.relocate(x, y);
        this.imageView.setRotate(rotation);
    }

    @Override
    public void update() {
        updateLocation();
        updateRotation();
    }

    private void updateLocation() {
        location = location.add(movement);
        imageView.relocate(location.getX(), location.getY());
    }

    private void updateRotation() {
        rotation = rotation + rotationSpeed;
        imageView.setRotate(rotation);
    }

    @Override
    public Node getGameNode() {
        return imageView;
    }

    private Point2D calculateMovementVector(double angleRadians, double speed) {
        var directionVector = new Point2D(Math.cos(angleRadians), Math.sin(angleRadians));
        Point2D movementVector = directionVector.normalize().multiply(speed);

        return movementVector;
    }
}
