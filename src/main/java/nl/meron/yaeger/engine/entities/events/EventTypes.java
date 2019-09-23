package nl.meron.yaeger.engine.entities.events;

import javafx.event.EventType;
import nl.meron.yaeger.engine.entities.entity.Removeable;

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
