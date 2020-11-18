package com.github.hanyaeger.api.engine.scenes;

import com.github.hanyaeger.api.engine.annotations.UpdatableProvider;
import com.github.hanyaeger.api.javafx.animationtimer.AnimationTimerFactory;
import com.google.inject.Inject;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import com.github.hanyaeger.api.engine.TimerListProvider;
import com.github.hanyaeger.api.engine.Timer;
import com.github.hanyaeger.api.engine.entities.EntitySpawner;
import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.UpdateDelegator;
import com.github.hanyaeger.api.engine.Updater;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Instantiate a new  {@code DynamicScene}. A {@code DynamicScene} extends a {@link StaticScene}, but adds its
 * own {@code Gameloop}.
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
        return timestamp -> {
            entityCollection.update(timestamp);
        };
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
