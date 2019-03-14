package nl.han.ica.yaeger.engine.entities.entity.sprites;

public class UpdatableSpriteEntityMover {

    private Movement movement;
    private UpdatableSpriteEntity updatableSpriteEntity;

    public UpdatableSpriteEntityMover(UpdatableSpriteEntity updatableSpriteEntity) {
        this(updatableSpriteEntity,new Movement(0,0));
    }

    public UpdatableSpriteEntityMover(UpdatableSpriteEntity updatableSpriteEntity, Movement movement) {
        this.updatableSpriteEntity = updatableSpriteEntity;
        this.movement = movement;
    }

    /**
     * Change the speed at which this {@code UpdatableSpriteEntity} should move. Using this method will increase or
     * decrease the current speed. If it is required to set the speed to a specific value, use the method
     * {@code setSpeed}.
     *
     * @param change A value large than 1 will mean an increment in speed. A value between 0 and 1 will mean a
     *               decrement in speed.
     */
    protected void changeSpeed(double change) {
        movement.setSpeed(movement.getSpeed() * change);
    }

    /**
     * Set the speed with which this {@link UpdatableSpriteEntity} moves.
     *
     * @param newSpeed the speed
     */
    protected void setSpeed(double newSpeed) {
        if (hasSpeedChanged(newSpeed)) {
            movement.setSpeed(newSpeed);
        }
    }

    /**
     * Set the {@link nl.han.ica.yaeger.engine.entities.entity.sprites.Movement.Direction} in which
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
    protected void setDirection(double newDirection) {
        if (hasDirectionChanged(newDirection)) {
            movement.setDirection(newDirection);
        }
    }

    private boolean hasDirectionChanged(double newDirection) {
        return Double.compare(newDirection, movement.getDirection()) != 0;
    }

    private boolean hasSpeedChanged(double newSpeed) {
        return Double.compare(newSpeed, movement.getSpeed()) != 0;
    }

    public void updateLocation() {
        updatableSpriteEntity.setPosition(updatableSpriteEntity.getPosition().add(movement.getVector()));
    }

    public void addMovement(Movement movement) {
        this.movement = movement;
    }
}
