package nl.han.ica.yaeger.gameobjects.sprites;

import javafx.geometry.Point2D;
import nl.han.ica.yaeger.gameobjects.enumerations.SceneBorder;
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
        checkSceneBoundary();
    }

    public void setLocation(double x, double y) {
        location = new Point2D(x, y);
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

    /**
     * This method is being called when this SpriteObject crosses a boundary of the scene.
     * Override this method to init behaviour.
     *
     * @param border Depending on which of the four sides of the boundary is being crossed.
     */
    protected void notifyBoundaryCrossing(SceneBorder border) {
        System.out.println("I have left the screen at: " + border);
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
