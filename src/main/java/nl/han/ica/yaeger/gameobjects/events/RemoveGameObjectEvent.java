package nl.han.ica.yaeger.gameobjects.events;

import javafx.event.Event;
import javafx.event.EventType;
import nl.han.ica.yaeger.gameobjects.GameObject;

public class RemoveGameObjectEvent extends Event {

    private GameObject source;

    public RemoveGameObjectEvent(EventType<? extends Event> eventType, GameObject source) {
        super(eventType);

        this.source = source;
    }

    @Override
    public GameObject getSource() {
        return source;
    }
}
