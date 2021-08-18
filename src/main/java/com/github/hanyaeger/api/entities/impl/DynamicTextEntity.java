package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.core.UpdateDelegator;
import com.github.hanyaeger.core.Updater;
import com.github.hanyaeger.core.entities.EntityCollection;
import com.github.hanyaeger.api.entities.ContinuousRotatable;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.entities.motion.*;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.geometry.Point2D;

import java.util.Optional;

/**
 * An {@link DynamicTextEntity} extends all behaviour of a {@link TextEntity}, but also implements the
 * {@link Updatable} Interface.
 */
public abstract class DynamicTextEntity extends TextEntity implements UpdateDelegator, BufferedMovable, ContinuousRotatable {

    private MotionApplier motionApplier;
    private Updater updater;
    private Optional<EntityMotionInitBuffer> buffer;
    private double rotationAngle;

    /**
     * Instantiate a new {@link DynamicTextEntity} for the given {@code initialLocation}.
     *
     * @param initialLocation the initial {@link Coordinate2D} of this {@link DynamicTextEntity}
     */
    protected DynamicTextEntity(final Coordinate2D initialLocation) {
        this(initialLocation, "");
    }

    /**
     * Instantiate a new {@link DynamicTextEntity} for the given {@link Point2D} and textDelegate.
     *
     * @param initialPosition the initial {@link Coordinate2D} of this {@link DynamicTextEntity}
     * @param text            a {@link String} containing the initial textDelegate to be displayed
     */
    protected DynamicTextEntity(final Coordinate2D initialPosition, final String text) {
        super(initialPosition, text);

        buffer = Optional.of(new EntityMotionInitBuffer());
    }

    @Override
    public MotionApplier getMotionApplier() {
        return motionApplier;
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        buffer.ifPresent(entityMotionInitBuffer -> {
            entityMotionInitBuffer.setMotionApplier(motionApplier);
            entityMotionInitBuffer.init(injector);
        });
        buffer = Optional.empty();
    }

    @Override
    public final void update(final long timestamp) {
        getUpdater().update(timestamp);
    }

    @Override
    public void setRotationSpeed(final double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    @Override
    public double getRotationSpeed() {
        return rotationAngle;
    }

    @Override
    public Updater getUpdater() {
        return updater;
    }

    @Override
    public Optional<EntityMotionInitBuffer> getBuffer() {
        return buffer;
    }

    @Override
    public void addToEntityCollection(EntityCollection collection) {
        collection.addDynamicEntity(this);
    }

    @Inject
    @Override
    public void setMotionApplier(final MotionApplier motionApplier) {
        this.motionApplier = motionApplier;
    }

    /**
     * Set the {@link Updater} to be used.
     *
     * @param updater an instance of {@link Updater}
     */
    @Inject
    public void setUpdater(final Updater updater) {
        this.updater = updater;
    }
}
