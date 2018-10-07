package nl.han.ica.yaeger.engine.entities.events;

import javafx.event.Event;
import nl.han.ica.yaeger.engine.entities.entity.Entity;
import nl.han.ica.yaeger.engine.entities.entity.Removeable;

/**
 * Verstuur een {@code RemoveEntityEvent} wanneer een {@link Removeable} verwijdert moet worden.
 */
public class RemoveEntityEvent extends Event {

    private Removeable source;

    /**
     * Creëer een nieuw {@code RemoveEntityEvent}. Bij creatie moet het {@link Removeable} worden meegegeven dat
     * verwijdert dient te worden.
     *
     * @param source Het {@link Removeable} dat verwijdert dient te worden.
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
