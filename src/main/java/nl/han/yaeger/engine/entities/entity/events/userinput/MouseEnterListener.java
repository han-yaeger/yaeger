package nl.han.yaeger.engine.entities.entity.events.userinput;

import nl.han.yaeger.engine.annotations.OnActivation;
import nl.han.yaeger.engine.entities.entity.NodeProvider;

/**
 * Being a {@link MouseEnterListener} enables the {@link nl.han.yaeger.engine.entities.entity.YaegerEntity} to be notified if the Mouse Cursor has
 * entered the area defined by the {@link javafx.geometry.BoundingBox} of an {@link nl.han.yaeger.engine.entities.entity.YaegerEntity}.
 */
public interface MouseEnterListener extends NodeProvider {

    /**
     * Called when the corresponding {@link javafx.scene.Node} receives a mouse enter event.
     */
    void onMouseEntered();

    /**
     * Attach a {@link MouseEnterListener} to this entity.
     */
    @OnActivation
    default void attachMouseEnterListener() {
        getGameNode().get().setOnMouseEntered(mouseEvent -> onMouseEntered());
    }
}
