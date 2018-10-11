package nl.han.ica.yaeger.engine.entities.entity.sprites;

import javafx.geometry.Point2D;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.scene.SceneBorder;
import nl.han.ica.yaeger.engine.entities.entity.Updatable;

/**
 * An {@code UpdatableSpriteEntity} extends all behaviour of a {@link SpriteEntity}, but also implements the
 * {@link Updatable} Interface.
 */
public abstract class UpdatableSpriteEntity extends SpriteEntity implements Updatable {

    private Movement movement;
    private Point2D movementVector;

    /**
     * Create a new SpriteEntity.
     *
     * @param resource The url of the image file. Relative to the resources folder.
     * @param position the initial {@link Position} of this Entity
     * @param size     The bounding box of this {@code SpriteEntity}.
     */
    public UpdatableSpriteEntity(final String resource, final Position position, final Size size) {
        this(resource, position, size, 1, new Movement(0, 0));
    }

    /**
     * Create a new {@code UpdatableSpriteEntity}.
     *
     * @param resource The url of the image file. Relative to the resources folder.
     * @param position the initial {@link Position} of this Entity
     * @param size     The bounding box of this {@code SpriteEntity}.
     * @param frames   The number of frames this Image contains. By default the first frame is loaded.
     * @param movement The movement of this {@code UpdatableSpriteEntity}
     */
    public UpdatableSpriteEntity(final String resource, final Position position, final Size size, int frames, final Movement movement) {
        super(resource, position, size, frames);

        this.movement = movement;

        setMovementVector();
    }

    @Override
    public void update(long timestamp) {
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
        movement.setSpeed(movement.getSpeed() * change);
        this.movementVector.multiply(change);

        setMovementVector();
    }

    /**
     * Set the speed with which this {@link UpdatableSpriteEntity} moves.
     *
     * @param newSpeed the speed
     */
    protected void setSpeed(double newSpeed) {
        if (hasSpeedChanged(newSpeed)) {
            movement.setSpeed(newSpeed);
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
    protected void setDirection(double newDirection) {
        if (hasDirectionChanged(newDirection)) {
            movement.setDirection(newDirection);
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
        double angleInRadians = Math.toRadians(movement.getDirection() - 90);
        var directionVector = new Point2D(Math.cos(angleInRadians), Math.sin(angleInRadians));

        movementVector = directionVector.normalize().multiply(movement.getSpeed());
    }

    private void updateLocation() {
        positionVector = positionVector.add(movementVector);
        imageView.relocate(positionVector.getX(), positionVector.getY());
    }

    private boolean hasDirectionChanged(double newDirection) {
        return Double.compare(newDirection, movement.getDirection()) != 0;
    }

    private boolean hasSpeedChanged(double newSpeed) {
        return Double.compare(newSpeed, movement.getSpeed()) != 0;
    }
}
