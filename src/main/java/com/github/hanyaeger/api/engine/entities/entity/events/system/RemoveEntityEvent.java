package com.github.hanyaeger.api.engine.entities.entity.events.system;

import com.github.hanyaeger.api.engine.entities.entity.Removeable;
import com.github.hanyaeger.api.engine.entities.entity.events.EventTypes;
import javafx.event.Event;

/**
 * Send a {@code RemoveEntityEvent} when a  {@link Removeable} needs to be removed.
 */
public class RemoveEntityEvent extends Event {

    private final transient Removeable removeable;

    /**
     * Instantiate a new {@code RemoveEntityEvent}. Pass the {@link Removeable} as an argument.
     *
     * @param removeable the {@link Removeable} that needs to be removed
     */
    public RemoveEntityEvent(final Removeable removeable) {
        super(EventTypes.REMOVE);

        this.removeable = removeable;
    }

    @Override
    public Removeable getSource() {
        return removeable;
    }
}
