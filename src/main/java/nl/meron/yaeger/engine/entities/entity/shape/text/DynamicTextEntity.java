package nl.meron.yaeger.engine.entities.entity.shape.text;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.geometry.Point2D;
import nl.meron.yaeger.engine.entities.entity.*;
import nl.meron.yaeger.engine.entities.entity.motion.DefaultMotionApplier;
import nl.meron.yaeger.engine.entities.entity.motion.EntityMotionInitBuffer;
import nl.meron.yaeger.engine.entities.entity.motion.Moveable;
import nl.meron.yaeger.engine.entities.entity.motion.MotionApplier;

import java.util.Optional;

/**
 * An {@link DynamicTextEntity} extends all behaviour of a {@link TextEntity}, but also implements the
 * {@link Updatable} Interface.
 */
public abstract class DynamicTextEntity extends TextEntity implements UpdateDelegator, Moveable, ContinuousRotatable {

    private DefaultMotionApplier motionApplier;
    private Updater updater;
    private Optional<EntityMotionInitBuffer> buffer;
    private double rotationAngle;

    /**
     * Instantiate a new {@link DynamicTextEntity} for the given {@link Location}.
     *
     * @param initialPosition the initial {@link Location} of this {@link DynamicTextEntity}
     */
    public DynamicTextEntity(final Location initialPosition) {
        this(initialPosition, "");
    }

    /**
     * Instantiate a new {@link DynamicTextEntity} for the given {@link Point2D} and textDelegate.
     *
     * @param initialPosition the initial {@link Location} of this {@link DynamicTextEntity}
     * @param text            a {@link String} containing the initial textDelegate to be displayed
     */
    public DynamicTextEntity(final Location initialPosition, final String text) {

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
    public void setSpeedTo(final double newSpeed) {
        buffer.ifPresentOrElse(eMBuffer -> eMBuffer.setSpeedTo(newSpeed), () -> motionApplier.setSpeedTo(newSpeed));
    }

    @Override
    public void setDirectionTo(final double newDirection) {
        buffer.ifPresentOrElse(eMBuffer -> eMBuffer.setDirectionTo(newDirection), () -> motionApplier.setDirectionTo(newDirection));
    }

    @Override
    public void setMotionTo(final double speed, final double direction) {
        buffer.ifPresentOrElse(eMBuffer -> eMBuffer.setMotionTo(speed, direction), () -> motionApplier.setMotionTo(speed, direction));
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
