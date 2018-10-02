package nl.han.ica.yaeger.engine.entities.events;

import javafx.event.Event;
import nl.han.ica.yaeger.engine.entities.Entity;

/**
 * Verstuur een {@code RemoveEntityEvent} wanneer een {@link Entity} verwijdert moet worden.
 */
public class RemoveEntityEvent extends Event {

    private Entity source;

    /**
     * Creëer een nieuw {@code RemoveEntityEvent}. Bij creatie moet het {@link Entity} worden meegegeven dat
     * verwijdert dient te worden.
     *
     * @param source Het {@code Entity} dat verwijdert dient te worden.
     */
    public RemoveEntityEvent(Entity source) {
        super(EventTypes.REMOVE);

        this.source = source;
    }

    @Override
    public Entity getSource() {
        return source;
    }
}
