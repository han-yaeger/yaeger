package com.github.hanyaeger.api.engine.entities.entity.shape.text;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.UpdateDelegator;
import com.github.hanyaeger.api.engine.Updater;
import com.github.hanyaeger.api.engine.entities.EntityCollection;
import com.github.hanyaeger.api.engine.entities.entity.ContinuousRotatable;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.motion.*;
import com.github.hanyaeger.api.guice.factories.MotionApplierFactory;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.geometry.Point2D;

import java.util.Optional;

/**
 * An {@link DynamicTextEntity} extends all behaviour of a {@link TextEntity}, but also implements the
 * {@link Updatable} Interface.
 */
public abstract class DynamicTextEntity extends TextEntity implements UpdateDelegator, BufferedMoveable, ContinuousRotatable {

    private MotionApplier motionApplier;
    private Updater updater;
    private Optional<EntityMotionInitBuffer> buffer;
    private double rotationAngle;

    /**
     * Instantiate a new {@link DynamicTextEntity} for the given {@link Coordinate2D}.
     *
     * @param initialPosition the initial {@link Coordinate2D} of this {@link DynamicTextEntity}
     */
    public DynamicTextEntity(final Coordinate2D initialPosition) {
        this(initialPosition, "");
    }

    /**
     * Instantiate a new {@link DynamicTextEntity} for the given {@link Point2D} and textDelegate.
     *
     * @param initialPosition the initial {@link Coordinate2D} of this {@link DynamicTextEntity}
     * @param text            a {@link String} containing the initial textDelegate to be displayed
     */
    public DynamicTextEntity(final Coordinate2D initialPosition, final String text) {
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
    public void injectMotionApplierFactory(final MotionApplierFactory motionApplierFactory) {
        this.motionApplier = motionApplierFactory.create(getMotionModifierType());
    }

    @Inject
    public void setUpdater(final Updater updater) {
        this.updater = updater;
    }
}
