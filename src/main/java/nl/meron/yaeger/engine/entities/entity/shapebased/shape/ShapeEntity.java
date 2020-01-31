package nl.meron.yaeger.engine.entities.entity.shapebased.shape;

import com.google.inject.Injector;
import javafx.scene.shape.Shape;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.shapebased.ShapeBasedEntity;

/**
 * A {@link ShapeEntity} is an {@link nl.meron.yaeger.engine.entities.entity.Entity} that is based
 * on a {@link Shape} from JavaFX.
 */
public abstract class ShapeEntity extends ShapeBasedEntity {

    /**
     * Create a new {@link ShapeEntity} with a given initial position. The actual {@link Shape}
     * that this {@link nl.meron.yaeger.engine.entities.entity.Entity} is based on should be provided
     * in the {@link ShapeEntity#provideShape()} method.
     *
     * @param initialPosition The initial position at which this {@link nl.meron.yaeger.engine.entities.entity.Entity}
     *                        should be placed.
     */
    public ShapeEntity(Point initialPosition) {
        super(initialPosition);
    }

    @Override
    public void init(Injector injector) {
        shape = provideShape();
        super.init(injector);
    }

    /**
     * Provide the {@link Shape} that should be used bij this {@link ShapeEntity}.
     *
     * @return The {@link Shape} that should be used bij this {@link ShapeEntity}.
     */
    protected abstract Shape provideShape();

}
