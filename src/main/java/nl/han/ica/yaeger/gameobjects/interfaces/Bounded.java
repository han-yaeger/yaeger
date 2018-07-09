package nl.han.ica.yaeger.gameobjects.interfaces;

import javafx.geometry.Bounds;

/**
 * Implementing this interface exposes the {@code getBounds} method, which returns the bounds, aka
 * Bounding Box, of this GameObject.
 */
public interface Bounded {


    /**
     * Return the Bounds, aka Bounding Box, of this GameObject.
     *
     * @return The Bounds of this GameObject.
     */
    Bounds getBounds();
}
