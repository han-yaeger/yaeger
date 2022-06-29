package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.core.UpdateDelegator;
import com.github.hanyaeger.core.Updater;
import com.github.hanyaeger.core.entities.motion.*;
import com.github.hanyaeger.core.entities.EntityCollection;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.Optional;

/**
 * When a group of entities is combined to create a single entity, they are
 * a composition and this class should be used to perform that composition.
 * <p>
 * It is possible to add instances of {@link YaegerEntity} to this {@link DynamicCompositeEntity},
 * which ensures their behavior is managed by this {@link DynamicCompositeEntity}. They are still
 * part of the {@link YaegerScene} as any other
 * {@link YaegerEntity}, but are managed as a whole.
 * </p>
 * <p>
 * Since the child entities are part of this {@link DynamicCompositeEntity}, their event listeners will
 * only be called if their parent {@link DynamicCompositeEntity} implements the correct interfaces. The
 * such a case, the parent will be called first, after which it will direct the event to its children.
 * </p>
 */
public abstract class DynamicCompositeEntity extends CompositeEntity implements UpdateDelegator, BufferedMovable, ContinuousRotatable {

    private MotionApplier motionApplier;
    private Updater updater;
    private Optional<EntityMotionInitBuffer> buffer;
    private double rotationAngle;

    /**
     * Create a new {@code DynamicCompositeEntity} on the given {@code initialLocation}.
     *
     * @param initialLocation the initial position at which this {@link DynamicCompositeEntity} should be placed
     */
    protected DynamicCompositeEntity(final Coordinate2D initialLocation) {
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
    public void update(final long timestamp) {
        collectGarbage();
        getUpdater().update(timestamp);
    }

    private void collectGarbage() {
        if (garbage.isEmpty()) {
            return;
        }

        group.ifPresent(groupNode -> garbage.forEach(yaegerEntity -> yaegerEntity.getNode().ifPresent(node -> groupNode.getChildren().remove(node))));

        entities.removeAll(garbage);
        garbage.clear();
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
    public void addToEntityCollection(final EntityCollection collection) {
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
