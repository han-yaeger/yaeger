package com.github.hanyaeger.api.engine.entities.entity.shape.circle;

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

import java.util.Optional;

/**
 * An {@link DynamicCircleEntity} extends all behaviour of a {@link CircleEntity}, but also implements the
 * {@link Updatable} Interface.
 */
public abstract class DynamicCircleEntity extends CircleEntity implements UpdateDelegator, BufferedMoveable, ContinuousRotatable {

    private MotionApplier motionApplier;
    private Updater updater;
    private Optional<EntityMotionInitBuffer> buffer;
    private double rotationAngle;

    /**
     * Create a new {@link DynamicCircleEntity} on the given {@code initialPosition}.
     *
     * @param initialLocation the initial position at which this {@link DynamicCircleEntity} should be placed
     */
    public DynamicCircleEntity(final Coordinate2D initialLocation) {
        super(initialLocation);

        buffer = Optional.of(new EntityMotionInitBuffer());
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
