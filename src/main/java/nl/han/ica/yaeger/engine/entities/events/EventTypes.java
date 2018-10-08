package nl.han.ica.yaeger.engine.entities.events;

import javafx.event.EventType;

/**
 * {@code EventTypes} contains all available custom Events.
 */
public class EventTypes {

    private EventTypes() {
    }

    /**
     * When a {@link nl.han.ica.yaeger.engine.entities.entity.Removeable} sends an {@code REMOVE} event,
     * it will be removed from the game.
     */
    public static final EventType<RemoveEntityEvent> REMOVE = new EventType<>("REMOVE");
}
