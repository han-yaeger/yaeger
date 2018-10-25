package nl.han.ica.yaeger.engine.entities.entity;

import javafx.scene.Node;
import nl.han.ica.yaeger.engine.entities.events.RemoveEntityEvent;

import java.io.Serializable;

/**
 * A {@code Removeable} denotes an {@code Object}, most likely an {@link Entity}, that is eligible for
 * removance from its parent.
 */
public interface Removeable {

    /**
     * Perform all necessary actions to remove the entity.
     */
    void remove();

    /**
     * Return the {@link Node} that belongs to this {@code Removeable}.
     *
     * @return the {@link Node} that belongs to this {@code Removeable}.
     */
    Node getGameNode();

    /**
     * Send a {@link javafx.event.Event} to notify all {@code Listeners} that this {@link Removeable}
     * should be removed.
     */
    default void notifyRemove() {
        var removeEvent = new RemoveEntityEvent(this);
        getGameNode().fireEvent(removeEvent);
    }

}
