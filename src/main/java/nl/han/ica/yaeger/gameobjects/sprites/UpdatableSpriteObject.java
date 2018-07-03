package nl.han.ica.yaeger.gameobjects.sprites;

import javafx.geometry.Point2D;
import nl.han.ica.yaeger.gameobjects.enumerations.SceneBorder;
import nl.han.ica.yaeger.gameobjects.interfaces.Updatable;

/**
 * An {@code UpdatableSpriteObject} extends all behaviour of a {@code SpriteObject}, but also implements the
 * {@code Updatable} Interface.
 * Because of this, it is required to specify its direction and speed at construction. Furthermore it is also possible
 * to set a rotation speed.
 */
public class UpdatableSpriteObject extends SpriteObject implements Updatable {

    private Point2D movement;
    private double rotationSpeed;

    /**
     * Create a new SpriteObject.
     *
     * @param resource The url of the image file. Relative to the resources folder.
     * @param x        The x-coordinate at which the {@code UpdatableSpriteObject} should be initially positioned.
     * @param y        The y-coordinate at which the {@code UpdatableSpriteObject} should be initially positioned.
     */
    public UpdatableSpriteObject(final String resource, double x, double y) {
        this(resource, x, y, 1, 0, 0);
    }

    /**
     * Create a new {@code UpdatableSpriteObject}.
     *
     * @param resource         The url of the image file. Relative to the resources folder.
     * @param x                The x-coordinate at which the {@code UpdatableSpriteObject} should be initially positioned.
     * @param y                The y-coordinate at which the {@code UpdatableSpriteObject} should be initially positioned.
     * @param frames           The number of frames this Image contains. By default the first frame is loaded.
     * @param initialDirection The initialDirection in angles in which the {@code UpdatableSpriteObject} should move.
     * @param initialSpeed     The initialSpeed in pixels at which the {@code UpdatableSpriteObject} should move.
     */
    public UpdatableSpriteObject(final String resource, double x, double y, int frames, double initialDirection, double initialSpeed) {
        this(resource, x, y, frames, initialDirection, initialSpeed, 0);
    }

    /**
     * Create a new {@code UpdatableSpriteObject}.
     *
     * @param resource         The url of the image file. Relative to the resources folder.
     * @param x                The x-coordinate at which the {@code UpdatableSpriteObject} should be initially positioned.
     * @param y                The y-coordinate at which the {@code UpdatableSpriteObject} should be initially positioned.
     * @param frames           The number of frames this Image contains. By default the first frame is loaded.
     * @param initialDirection The direction in angles in which the {@code UpdatableSpriteObject} should move.
     * @param initialSpeed     The speed in pixels at which the {@code UpdatableSpriteObject} should move.
     * @param initialAngle     The initial angle in degrees at which {@code UpdatableSpriteObject} should be rotated.
     */
    public UpdatableSpriteObject(final String resource, double x, double y, int frames, double initialDirection, double initialSpeed, double initialAngle) {
        this(resource, x, y, frames, initialDirection, initialSpeed, initialAngle, 0);
    }

    /**
     * Create a new {@code UpdatableSpriteObject}.
     *
     * @param resource      The url of the image file. Relative to the resources folder.
     * @param x             The x-coordinate at which the {@code UpdatableSpriteObject} should be initially positioned.
     * @param y             The y-coordinate at which the {@code UpdatableSpriteObject} should be initially positioned.
     * @param frames        The number of frames this Image contains. By default the first frame is loaded.
     * @param direction     The direction in angles in which the {@code UpdatableSpriteObject} should move.
     * @param speed         The speed in pixels at which the {@code UpdatableSpriteObject} should move.
     * @param initialAngle  The initial angle in degrees at which {@code UpdatableSpriteObject} should be rotated.
     * @param rotationSpeed The speed at which the SpriteObject should rotate.
     */
    public UpdatableSpriteObject(final String resource, double x, double y, int frames, double direction, double speed, double initialAngle, double rotationSpeed) {

        super(resource, frames, x, y, initialAngle);

        this.movement = calculateMovementVector(Math.toRadians(direction - 90), speed);
        this.rotationSpeed = rotationSpeed;

        this.imageView.relocate(x, y);
        this.imageView.setRotate(initialAngle);
    }

    @Override
    public void update() {
        updateLocation();
        updateRotation();
        checkSceneBoundary();
    }

    /**
     * Alter the speed of this {@code UpdatableSpriteObject}.
     *
     * @param change
     */

    public void alterSpeed(double change) {
        this.movement.multiply(change);
    }

    /**
     * This method is being called when this {@code UpdatableSpriteObject} crosses a boundary of the scene.
     * Override this method to init behaviour.
     *
     * @param border Depending on which of the four sides of the boundary is being crossed.
     */
    protected void notifyBoundaryCrossing(SceneBorder border) {
        System.out.println("I have left the screen at: " + border);
    }

    private void checkSceneBoundary() {
        var x = imageView.getLayoutX();
        var y = imageView.getLayoutY();
        var width = imageView.getLayoutBounds().getWidth();
        var height = imageView.getLayoutBounds().getHeight();
        var rightSideXCoordinate = x + width;
        var bottomYCoordinate = y + height;
        var screenBottom = imageView.getScene().getHeight();
        var screenRight = imageView.getScene().getWidth();

        if (rightSideXCoordinate <= 0) {
            notifyBoundaryCrossing(SceneBorder.LEFT);
        } else if (bottomYCoordinate <= 0) {
            notifyBoundaryCrossing(SceneBorder.TOP);
        } else if (y >= screenBottom) {
            notifyBoundaryCrossing(SceneBorder.BOTTOM);
        } else if (x >= screenRight) {
            notifyBoundaryCrossing(SceneBorder.RIGHT);
        }
    }

    private Point2D calculateMovementVector(double angleRadians, double speed) {
        var directionVector = new Point2D(Math.cos(angleRadians), Math.sin(angleRadians));

        return directionVector.normalize().multiply(speed);
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
