package com.github.hanyaeger.core.entities.events;

import com.github.hanyaeger.core.entities.Removable;
import javafx.event.EventType;

/**
 * {@link EventTypes} contains all available custom Events.
 */
public class EventTypes {

    private EventTypes() {
    }

    /**
     * When a {@link Removable} sends an {@code REMOVE} event,
     * it will be removed from the game.
     */
    public static final EventType<RemoveEntityEvent> REMOVE = new EventType<>("REMOVE");
}
