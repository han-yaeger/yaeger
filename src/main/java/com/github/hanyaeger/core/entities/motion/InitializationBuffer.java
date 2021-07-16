package com.github.hanyaeger.core.entities.motion;

import com.github.hanyaeger.core.entities.events.EventTypes;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link InitializationBuffer} is a simple data container, only used for storing the values can be set
 * before an {@link com.github.hanyaeger.api.entities.YaegerEntity} gets initialized and the
 * {@link javafx.scene.Node} is available.
 */
public class InitializationBuffer {

    private List<EventHandler> removeHandlers = new ArrayList<>();
    private double rotation;

    /**
     * Return the value of {@code rotation}.
     *
     * @return The value of {@code rotation} as a {@code double}.
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * Set the value of {@code rotation}.
     *
     * @param rotation The value of {@code rotation} as a {@code double}.
     */
    public void setRotation(final double rotation) {
        this.rotation = rotation;
    }

    /**
     * Return all the {@link EventHandler} that should be registered for {@link EventTypes#REMOVE}.
     *
     * @return a {@link List} that contains all {@link EventHandler} that  should be registered for {@link EventTypes#REMOVE}
     */
    public List<EventHandler> getRemoveHandlers() {
        return removeHandlers;
    }

    /**
     * Add a {@link EventHandler} that should be registered for {@link EventTypes#REMOVE} when the {@link javafx.scene.Node}
     * has been set.
     *
     * @param removeHandler the {@link EventHandler} that should be registered
     */
    public void addRemoveHandler(final EventHandler removeHandler) {
        removeHandlers.add(removeHandler);
    }
}
