package nl.meron.yaeger.engine.entities.entity.sprites.movement;

import nl.meron.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;

public class UpdatableSpriteEntityMover {

    private MovementVector movementVector;
    private UpdatableSpriteEntity updatableSpriteEntity;

    public UpdatableSpriteEntityMover(UpdatableSpriteEntity updatableSpriteEntity) {
        this(updatableSpriteEntity, new MovementVector(0, 0));
    }

    public UpdatableSpriteEntityMover(UpdatableSpriteEntity updatableSpriteEntity, MovementVector movementVector) {
        this.updatableSpriteEntity = updatableSpriteEntity;
        this.movementVector = movementVector;
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
        movementVector.setSpeed(movementVector.getSpeed() * change);
    }

    /**
     * Set the speed with which this {@link UpdatableSpriteEntity} moves.
     *
     * @param newSpeed the speed
     */
    public void setSpeed(double newSpeed) {
        if (hasSpeedChanged(newSpeed)) {
            movementVector.setSpeed(newSpeed);
        }
    }

    /**
     * Set the {@link MovementVector.Direction} in which
     * this {@link UpdatableSpriteEntity} should move. This value is in degrees, where
     *
     * <ul>
     * <li>0 means up</li>
     * <li>90 means to the right</li>
     * <li>180 means down</li>
     * <li>270 to the left</li>
     * </ul>
     * <p>
     *
     * @param newDirection the direction in degrees
     */
    public void setDirection(double newDirection) {
        if (hasDirectionChanged(newDirection)) {
            movementVector.setDirection(newDirection);
        }
    }

    /**
     * Get the direction in which this {@code Entity} is moving
     *
     * @return a {@code double} representing the direction
     */
    public double getDirection(){
        return movementVector.getDirection();
    }

    private boolean hasDirectionChanged(double newDirection) {
        return Double.compare(newDirection, movementVector.getDirection()) != 0;
    }

    private boolean hasSpeedChanged(double newSpeed) {
        return Double.compare(newSpeed, movementVector.getSpeed()) != 0;
    }

    public void updateLocation() {
        updatableSpriteEntity.setPoint(updatableSpriteEntity.getAnchorPoint().add(movementVector.getVector()));
    }

    public void addMovement(MovementVector movementVector) {
        this.movementVector = movementVector;
    }
}
