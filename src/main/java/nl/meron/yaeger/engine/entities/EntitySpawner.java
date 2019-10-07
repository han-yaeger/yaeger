package nl.meron.yaeger.engine.entities;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.animation.AnimationTimer;
import nl.meron.yaeger.engine.Destroyable;
import nl.meron.yaeger.engine.Initializable;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.javafx.animationtimer.AnimationTimerFactory;

import java.util.Objects;

/**
 * An {@code EntitiySpawner} is the abstract superclass that should be extended to create an object that
 * spawns a subclass of {@link Entity}.
 */
public abstract class EntitySpawner extends EntitySupplier implements Destroyable, Initializable {

    private transient AnimationTimer timer;
    private transient AnimationTimerFactory animationTimerFactory;

    private long interval;

    /**
     * Create a new {@code EntitySpawner}.
     *
     * @param interval the interval at which instances of {@link Entity} should ne spawned, in milli-seconds
     */
    public EntitySpawner(long interval) {
        this.interval = interval;
    }

    @Override
    public void init(Injector injector) {
        initTimer();
    }

    /**
     * Spawn an {@link Entity}.
     *
     * @param entity the {@link Entity} to be spawned
     */
    protected void spawn(Entity entity) {
        add(entity);
    }

    /**
     * Called by the {@code EntitySpawner} every time a new {@link Entity} should be spawned.
     */
    public abstract void tick();

    @Override
    public void destroy() {

        clear();

        if (timer != null) {
            timer.stop();
        }
    }

    private void initTimer() {
        timer = this.animationTimerFactory.createTimeableAnimationTimer(this::tick, this.interval);

        timer.start();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EntitySpawner entities = (EntitySpawner) o;
        return interval == entities.interval;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), interval);
    }

    @Inject
    public void setAnimationTimerFactory(AnimationTimerFactory animationTimerFactory) {
        this.animationTimerFactory = animationTimerFactory;
    }
}
