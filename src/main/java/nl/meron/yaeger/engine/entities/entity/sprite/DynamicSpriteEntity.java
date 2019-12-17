package nl.meron.yaeger.engine.entities.entity.sprite;

import com.google.inject.Inject;
import com.google.inject.Injector;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.Updatable;
import nl.meron.yaeger.engine.entities.entity.UpdateDelegator;
import nl.meron.yaeger.engine.entities.entity.Updater;
import nl.meron.yaeger.engine.entities.entity.motion.*;

/**
 * An {@link DynamicSpriteEntity} extends all behaviour of a {@link SpriteEntity}, but also implements the
 * {@link Updatable} Interface.
 */
public abstract class DynamicSpriteEntity extends SpriteEntity implements UpdateDelegator, Moveable {

    private DefaultMotionApplier motionApplier;
    private long autoCycleInterval = 0;
    private Updater updater;

    /**
     * Create a new SpriteEntity.
     *
     * @param resource     The url of the image file. Relative to the resources folder.
     * @param initialPoint the initial {@link Point} of this Entity
     * @param size         The bounding box of this {@code SpriteEntity}.
     */
    public DynamicSpriteEntity(final String resource, final Point initialPoint, final Size size) {
        this(resource, initialPoint, size, 1);
    }

    /**
     * Create a new SpriteEntity.
     *
     * @param resource     The url of the image file. Relative to the resources folder.
     * @param initialPoint the initial {@link Point} of this Entity
     * @param size         The bounding box of this {@code SpriteEntity}.
     * @param frames       The number of frames this Image contains. By default the first frame is loaded.
     */
    public DynamicSpriteEntity(final String resource, final Point initialPoint, final Size size, int frames) {
        super(resource, initialPoint, size, frames);
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
    public MotionApplier getMotionApplier() {
        return motionApplier;
    }

    @Override
    public Updater getUpdater() {
        return updater;
    }

    @Override
    public void init(Injector injector) {
        super.init(injector);

        if (spriteAnimationDelegate != null) {
            updater.addUpdatable(spriteAnimationDelegate);
        }
        if (getFrames() > 1 && autoCycleInterval != 0) {
            spriteAnimationDelegate.setAutoCycle(autoCycleInterval);
        }
    }

    @Inject
    @Override
    public void setMotionApplier(DefaultMotionApplier motionApplier) {
        this.motionApplier = motionApplier;
    }

    @Inject
    public void setUpdater(Updater updater) {
        this.updater = updater;
    }
}
