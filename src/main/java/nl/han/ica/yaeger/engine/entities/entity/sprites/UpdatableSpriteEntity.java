package nl.han.ica.yaeger.engine.entities.entity.sprites;

import com.google.inject.Inject;
import com.google.inject.Injector;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.entities.entity.SceneBoundaryCrosser;
import nl.han.ica.yaeger.engine.entities.entity.sprites.delegates.SceneBoundaryCrossingDelegate;
import nl.han.ica.yaeger.engine.entities.entity.Updatable;
import nl.han.ica.yaeger.module.factories.SceneBoundaryCrossingDelegateFactory;

/**
 * An {@code UpdatableSpriteEntity} extends all behaviour of a {@link SpriteEntity}, but also implements the
 * {@link Updatable} Interface.
 */
public abstract class UpdatableSpriteEntity extends SpriteEntity implements Updatable, SceneBoundaryCrosser {

    private long autoCycleInterval = 0;
    private Movement movement;

    private SceneBoundaryCrossingDelegateFactory sceneBoundaryCrossingDelegateFactory;
    private SceneBoundaryCrossingDelegate sceneBoundaryCrossingDelegate;

    /**
     * Create a new SpriteEntity.
     *
     * @param resource        The url of the image file. Relative to the resources folder.
     * @param initialPosition the initial {@link Position} of this Entity
     * @param size            The bounding box of this {@code SpriteEntity}.
     */
    public UpdatableSpriteEntity(final String resource, final Position initialPosition, final Size size) {
        this(resource, initialPosition, size, 1, new Movement(0, 0));
    }

    /**
     * Create a new SpriteEntity.
     *
     * @param resource        The url of the image file. Relative to the resources folder.
     * @param initialPosition the initial {@link Position} of this Entity
     * @param size            The bounding box of this {@code SpriteEntity}.
     * @param frames          The number of frames this Image contains. By default the first frame is loaded.
     */
    public UpdatableSpriteEntity(final String resource, final Position initialPosition, final Size size, int frames) {
        this(resource, initialPosition, size, frames, new Movement(0, 0));
    }

    /**
     * Create a new {@code UpdatableSpriteEntity}.
     *
     * @param resource        The url of the image file. Relative to the resources folder.
     * @param initialPosition the initial {@link Position} of this Entity
     * @param size            The bounding box of this {@code SpriteEntity}.
     * @param frames          The number of frames this Image contains. By default the first frame is loaded.
     * @param initialMovement The movement of this {@code UpdatableSpriteEntity}
     */
    public UpdatableSpriteEntity(final String resource, final Position initialPosition, final Size size, int frames, final Movement initialMovement) {
        super(resource, initialPosition, size, frames);

        movement = initialMovement;
    }

    @Override
    public void update(long timestamp) {
        updateLocation();
        sceneBoundaryCrossingDelegate.checkSceneBoundary(imageView);

        if (spriteAnimationDelegate != null) {
            spriteAnimationDelegate.update(timestamp);
        }
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
     * Set the interval at which the sprite should be automatically cycled
     *
     * @param interval the interval milli-seconds
     */
    protected void setAutoCycle(long interval) {
        this.autoCycleInterval = interval;
    }

    @Override
    public void init(Injector injector) {
        super.init(injector);

        if (getFrames() > 1 && autoCycleInterval != 0) {
            spriteAnimationDelegate.setAutoCycle(autoCycleInterval);
        }
        sceneBoundaryCrossingDelegate = sceneBoundaryCrossingDelegateFactory.create(this);
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

    private void updateLocation() {
        setPosition(getPosition().add(movement.getVector()));
    }

    private boolean hasDirectionChanged(double newDirection) {
        return Double.compare(newDirection, movement.getDirection()) != 0;
    }

    private boolean hasSpeedChanged(double newSpeed) {
        return Double.compare(newSpeed, movement.getSpeed()) != 0;
    }

    @Inject
    public void setSceneBoundaryCrossingDelegateFactory(SceneBoundaryCrossingDelegateFactory sceneBoundaryCrossingDelegateFactory) {
        this.sceneBoundaryCrossingDelegateFactory = sceneBoundaryCrossingDelegateFactory;
    }
}
