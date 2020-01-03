package nl.meron.yaeger.engine.entities.entity.drawable;

import com.google.inject.Inject;
import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.UpdatableProvider;
import nl.meron.yaeger.engine.entities.entity.UpdateDelegator;
import nl.meron.yaeger.engine.entities.entity.Updater;

public class DynamicDrawableEntity extends DrawableEntity implements UpdateDelegator {

    private Updater updater;

    /**
     * Instantiate a new {@link DrawableEntity} for the given {@link Point} and textDelegate.
     *
     * @param initialPosition the initial {@link Point} of this {@link DrawableEntity}
     * @param shape           the {@link Shape} to be used by this {@link nl.meron.yaeger.engine.entities.entity.Entity}
     */
    public DynamicDrawableEntity(Point initialPosition, Shape shape) {
        super(initialPosition, shape);
    }

    @Override
    public Updater getUpdater() {
        return updater;
    }

    @Inject
    public void setUpdater(final Updater updater) {
        this.updater = updater;
    }
}
