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
 * Instantiate a new  {@code DynamicScene}. A {@code DynamicScene} extends a {@link StaticScene}, but adds its
 * own Game World Update.
 */
public abstract class DynamicScene extends StaticScene implements UpdateDelegator, TimerListProvider, EntitySpawnerListProvider {

    private Updater updater;
    private AnimationTimer animator;
    private AnimationTimerFactory animationTimerFactory;
    private final List<Timer> timers = new ArrayList<>();
    private final List<EntitySpawner> spawners = new ArrayList<>();

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
        animator.start();
    }

    private void stopGameLoop() {
        animator.stop();
        animator = null;
    }

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

    @Override
    public List<EntitySpawner> getSpawners() {
        return spawners;
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
