package nl.han.ica.yaeger.engine.entities.entity;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import nl.han.ica.yaeger.engine.entities.events.RemoveEntityEvent;

/**
 * Een {@code Entity} is het {@code Root Object} van alle entiteiten die deel uitmaken van het game.
 * Deze bevat dan ook al het gedrag dat door alle kind-objecten wordt gedeeld.
 */
public interface Entity extends Bounded, Removeable {

    /**
     * Retourneer de {@link Node} waar deze {@code Entity} bij hoort. Een {@link Node} is
     * onderdeel van de onderliggende structuur.
     *
     * @return Node
     */
    Node getGameNode();

    /**
     * Retourneer de breedte van de scene waar deze Entity deel van uitmaakt.
     *
     * @return De breedte van de scene als een {@code double}.
     */
    default double getSceneWidth() {
        return getGameNode().getScene().getWidth();
    }

    /**
     * Retourneer de hoogte van de scene waar deze Entity deel van uitmaakt.
     *
     * @return De hoogte van de scene als een {@code double}.
     */
    default double getSceneHeight() {
        return getGameNode().getScene().getWidth();
    }

    /**
     * Retourneer de {@code Bounds}, oftewel de {@code BoundingBox} van deze {@code Entity}.
     *
     * @return De {@code Bounds} van deze {@code Entity}.
     */
    default Bounds getBounds() {
        return getGameNode().getBoundsInParent();
    }


    /**
     * Zet de zichtbaarheid van deze {@code Entity}. Als default waarde zal deze op {@code true} staan.
     *
     * @param visible In het geval van {@code true} zal deze {@code Entity} zichtbaar zijn.
     *                In het geval van {@code false} zal deze {@code Entity} niet zichtbaar zijn.
     */
    default void setVisible(boolean visible) {
        getGameNode().setVisible(visible);
    }

    /**
     * @return the {@link Position} of this {@link Entity}
     */
    Position getPosition();
}
