package nl.han.yaeger.engine.entities.entity.events;

import javafx.event.EventType;
import nl.han.yaeger.engine.entities.entity.Removeable;
import nl.han.yaeger.engine.entities.entity.events.system.RemoveEntityEvent;

/**
 * {@code EventTypes} contains all available custom Events.
 */
public class EventTypes {

    private EventTypes() {
    }

    /**
     * When a {@link Removeable} sends an {@code REMOVE} event,
     * it will be removed from the game.
     */
    public static final EventType<RemoveEntityEvent> REMOVE = new EventType<>("REMOVE");
}
