package nl.meron.yaeger.engine.entities.entity.events.userinput;

import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.annotations.Initializer;
import nl.meron.yaeger.engine.entities.entity.NodeProvider;

/**
 * Being a {@link MouseEnterListener} enables the {@link Entity} to be notified if the Mouse Cursor has
 * entered the area defined by the {@link javafx.geometry.BoundingBox} of an {@link Entity}.
 */
public interface MouseEnterListener extends NodeProvider {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse enter event.
     */
    void onMouseEntered();

    /**
     * Attach a mousePressedListener to this entity.
     */
    @Initializer
    default void attachMouseEnterListener() {
        getGameNode().setOnMouseEntered(mouseEvent -> onMouseEntered());
    }
}
