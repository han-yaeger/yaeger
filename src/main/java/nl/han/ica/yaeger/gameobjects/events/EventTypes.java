package nl.han.ica.yaeger.gameobjects.events;

import javafx.event.EventType;

/**
 * The class {@code EventTypes} contains all available custom Events that can be emitted by a {@code GameObject}.
 * All instances are static.
 */
public class EventTypes {

    private EventTypes(){
    }

    /**
     * When an {@code GameObject} emits a {@code DELETE} event, it should be deleted from the game.
     */
    public static final EventType<RemoveGameObjectEvent> DELETE = new EventType<>("DELETE");
}
