package com.github.hanyaeger.api.engine.entities.entity;

import javafx.event.EventHandler;
import javafx.event.EventType;

/**
 * By implementing the interface {@link EventInitiator} it is possible to register an {@link EventHandler} for
 * a given {@link EventType}.
 */
public interface EventInitiator {

    /**
     * Attach an {@link EventHandler} for the given {@link EventType}.
     *
     * @param eventType    The {@link EventType} that should trigger the given {@link EventHandler}.
     * @param eventHandler The {@link EventHandler} that should be called whenever an event of type {@link EventType}
     *                     occurs.
     */
    void attachEventListener(final EventType eventType,
                             final EventHandler eventHandler);
}
