package nl.meron.yaeger.engine.entities.entity.events.userinput;

import nl.meron.yaeger.engine.annotations.OnActivation;
import nl.meron.yaeger.engine.entities.entity.NodeProvider;

/**
 * Being a {@link MouseExitListener} enables the {@link nl.meron.yaeger.engine.entities.entity.YaegerEntity} to be notified if the Mouse Cursor has
 * exited the area defined by the {@link javafx.geometry.BoundingBox} of an {@link nl.meron.yaeger.engine.entities.entity.YaegerEntity}.
 */
public interface MouseExitListener extends NodeProvider {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse exited event.
     */
    void onMouseExited();

    /**
     * Attach a {@link MouseExitListener} to this entity.
     */
    @OnActivation
    default void attachMouseExitListener() {
        getGameNode().get().setOnMouseExited(mouseEvent -> onMouseExited());
    }
}
