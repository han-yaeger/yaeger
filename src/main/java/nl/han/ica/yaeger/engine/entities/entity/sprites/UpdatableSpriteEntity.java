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
    private UpdatableSpriteEntityMover mover;

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
        this(resource, initialPosition, size, 1);
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
        this(resource, initialPosition, size, frames, new MovementVector(0, 0));
    }

    /**
     * Create a new {@code UpdatableSpriteEntity}.
     *
     * @param resource        The url of the image file. Relative to the resources folder.
     * @param initialPosition the initial {@link Position} of this Entity
     * @param size            The bounding box of this {@code SpriteEntity}.
     * @param frames          The number of frames this Image contains. By default the first frame is loaded.
     * @param initialMovementVector The movement of this {@code UpdatableSpriteEntity}
     */
    public UpdatableSpriteEntity(final String resource, final Position initialPosition, final Size size, int frames, final MovementVector initialMovementVector) {
        super(resource, initialPosition, size, frames);

        mover = new UpdatableSpriteEntityMover(this, initialMovementVector);
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
     * Delegate work to {@link UpdatableSpriteEntityMover}.
     *
     * @param change A value large than 1 will mean an increment in speed. A value between 0 and 1 will mean a
     *               decrement in speed.
     */
    protected void changeSpeed(double change) {
        mover.changeSpeed(change);
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
     * Delegate work to {@link UpdatableSpriteEntityMover}.
     *
     * @param newSpeed the speed
     */
    protected void setSpeed(double newSpeed) {
        mover.setSpeed(newSpeed);
    }

    /**
     * Delegate work to {@link UpdatableSpriteEntityMover}.
     */
    protected void setDirection(double newDirection) {
        mover.setDirection(newDirection);
    }

    private void updateLocation() {
        mover.updateLocation();
    }

    @Inject
    public void setSceneBoundaryCrossingDelegateFactory(SceneBoundaryCrossingDelegateFactory sceneBoundaryCrossingDelegateFactory) {
        this.sceneBoundaryCrossingDelegateFactory = sceneBoundaryCrossingDelegateFactory;
    }
}
