package nl.han.ica.yaeger.entities.sprites;

import javafx.geometry.Point2D;
import nl.han.ica.yaeger.entities.enumerations.SceneBorder;
import nl.han.ica.yaeger.entities.interfaces.Updatable;

/**
 * An {@code UpdatableSpriteEntity} extends all behaviour of a {@code SpriteEntity}, but also implements the
 * {@code Updatable} Interface.
 * Because of this, it is required to specify its direction and speed at construction. Furthermore it is also possible
 * to set a rotation speed.
 */
public abstract class UpdatableSpriteEntity extends SpriteEntity implements Updatable {

    private double speed;
    private double direction;

    private Point2D movement;

    /**
     * Create a new SpriteEntity.
     *
     * @param resource          The url of the image file. Relative to the resources folder.
     * @param boundingBoxWidth  The bounding box width of this SpriteEntity.
     * @param boundingBoxHeight The bounding box height of this SpriteEntity.
     */
    public UpdatableSpriteEntity(final String resource, final double boundingBoxWidth, final double boundingBoxHeight) {
        this(resource, boundingBoxWidth, boundingBoxHeight, 1, 0, 0);
    }

    /**
     * Create a new {@code UpdatableSpriteEntity}.
     *
     * @param resource          The url of the image file. Relative to the resources folder.
     * @param boundingBoxWidth  The bounding box width of this SpriteEntity.
     * @param boundingBoxHeight The bounding box height of this SpriteEntity.
     * @param frames            The number of frames this Image contains. By default the first frame is loaded.
     * @param initialDirection  The initialDirection in angles in which the {@code UpdatableSpriteEntity} should move.
     * @param initialSpeed      The initialSpeed in pixels at which the {@code UpdatableSpriteEntity} should move.
     */
    public UpdatableSpriteEntity(final String resource, final double boundingBoxWidth, final double boundingBoxHeight, int frames, double initialDirection, double initialSpeed) {
        this(resource, boundingBoxWidth, boundingBoxHeight, frames, initialDirection, initialSpeed, 0);
    }

    /**
     * Create a new {@code UpdatableSpriteEntity}.
     *
     * @param resource          The url of the image file. Relative to the resources folder.
     * @param boundingBoxWidth  The bounding box width of this SpriteEntity.
     * @param boundingBoxHeight The bounding box height of this SpriteEntity.
     * @param frames            The number of frames this Image contains. By default the first frame is loaded.
     * @param direction         The direction in angles in which the {@code UpdatableSpriteEntity} should move.
     * @param speed             The speed in pixels at which the {@code UpdatableSpriteEntity} should move.
     * @param initialAngle      The initial angle in degrees at which {@code UpdatableSpriteEntity} should be rotated.
     */
    public UpdatableSpriteEntity(final String resource, final double boundingBoxWidth, final double boundingBoxHeight, int frames, double direction, double speed, double initialAngle) {

        super(resource, frames, boundingBoxWidth, boundingBoxHeight, initialAngle);

        this.speed = speed;
        this.direction = direction;

        setMovementVector();

        this.imageView.setRotate(initialAngle);
    }

    @Override
    public void update() {
        updateLocation();
        checkSceneBoundary();
    }

    /**
     * Change the speed at which this {@code UpdatableSpriteEntity} should move. Using this method will increase or
     * decrease the current speed. If it is required to set the speed to a specific value, use the method
     * {@code setSpeed}.
     *
     * @param change A value large than 1 will mean an increment in speed. A value between 0 and 1 will mean a
     *               decrement in speed.
     */
    public void changeSpeed(double change) {
        this.speed = speed * change;
        this.movement.multiply(change);

        setMovementVector();
    }

    /**
     * Zet de snelheid waarmee deze {@link UpdatableSpriteEntity} zich beweegt.
     *
     * @param newSpeed De snelheid.
     */
    protected void setSpeed(double newSpeed) {
        if (hasSpeedChanged(newSpeed)) {
            speed = newSpeed;
            setMovementVector();
        }
    }

    /**
     * Zet de richting waarin deze {@link UpdatableSpriteEntity} zich beweegt. De waarde is in graden, waarbij
     *
     * <ul>
     * <li>0 betekend naar boven</li>
     * <li>90 betekend naar rechts</li>
     * <li>180 betekend naar beneden</li>
     * <li>270 betekend naar links</li>
     * </ul>
     *
     * @param newDirection De richting in graden.
     */
    public void setDirection(double newDirection) {
        if (hasDirectionChanged(newDirection)) {
            this.direction = newDirection;
            setMovementVector();
        }
    }

    /**
     * This method is being called when this {@code UpdatableSpriteEntity} crosses a boundary of the scene.
     * Override this method to add behaviour.
     *
     * @param border The border at which the screen is being crossed.
     */
    protected abstract void notifyBoundaryCrossing(SceneBorder border);

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

    private void setMovementVector() {
        double angleInRadians = Math.toRadians(direction - 90);
        var directionVector = new Point2D(Math.cos(angleInRadians), Math.sin(angleInRadians));

        movement = directionVector.normalize().multiply(speed);
    }

    private void updateLocation() {
        location = location.add(movement);
        imageView.relocate(location.getX(), location.getY());
    }

    private boolean hasDirectionChanged(double newDirection) {
        return Double.compare(newDirection, direction) != 0;
    }

    private boolean hasSpeedChanged(double newSpeed) {
        return Double.compare(newSpeed, speed) != 0;
    }
}
