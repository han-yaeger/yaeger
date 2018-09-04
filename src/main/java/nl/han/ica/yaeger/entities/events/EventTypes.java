package nl.han.ica.yaeger.entities.events;

import javafx.event.EventType;

/**
 * {@code EventTypes} bevat alle beschikbaar custom Events die door een {@link nl.han.ica.yaeger.entities.Entity}
 * kunnen worden verstuurd.
 */
public class EventTypes {

    private EventTypes() {
    }

    /**
     * Wanneer een {@link nl.han.ica.yaeger.entities.Entity} een {@code REMOVE} event verstuurt, zal deze uit het
     * game worden verwijdert.
     */
    public static final EventType<RemoveEntityEvent> REMOVE = new EventType<>("REMOVE");
}
