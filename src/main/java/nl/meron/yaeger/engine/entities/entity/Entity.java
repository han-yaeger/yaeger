package nl.meron.yaeger.engine.entities.entity;

import javafx.geometry.Bounds;
import nl.meron.yaeger.engine.Initializable;
import nl.meron.yaeger.engine.Initializable;

/**
 * An {@code Entity} will serve as the {@code Root Interface} for all objects that are part of a {@code Yaeger} game.
 */

public interface Entity extends Initializable, Bounded, Removeable, NodeProvider {

    /**
     * Return the width of the {@link javafx.scene.Scene} that this {@code Entity}
     * is part of.
     *
     * @return the width of this {@code Scene} as a {@code double}
     */
    default double getSceneWidth() {
        return getGameNode().getScene().getWidth();
    }

    /**
     * Return the height of the {@link javafx.scene.Scene} that this {@code Entity}
     * is part of.
     *
     * @return the height of this {@code Scene} as a {@code double}
     */
    default double getSceneHeight() {
        return getGameNode().getScene().getHeight();
    }

    /**
     * Return the {@link Bounds} of this {@code Entity}.
     *
     * @return the {@link Bounds} of this {@code Entity}
     */
    default Bounds getBounds() {
        return getGameNode().getBoundsInParent();
    }

    /**
     * Set if this {@code Entity} should be visible.
     *
     * @param visible a {@code boolean} stating whether this {@code Entity} should be visible
     */
    default void setVisible(boolean visible) {
        getGameNode().setVisible(visible);
    }

    /**
     * @return the {@link Position} of this {@link Entity}
     */
    Position getPosition();
}
