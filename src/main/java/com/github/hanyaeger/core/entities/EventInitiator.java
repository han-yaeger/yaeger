package com.github.hanyaeger.core.entities;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

/**
 * By implementing the interface {@link EventInitiator} it is possible to register an {@link EventHandler} for
 * a given {@link EventType}.
 */
@FunctionalInterface
public interface EventInitiator<T extends Event> {

    /**
     * Attach an {@link EventHandler} for the given {@link EventType}.
     *
     * @param eventType    the {@link EventType} that should trigger the given {@link EventHandler}
     * @param eventHandler the {@link EventHandler} that should be called whenever an event of type {@link EventType}
     *                     occurs
     */
    void attachEventListener(final EventType<T> eventType,
                             final EventHandler<T> eventHandler);
}
