package nl.meron.yaeger.engine.entities.entity.events.userinput;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import nl.meron.yaeger.engine.annotations.OnActivation;
import nl.meron.yaeger.engine.entities.entity.NodeProvider;


/**
 * Being a {@link MousePressedListener} enables the {@link nl.meron.yaeger.engine.entities.entity.YaegerEntity} to be notified if a {@link MouseButton} has been clicked
 * while the mouse pointer is on the {@link nl.meron.yaeger.engine.entities.entity.YaegerEntity}.
 */
public interface MousePressedListener extends NodeProvider {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse pressed event.
     *
     * @param event the {@link MouseEvent} being pressed.
     */
    void onMousePressed(MouseEvent event, Double xCoordinates, Double yCoordinates);

    /**
     * Attach a mousePressedListener to this entity.
     */
    @OnActivation
    default void attachMousePressedListener() {
        if(getGameNode().isPresent()) {
            getGameNode().get().setOnMousePressed(event -> onMousePressed(event, event.getX(), event.getY()));
        }
    }
}
