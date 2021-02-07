package com.github.hanyaeger.api.engine.entities.entity.events;

import com.github.hanyaeger.api.engine.entities.entity.Removable;
import javafx.event.EventType;
import com.github.hanyaeger.api.engine.entities.entity.events.system.RemoveEntityEvent;

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
