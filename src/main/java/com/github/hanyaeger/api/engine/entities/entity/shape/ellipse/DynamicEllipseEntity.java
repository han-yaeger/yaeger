package com.github.hanyaeger.api.engine.entities.entity.shape.ellipse;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.UpdateDelegator;
import com.github.hanyaeger.api.engine.Updater;
import com.github.hanyaeger.api.engine.entities.EntityCollection;
import com.github.hanyaeger.api.engine.entities.entity.ContinuousRotatable;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.motion.*;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.Optional;

/**
 * An {@link DynamicEllipseEntity} extends all behaviour of a {@link EllipseEntity}, but also implements the
 * {@link Updatable} Interface.
 */
public abstract class DynamicEllipseEntity extends EllipseEntity implements UpdateDelegator, BufferedMoveable, ContinuousRotatable {

    private MotionApplier motionApplier;
    private Updater updater;
    private Optional<EntityMotionInitBuffer> buffer;
    private double rotationAngle;

    /**
     * Create a new {@link DynamicEllipseEntity} on the given {@code initialPosition}.
     *
     * @param initialPosition the initial position at which this {@link DynamicEllipseEntity} should be placed
     */
    public DynamicEllipseEntity(final Coordinate2D initialPosition) {
        super(initialPosition);

        buffer = Optional.of(new EntityMotionInitBuffer());
    }

    /**
     * Create a new {@link EllipseEntity} on the given {@code initialPosition} with the given {@link Size}.
     * Using this constructor results in the same situation as using {@link #DynamicEllipseEntity(Coordinate2D)},
     * {@link #setRadiusX(double)} and {@link #setRadiusY(double)}, where both the {@link Size#getWidth()} and
     * {@link Size#getHeight()} are divided by 2.
     *
     * @param initialPosition the initial position at which this {@link DynamicEllipseEntity} should be placed
     * @param size            the {@link Size} of this {@link DynamicEllipseEntity}
     */
    public DynamicEllipseEntity(final Coordinate2D initialPosition, final Size size) {
        super(initialPosition);
        this.radiusX = size.getWidth() / 2;
        this.radiusY = size.getHeight() / 2;

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
    public void setMotionApplier(final MotionApplier motionApplier) {
        this.motionApplier = motionApplier;
    }

    @Inject
    public void setUpdater(final Updater updater) {
        this.updater = updater;
    }
}
