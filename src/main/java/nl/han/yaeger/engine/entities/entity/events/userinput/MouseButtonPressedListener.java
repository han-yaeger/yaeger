package nl.han.yaeger.engine.entities.entity.events.userinput;

import javafx.scene.input.MouseButton;
import nl.han.yaeger.engine.annotations.OnActivation;
import nl.han.yaeger.engine.entities.entity.NodeProvider;

/**
 * Being a {@link MouseButtonPressedListener} enables the {@link nl.han.yaeger.engine.entities.entity.YaegerEntity} to be notified if a {@link MouseButton} has been clicked
 * while the mouse pointer is on the {@link nl.han.yaeger.engine.entities.entity.YaegerEntity}.
 */
public interface MouseButtonPressedListener extends NodeProvider {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse pressed event.
     *
     * @param button the {@link MouseButton} being pressed.
     * @param x      The x-coordinate of the mouse pointer while being pressed as a {@code double}.
     * @param y      The y-coordinate of the mouse pointer while being pressed as a {@code double}.
     */
    void onMouseButtonPressed(MouseButton button, double x, double y);

    /**
     * Attach a mousePressedListener to this entity.
     */
    @OnActivation
    default void attachMousePressedListener() {
        getGameNode().get().setOnMousePressed(event -> onMouseButtonPressed(event.getButton(), event.getX(), event.getY()));
    }
}
