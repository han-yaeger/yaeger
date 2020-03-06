package nl.meron.yaeger.engine.scenes;

import com.google.inject.Inject;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import nl.meron.yaeger.engine.WithTimerList;
import nl.meron.yaeger.engine.Timer;
import nl.meron.yaeger.engine.annotations.UpdatableProvider;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.EntitySpawner;
import nl.meron.yaeger.engine.Updatable;
import nl.meron.yaeger.engine.UpdateDelegator;
import nl.meron.yaeger.engine.Updater;
import nl.meron.yaeger.javafx.animationtimer.AnimationTimerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Instantiate a new  {@code DynamicScene}. A {@code DynamicScene} extends a {@link StaticScene}, but adds its
 * own {@code Gameloop}.
 */
public abstract class DynamicScene extends StaticScene implements UpdateDelegator, WithTimerList {

    private Updater updater;
    private AnimationTimer animator;
    private AnimationTimerFactory animationTimerFactory;
    private List<Timer> timers = new ArrayList<>();

    @Override
    public void configure() {
        super.configure();

        createGameLoop();

        setupSpawners();

        startGameLoop();
    }

    protected abstract void setupSpawners();

    @Override
    public void onInputChanged(final Set<KeyCode> input) {
        entityCollection.notifyGameObjectsOfPressedKeys(input);
    }

    /**
     * Register an {@link EntitySpawner}. After being registered, the {@link EntitySpawner} will be responsible for spawning
     * new instances of {@link Entity}.
     *
     * @param spawner the {@link EntitySpawner} to be registered
     */
    protected void registerSpawner(final EntitySpawner spawner) {
        injector.injectMembers(spawner);
        spawner.init(injector);

        entityCollection.registerSupplier(spawner);
    }

    @Override
    public void destroy() {
        stopGameLoop();
        updater.clear();
        getEntitySupplier().clear();
        entityCollection.clear();
        setAnimationTimerFactory(null);
        setUpdater(null);
        super.destroy();
    }

    private void startGameLoop() {
        animator.start();
    }

    private void stopGameLoop() {
        animator.stop();
        animator = null;
    }

    @UpdatableProvider
    public Updatable entityCollectionUpdatable() {
        return timestamp -> {
            entityCollection.update(timestamp);
        };
    }

    private void createGameLoop() {
        animator = this.animationTimerFactory.create(this::update);
    }

    @Override
    public Updater getUpdater() {
        return updater;
    }

    @Override
    public List<Timer> getTimers() {
        return timers;
    }

    @Inject
    public void setAnimationTimerFactory(final AnimationTimerFactory animationTimerFactory) {
        this.animationTimerFactory = animationTimerFactory;
    }

    @Inject
    public void setUpdater(final Updater updater) {
        this.updater = updater;
    }
}
