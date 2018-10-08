package nl.han.ica.yaeger.engine.entities.events;

import javafx.event.Event;
import nl.han.ica.yaeger.engine.entities.entity.Removeable;

/**
 * Send a {@code RemoveEntityEvent} when a  {@link Removeable} needs to be removed.
 */
public class RemoveEntityEvent extends Event {

    private Removeable source;

    /**
     * Instantiate a new {@code RemoveEntityEvent}. Pass the {@link Removeable} as an argument.
     *
     * @param source the {@link Removeable} that needs to be removed
     */
    public RemoveEntityEvent(Removeable source) {
        super(EventTypes.REMOVE);

        this.source = source;
    }

    @Override
    public Removeable getSource() {
        return source;
    }
}
