package nl.meron.yaeger.engine.entities.entity.sprite;

import com.google.inject.Inject;
import com.google.inject.Injector;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.*;
import nl.meron.yaeger.engine.entities.entity.motion.*;

/**
 * An {@link DynamicSpriteEntity} extends all behaviour of a {@link SpriteEntity}, but also implements the
 * {@link Updatable} Interface.
 */
public abstract class DynamicSpriteEntity extends SpriteEntity implements UpdateDelegator, Moveable, ContinuousRotatable {

    private DefaultMotionApplier motionApplier;
    private long autoCycleInterval = 0;
    private Updater updater;
    private double speed;
    private double direction;
    private double rotationAngle;

    /**
     * Create a new SpriteEntity.
     *
     * @param resource        The url of the image file. Relative to the resources folder.
     * @param initialLocation the initial {@link Location} of this Entity
     * @param size            The bounding box of this {@code SpriteEntity}.
     */
    public DynamicSpriteEntity(final String resource, final Location initialLocation, final Size size) {
        this(resource, initialLocation, size, 1);
    }

    /**
     * Create a new SpriteEntity.
     *
     * @param resource        The url of the image file. Relative to the resources folder.
     * @param initialLocation the initial {@link Location} of this Entity
     * @param size            The bounding box of this {@code SpriteEntity}.
     * @param frames          The number of frames this Image contains. By default the first frame is loaded.
     */
    public DynamicSpriteEntity(final String resource, final Location initialLocation, final Size size, final int frames) {
        super(resource, initialLocation, size, frames);
    }

    /**
     * Set the interval at which the sprite should be automatically cycled
     *
     * @param interval the interval milli-seconds
     */
    protected void setAutoCycle(final long interval) {
        this.autoCycleInterval = interval;
    }

    @Override
    public MotionApplier getMotionApplier() {
        return motionApplier;
    }

    @Override
    public Updater getUpdater() {
        return updater;
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        if (spriteAnimationDelegate != null) {
            updater.addUpdatable(spriteAnimationDelegate);
        }
        if (getFrames() > 1 && autoCycleInterval != 0) {
            spriteAnimationDelegate.setAutoCycle(autoCycleInterval);
        }
        if (motionApplier != null && speed != 0 && direction != 0) {
            motionApplier.setMotionTo(speed, direction);
        }
    }

    @Override
    public void setSpeedTo(final double newSpeed) {
        var directionToSet = direction;

        if (motionApplier != null) {
            directionToSet = getDirection();
        }
        setMotionTo(newSpeed, directionToSet);
    }

    @Override
    public void setDirectionTo(final double newDirection) {
        var speedToSet = speed;

        if (motionApplier != null) {
            speedToSet = getSpeed();
        }
        setMotionTo(speedToSet, newDirection);
    }

    @Override
    public void setMotionTo(final double speed, final double direction) {
        if (motionApplier != null) {
            motionApplier.setMotionTo(speed, direction);
        } else {
            this.speed = speed;
            this.direction = direction;
        }
    }

    @Override
    public void setRotationSpeed(final double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    @Inject
    @Override
    public void setMotionApplier(final DefaultMotionApplier motionApplier) {
        this.motionApplier = motionApplier;
    }

    @Inject
    public void setUpdater(final Updater updater) {
        this.updater = updater;
    }

    @Override
    public double getRotationSpeed() {
        return rotationAngle;
    }


}
