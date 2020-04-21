package nl.han.yaeger.engine.entities.entity.events.userinput;

import javafx.scene.input.MouseButton;
import nl.han.yaeger.engine.annotations.OnActivation;
import nl.han.yaeger.engine.entities.entity.NodeProvider;

/**
 * Being a {@link MouseButtonReleasedListener} enables the {@link nl.han.yaeger.engine.entities.entity.YaegerEntity} to be notified if a {@link MouseButton} has been
 * released while the mouse pointer is on the {@link nl.han.yaeger.engine.entities.entity.YaegerEntity}.
 */
public interface MouseButtonReleasedListener extends NodeProvider {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse released event.
     *
     * @param button the {@link MouseButton} being released.
     * @param x      The x-coordinate of the mouse pointer while being released as a {@code double}.
     * @param y      The y-coordinate of the mouse pointer while being released as a {@code double}.
     */
    void onMouseButtonReleased(MouseButton button, double x, double y);

    /**
     * Attach a {@link MouseButtonReleasedListener} to this entity.
     */
    @OnActivation
    default void attachMouseReleasedListener() {
        getGameNode().get().setOnMouseReleased(event -> onMouseButtonReleased(event.getButton(), event.getX(), event.getY()));
    }
}
