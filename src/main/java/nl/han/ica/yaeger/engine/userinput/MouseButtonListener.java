package nl.han.ica.yaeger.engine.userinput;

import javafx.scene.input.MouseButton;
import nl.han.ica.yaeger.engine.entities.entity.Entity;
import nl.han.ica.yaeger.engine.entities.entity.NodeProvider;

/**
 * Being a {@code MouseButtonListener} enables the {@link Entity} to be notified if a {@link MouseButton} has been clicked
 * while the mouse pointer is on the {@link Entity}.
 */
public interface MouseButtonListener extends NodeProvider {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse pressed event.
     *
     * @param button the {@link MouseButton} being pressed.
     */
    void onMousePressed(MouseButton button);

    default void attachMousePressedListener() {
        getGameNode().setOnMousePressed(event -> onMousePressed(event.getButton()));
    }
}
