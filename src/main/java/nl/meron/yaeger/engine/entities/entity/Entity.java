package nl.meron.yaeger.engine.entities.entity;

import nl.meron.yaeger.engine.Initializable;

/**
 * An {@code Entity} will serve as the {@code Root Interface} for all objects that are part of a
 * {@link nl.meron.yaeger.engine.scenes.YaegerScene}.
 */
public interface Entity extends Initializable, Bounded, Removeable, Placeable, SceneChild, NodeProvider {

    /**
     * Set if this {@link Entity} should be visible.
     *
     * @param visible a {@code boolean} stating whether this {@link Entity} should be visible
     */
    default void setVisible(boolean visible) {
        getGameNode().setVisible(visible);
    }

    /**
     * Lifecycle method that should be used for initializing this {@link Entity}.
     */
    default void initialize() {

    }
}
