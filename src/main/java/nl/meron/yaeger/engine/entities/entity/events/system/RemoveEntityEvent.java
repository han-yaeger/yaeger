package nl.meron.yaeger.engine.entities.entity.events.system;

import javafx.event.Event;
import nl.meron.yaeger.engine.entities.entity.Removeable;
import nl.meron.yaeger.engine.entities.entity.events.EventTypes;

/**
 * Send a {@code RemoveEntityEvent} when a  {@link Removeable} needs to be removed.
 */
public class RemoveEntityEvent extends Event {

    private transient Removeable removeable;

    /**
     * Instantiate a new {@code RemoveEntityEvent}. Pass the {@link Removeable} as an argument.
     *
     * @param removeable the {@link Removeable} that needs to be removed
     */
    public RemoveEntityEvent(Removeable removeable) {
        super(EventTypes.REMOVE);

        this.removeable = removeable;
    }

    @Override
    public Removeable getSource() {
        return removeable;
    }
}
