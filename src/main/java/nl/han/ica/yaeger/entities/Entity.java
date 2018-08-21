package nl.han.ica.yaeger.entities;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import nl.han.ica.yaeger.entities.events.EventTypes;
import nl.han.ica.yaeger.entities.events.RemoveEntityEvent;
import nl.han.ica.yaeger.entities.interfaces.Bounded;

/**
 * Een {@code Entity} is het {@code Root Object} van alle entiteiten die deel uitmaken van het spel.
 * Deze bevat dan ook al het gedrag dat door alle kind-objecten wordt gedeeld.
 */
public abstract class Entity implements Bounded {

    /**
     * Retourneer de {@link Node} waar deze {@code Entity} bij hoort. Een {@link Node} is
     * onderdeel van de onderliggende structuur.
     *
     * @return Node
     */
    public abstract Node getGameNode();

    /**
     * Return the width of the Scene that this Entity is part of.
     *
     * @return The width of the Scene as a {@code double}
     */
    public double getSceneWidth() {
        return getGameNode().getScene().getWidth();
    }

    /**
     * Return the height of the Scene that this {@code Entity} is part of.
     *
     * @return The height of the Scene as a {@code double}
     */
    public double getSceneHeight() {
        return getGameNode().getScene().getWidth();
    }

    /**
     * Retourneer de {@code Bounds}, oftewel de {@code BoundingBox} van deze {@code Entity}.
     *
     * @return De {@code Bounds} van deze {@code Entity}.
     */
    public Bounds getBounds() {
        return getGameNode().getBoundsInParent();
    }

    /**
     * Verstuur een {@link javafx.event.Event} om alle {@code Listeners} op de hoogte te stellen dat deze
     * {@code Entity} moet worden verwijdert.
     */
    public void remove() {
        var removeEvent = new RemoveEntityEvent(this);
        getGameNode().fireEvent(removeEvent);
    }

    /**
     * Zet de zichtbaarheid van deze {@code Entity}. Als default waarde zal deze op {@code true} staan.
     *
     * @param visible In het geval van {@code true} zal deze {@code Entity} zichtbaar zijn.
     *                In het geval van {@code false} zal deze {@code Entity} niet zichtbaar zijn.
     */
    public void setVisible(boolean visible) {
        getGameNode().setVisible(visible);
    }
}
