package com.github.hanyaeger.core.entities.events;

import com.github.hanyaeger.core.entities.Removable;
import javafx.event.Event;

/**
 * Send a {@code RemoveEntityEvent} when a  {@link Removable} needs to be removed.
 */
public class RemoveEntityEvent extends Event {

    private final transient Removable removable;

    /**
     * Instantiate a new {@code RemoveEntityEvent}. Pass the {@link Removable} as an argument.
     *
     * @param removable the {@link Removable} that needs to be removed
     */
    public RemoveEntityEvent(final Removable removable) {
        super(EventTypes.REMOVE);

        this.removable = removable;
    }

    @Override
    public Removable getSource() {
        return removable;
    }
}
