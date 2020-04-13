package nl.meron.yaeger.engine.entities.entity.events.userinput;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.annotations.Initializer;
import nl.meron.yaeger.engine.entities.entity.NodeProvider;

import java.util.Optional;

/**
 * Being a {@link MousePressedListener} enables the {@link Entity} to be notified if a {@link MouseButton} has been clicked
 * while the mouse pointer is on the {@link Entity}.
 */
public interface ButtonPressedListener extends NodeProvider {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse pressed event.
     *
     * @param button the {@link MouseEvent} being pressed.
     */
    void onButtonPressed(MouseButton button);

    /**
     * Attach a mousePressedListener to this entity.
     */
    @Initializer
    default void attachButtonPressedListener() {
        if(getGameNode().isPresent()) {
            getGameNode().get().setOnMousePressed(event -> onButtonPressed(event.getButton()));
        }
    }
}
