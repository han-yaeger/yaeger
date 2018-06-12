package nl.han.ica.yaeger.gameobjects;

import javafx.scene.Node;
import nl.han.ica.yaeger.gameobjects.events.EventTypes;
import nl.han.ica.yaeger.gameobjects.events.RemoveGameObjectEvent;

/**
 * A {@code GameObject} is the root object of all objects that compose the game.
 */
public abstract class GameObject {

    /**
     * Return the {@code Node} that belongs to this {@code GameObject}. A {@code Node}
     * represents a node in the
     *
     * @return Node
     */
    public abstract Node getGameNode();

    /**
     * Return the width of the Scene that this GameObject is part of.
     *
     * @return The width of the Scene as a double
     */
    public double getSceneWidth() {
        return getGameNode().getScene().getWidth();
    }

    /**
     * Return the height of the Scene that this GameObject is part of.
     *
     * @return The height of the Scene as a double
     */
    public double getSceneHeight() {
        return getGameNode().getScene().getWidth();
    }

    /**
     * Send an event to notify all event listeners that this GameObject should be removed.
     */
    public void remove() {
        var deleteEvent = new RemoveGameObjectEvent(EventTypes.DELETE, this);
        getGameNode().fireEvent(deleteEvent);
    }
}
