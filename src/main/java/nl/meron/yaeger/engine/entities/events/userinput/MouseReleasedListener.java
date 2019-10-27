package nl.meron.yaeger.engine.entities.events.userinput;

import javafx.scene.input.MouseButton;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.entity.NodeProvider;

/**
 * Being a {@link MouseReleasedListener} enables the {@link Entity} to be notified if a {@link MouseButton} has been
 * released while the mouse pointer is on the {@link Entity}.
 */
public interface MouseReleasedListener extends NodeProvider {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse released event.
     *
     * @param button the {@link MouseButton} being released.
     */
    void onMouseReleased(MouseButton button);

    /**
     * Attach a {@link MouseReleasedListener} to this entity.
     */
    default void attachMouseReleasedListener() {
        getGameNode().setOnMouseReleased(event -> onMouseReleased(event.getButton()));
    }
}
