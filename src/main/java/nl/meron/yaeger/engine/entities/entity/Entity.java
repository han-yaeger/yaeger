package nl.meron.yaeger.engine.entities.entity;

import javafx.scene.Cursor;
import nl.meron.yaeger.engine.Initializable;
import nl.meron.yaeger.engine.Timeable;

/**
 * An {@code Entity} will serve as the {@code Root Interface} for all objects that are part of a
 * {@link nl.meron.yaeger.engine.scenes.YaegerScene}.
 */
public interface Entity extends Initializable, Timeable, Bounded, Removeable, Placeable, SceneChild, NodeProvider, Rotatable {

    /**
     * Set if this {@link Entity} should be visible.
     *
     * @param visible A {@code boolean} stating whether this {@link Entity} should be visible
     */
    default void setVisible(final boolean visible) {
        getGameNode().setVisible(visible);
    }

    /**
     * Set the cursor to the specified {@link Cursor} type.
     *
     * @param cursor The {@link Cursor} that should be used
     */
    default void setCursor(final Cursor cursor) {
        getGameNode().getScene().setCursor(cursor);
    }
}
