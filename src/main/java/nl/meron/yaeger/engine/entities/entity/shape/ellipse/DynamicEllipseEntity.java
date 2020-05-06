package nl.meron.yaeger.engine.entities.entity.shape.ellipse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import nl.meron.yaeger.engine.Updatable;
import nl.meron.yaeger.engine.UpdateDelegator;
import nl.meron.yaeger.engine.Updater;
import nl.meron.yaeger.engine.entities.entity.ContinuousRotatable;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.motion.BufferedMoveable;
import nl.meron.yaeger.engine.entities.entity.motion.DefaultMotionApplier;
import nl.meron.yaeger.engine.entities.entity.motion.EntityMotionInitBuffer;
import nl.meron.yaeger.engine.entities.entity.motion.MotionApplier;
import nl.meron.yaeger.engine.entities.entity.shape.ellipse.EllipseEntity;

import java.util.Optional;


/**
 * An {@link nl.meron.yaeger.engine.entities.entity.shape.rectangle.DynamicRectangleEntity} extends all behaviour of a {@link EllipseEntity}, but also implements the
 * {@link Updatable} Interface.
 */
public abstract class DynamicEllipseEntity extends EllipseEntity implements UpdateDelegator, BufferedMoveable, ContinuousRotatable {

    private DefaultMotionApplier motionApplier;
    private Updater updater;
    private Optional<EntityMotionInitBuffer> buffer;
    private double rotationAngle;

    /**
     * Create a new {@link nl.meron.yaeger.engine.entities.entity.shape.rectangle.DynamicRectangleEntity} on the given {@code initialPosition}.
     *
     * @param initialPosition The initial position at which this {@link nl.meron.yaeger.engine.entities.entity.shape.ellipse.DynamicEllipseEntity} should be placed
     */
    public DynamicEllipseEntity(final Location initialPosition) {
        super(initialPosition);

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

    @Inject
    @Override
    public void setMotionApplier(final DefaultMotionApplier motionApplier) {
        this.motionApplier = motionApplier;
    }

    @Inject
    public void setUpdater(final Updater updater) {
        this.updater = updater;
    }
}
