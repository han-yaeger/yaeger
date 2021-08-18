package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.core.entities.EntityCollection;
import com.github.hanyaeger.core.entities.motion.*;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.github.hanyaeger.api.entities.ContinuousRotatable;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.core.UpdateDelegator;
import com.github.hanyaeger.core.Updater;

import java.util.Optional;

/**
 * A {@link DynamicSpriteEntity} extends all behaviour of a {@link SpriteEntity}, but also implements the
 * {@link Updatable} Interface.
 */
public abstract class DynamicSpriteEntity extends SpriteEntity implements UpdateDelegator, BufferedMovable, ContinuousRotatable {

    private MotionApplier motionApplier;
    private long autoCycleInterval = 0;
    private int cyclingRow = -1;
    private Updater updater;
    private Optional<EntityMotionInitBuffer> buffer;
    private double rotationAngle;

    /**
     * Create a new {@link DynamicSpriteEntity} for the given image resource on the given {@code initialLocation}.
     * This {@link DynamicSpriteEntity} will use the original dimensions of the image.
     *
     * @param resource        the url of the image file. Relative to the resources folder
     * @param initialLocation the initial {@link Coordinate2D} of this Entity
     */
    protected DynamicSpriteEntity(final String resource, final Coordinate2D initialLocation) {
        this(resource, initialLocation, null, 1, 1);
    }

    /**
     * Create a new {@link DynamicSpriteEntity} for the given image resource on the given {@link Coordinate2D}.
     * This {@link DynamicSpriteEntity} will use the original dimensions of the image.
     *
     * @param resource        the url of the image file. Relative to the resources folder
     * @param initialLocation the initial {@link Coordinate2D} of this Entity
     * @param rows            the number of rows the Image contains
     * @param columns         the number of columns the Image contains
     */
    protected DynamicSpriteEntity(final String resource, final Coordinate2D initialLocation, final int rows, final int columns) {
        this(resource, initialLocation, null, rows, columns);
    }

    /**
     * Create a new {@link DynamicSpriteEntity} for the given image resource on the given {@link Coordinate2D}, with the given {@link Size}.
     *
     * @param resource        the url of the image file. Relative to the resources folder
     * @param initialLocation the initial {@link Coordinate2D} of this Entity
     * @param size            the {@link Size} (width and height) with which the image should be shown. This {@link Size}
     *                        will also be used as the {@link javafx.geometry.BoundingBox} in case of collision detection
     */
    protected DynamicSpriteEntity(final String resource, final Coordinate2D initialLocation, final Size size) {
        this(resource, initialLocation, size, 1, 1);
    }

    /**
     * Create a new {@link DynamicSpriteEntity} for the given image resource on the given {@link Coordinate2D},
     * with the given {@link Size}.
     *
     * @param resource        the url of the image file. Relative to the resources folder
     * @param initialLocation the initial {@link Coordinate2D} of this entity
     * @param size            the {@link Size} (width and height) that should be used. The height and width divided by the
     *                        number of rows and columns will be used for displaying the Image and as the {@link javafx.geometry.BoundingBox} in case of collision detection.
     * @param rows            the number of rows the Image contains
     * @param columns         the number of columns the Image contains
     */
    protected DynamicSpriteEntity(final String resource, final Coordinate2D initialLocation, final Size size, final int rows, final int columns) {
        super(resource, initialLocation, size, rows, columns);

        buffer = Optional.of(new EntityMotionInitBuffer());
    }

    /**
     * Set the interval at which the sprite should be automatically cycled.
     *
     * @param interval the interval in milli-seconds
     */
    protected void setAutoCycle(final long interval) {
        setAutoCycle(interval, -1);
    }

    /**
     * Set the interval at which the sprite should be automatically cycled.
     * The sprite will cycle through one row of sprites, from left to right.
     *
     * @param interval the interval in milli-seconds
     * @param row      the row to cycle through. This value is zero based; if a value of -1 is used, all sprites are
     *                 cycled through
     */
    protected void setAutoCycle(final long interval, final int row) {
        spriteAnimationDelegate.ifPresentOrElse(delegate -> delegate.setAutoCycle(interval, row), () -> {
            this.autoCycleInterval = interval;
            this.cyclingRow = row;
        });
    }

    /**
     * Set the row through which the sprite should be automatically cycled.
     *
     * @param row the row to cycle through. This value is zero based; if a value of -1 is used, all sprites are
     *            cycled through
     */
    protected void setAutoCycleRow(final int row) {
        spriteAnimationDelegate.ifPresentOrElse(delegate -> delegate.setAutoCycleRow(row), () -> this.cyclingRow = row);
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

        spriteAnimationDelegate.ifPresent(delegate -> {
            updater.addUpdatable(delegate);
            if (getFrames() > 1) {
                delegate.setAutoCycle(autoCycleInterval, cyclingRow);
            }
        });

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
    public void addToEntityCollection(final EntityCollection collection) {
        collection.addDynamicEntity(this);
    }

    @Override
    public Optional<EntityMotionInitBuffer> getBuffer() {
        return buffer;
    }

    @Override
    public void setRotationSpeed(final double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    @Override
    public double getRotationSpeed() {
        return rotationAngle;
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

    @Inject
    @Override
    public void setMotionApplier(final MotionApplier motionApplier) {
        this.motionApplier = motionApplier;
    }
}
