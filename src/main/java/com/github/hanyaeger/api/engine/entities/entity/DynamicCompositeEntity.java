package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.UpdateDelegator;
import com.github.hanyaeger.api.engine.Updater;
import com.github.hanyaeger.api.engine.entities.EntityCollection;
import com.github.hanyaeger.api.engine.entities.entity.motion.*;
import com.github.hanyaeger.api.guice.factories.MotionApplierFactory;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.Optional;

/**
 * When a group of Entities are combined to create a single Entity, they are
 * a composition and this class should be used to perform that composition.
 * <p>
 * It is possible to add instances of {@link YaegerEntity} to this {@link DynamicCompositeEntity},
 * which ensures their behavior is managed by this {@link DynamicCompositeEntity}. They are still
 * part of the {@link com.github.hanyaeger.api.engine.scenes.YaegerScene} as any other
 * {@link YaegerEntity}, but are managed as a whole.
 * </p>
 * <p>
 * Since the child Entities are part of this {@link DynamicCompositeEntity}, their event listeners will
 * only be called if their parent {@link DynamicCompositeEntity} implements the correct interfaces. The
 * such a case, the parent will be called first, after which it will direct the event to its children.
 * </p>
 */
public abstract class DynamicCompositeEntity extends CompositeEntity implements UpdateDelegator, BufferedMoveable, ContinuousRotatable {

    private MotionApplier motionApplier;
    private Updater updater;
    private Optional<EntityMotionInitBuffer> buffer;
    private double rotationAngle;

    public DynamicCompositeEntity(final Coordinate2D initialLocation) {
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

        group.ifPresent(groupNode -> garbage.forEach(yaegerEntity ->
                groupNode.getChildren().remove(yaegerEntity.getNode())
        ));

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
    public void injectMotionApplierFactory(final MotionApplierFactory motionApplierFactory) {
        this.motionApplier = motionApplierFactory.create(MotionApplierType.DEFAULT);
    }

    @Inject
    public void setUpdater(final Updater updater) {
        this.updater = updater;
    }
}
