package nl.han.ica.yaeger.engine.entities.events;

import javafx.event.EventType;
import nl.han.ica.yaeger.engine.entities.entity.Entity;

/**
 * {@code EventTypes} bevat alle beschikbaar custom Events die door een {@link Entity}
 * kunnen worden verstuurd.
 */
public class EventTypes {

    private EventTypes() {
    }

    /**
     * Wanneer een {@link Entity} een {@code REMOVE} event verstuurt, zal deze uit het
     * game worden verwijdert.
     */
    public static final EventType<RemoveEntityEvent> REMOVE = new EventType<>("REMOVE");
}
