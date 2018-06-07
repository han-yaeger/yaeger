package nl.han.ica.yaeger.gameobjects.Sprites;

import javafx.geometry.Point2D;
import nl.han.ica.yaeger.gameobjects.interfaces.Updatable;

/**
 * An UpdatableSpriteObject extends all behaviour of a SpriteObject, but also implements the Updatable Interface.
 * Because of this, it is required to specify its direction and speed at construction. Furthermore it is also possible
 * to set a rotation speed.
 */
public class UpdatableSpriteObject extends SpriteObject implements Updatable {

    private Point2D movement;
    private double rotationSpeed;

    /**
     * Create a new SpriteObject.
     *
     * @param resource  The url of the image file. Relative to the resources folder.
     * @param x         The x-coordinate at which the SpriteObject should be initially positioned.
     * @param y         The y-coordinate at which the SpriteObject should be initially positioned.
     * @param direction The direction in angles in which the SpriteObject should move.
     * @param speed     The speed in pixels at which the SpriteObject should move.
     */
    public UpdatableSpriteObject(final String resource, double x, double y, double direction, double speed) {
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
    public UpdatableSpriteObject(final String resource, double x, double y, double direction, double speed, double angle) {
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
    public UpdatableSpriteObject(final String resource, double x, double y, double direction, double speed, double angle, double rotationSpeed) {

        super(resource, x, y, angle);

        this.movement = calculateMovementVector(Math.toRadians(direction - 90), speed);
        this.rotationSpeed = rotationSpeed;

        this.imageView.relocate(x, y);
        this.imageView.setRotate(angle);
    }

    @Override
    public void update() {
        updateLocation();
        updateRotation();
    }

    private Point2D calculateMovementVector(double angleRadians, double speed) {
        var directionVector = new Point2D(Math.cos(angleRadians), Math.sin(angleRadians));
        Point2D movementVector = directionVector.normalize().multiply(speed);

        return movementVector;
    }

    private void updateLocation() {
        location = location.add(movement);
        imageView.relocate(location.getX(), location.getY());
    }

    private void updateRotation() {
        angle = angle + rotationSpeed;
        imageView.setRotate(angle);
    }
}
