package com.github.hanyaeger.core.repositories;

import com.github.hanyaeger.api.userinput.MouseDraggedListener;
import com.google.inject.Singleton;

/**
 * The {@code DragNDropRepository} is responsible for storing the Object that is currently being dragged. Because JavaFX
 * does not support setting a custom GestureSource, whenever a drag is initiated, this repository keeps a reference to the
 * object being dragged. This way, other objects interested in this information have access to this Object.
 */
@Singleton
public class DragNDropRepository {

    private MouseDraggedListener draggedObject;

    /**
     * Get the Object that is currently being dragged.
     *
     * @return the {@link MouseDraggedListener} currently being dragged
     */
    public MouseDraggedListener getDraggedObject() {
        return draggedObject;
    }

    /**
     * Set the Object that is currently being dragged.
     *
     * @param draggedObject the {@link MouseDraggedListener} currently being dragged
     */
    public void setDraggedObject(MouseDraggedListener draggedObject) {
        this.draggedObject = draggedObject;
    }

    /**
     * Clear the content of this repository. To be used the unset any {@link MouseDraggedListener}.
     */
    public void clear() {
        this.draggedObject = null;
    }
}
