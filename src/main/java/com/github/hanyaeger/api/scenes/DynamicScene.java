package com.github.hanyaeger.api.scenes;

import com.github.hanyaeger.core.annotations.UpdatableProvider;
import com.github.hanyaeger.core.scenes.EntitySpawnerListProvider;
import com.github.hanyaeger.core.factories.animationtimer.AnimationTimerFactory;
import com.google.inject.Inject;
import javafx.animation.AnimationTimer;
import com.github.hanyaeger.core.TimerListProvider;
import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.api.entities.EntitySpawner;
import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.core.UpdateDelegator;
import com.github.hanyaeger.core.Updater;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@code DynamicScene} extends a {@link StaticScene}, but adds a Game World Update (GWU). Because of this,
 * the {@code DynamicScene} should be used in all cases where something should move on the Screen.
 * <p>
 * Because of th presence of the GWU, it is possible to add instances of {@link Timer}, by implementing the {@link com.github.hanyaeger.api.TimerContainer}
 * interface, or instances of {@link EntitySpawner}, by implementing the {@link com.github.hanyaeger.api.EntitySpawnerContainer}
 * interface.
 * <p>
 * As with the {@link StaticScene}, the viewable area of a {@code DynamicScene} is exactly the same as the area
 * it occupies. If area should be larger, a {@link ScrollableDynamicScene} should be used.
 */
public abstract class DynamicScene extends StaticScene implements UpdateDelegator, TimerListProvider, EntitySpawnerListProvider {

    private Updater updater;
    private boolean activeGWU;
    private AnimationTimer animator;
    private AnimationTimerFactory animationTimerFactory;
    private final List<Timer> timers = new ArrayList<>();
    private final List<EntitySpawner> spawners = new ArrayList<>();

    /**
     * Pause the Game World Update (GWU) of this {@link YaegerScene}. After the GWU has been paused,
     * all Dynamic Entities and instances of {@link EntitySpawner} and {@link Timer} will no longer
     * receive their GWA, meaning the Game comes to a pause.
     * <p>
     * To resume the Game, call {@link #resume()}.
     */
    public void pause() {
        animator.stop();
        activeGWU = false;
    }

    /**
     * Resume a Game that has been paused (the {@link #pause()}) method has been called. After calling the
     * method GWA will start and all Dynamic Entities and instances of {@link EntitySpawner} and {@link Timer} will
     * again receive a Game World Update.
     */
    public void resume() {
        animator.start();
        activeGWU = true;
    }

    /**
     * Returns whether the Game World Update is active or not. By default, the GWU is active. After
     * calling {@link #pause()} this method will return {@code false}. To reactivate the GWA, use
     * {@link #resume()}.
     *
     * @return a {@code boolean} value that states whether the GWA is active
     */
    public boolean isActiveGWU() {
        return activeGWU;
    }

    @Override
    public void activate() {
        super.activate();
        createGameLoop();
        startGameLoop();
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
        resume();
    }

    private void stopGameLoop() {
        pause();
        animator = null;
    }

    /**
     * Return an {@link Updatable} that delegates the {@link Updatable#update(long)} to
     * {@link com.github.hanyaeger.core.entities.EntityCollection#update(long)}.
     *
     * @return an instance of {@link Updatable}
     */
    @UpdatableProvider
    public Updatable entityCollectionUpdatable() {
        return timestamp ->
                entityCollection.update(timestamp);
    }

    @Override
    public final void update(final long timestamp) {
        getUpdater().update(timestamp);
    }

    private void createGameLoop() {
        animator = this.animationTimerFactory.create(this::update, config.limitGWU());
    }

    @Override
    public Updater getUpdater() {
        return updater;
    }

    @Override
    public List<Timer> getTimers() {
        return timers;
    }

    @Override
    public List<EntitySpawner> getSpawners() {
        return spawners;
    }

    /**
     * Set the {@link AnimationTimerFactory} that should be used.
     *
     * @param animationTimerFactory the {@link AnimationTimerFactory}
     */
    @Inject
    public void setAnimationTimerFactory(final AnimationTimerFactory animationTimerFactory) {
        this.animationTimerFactory = animationTimerFactory;
    }

    /**
     * Set the {@link Updater} that should be used.
     *
     * @param updater the {@link Updater}
     */
    @Inject
    public void setUpdater(final Updater updater) {
        this.updater = updater;
    }
}
