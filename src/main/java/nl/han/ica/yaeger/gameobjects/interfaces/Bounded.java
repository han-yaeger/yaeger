package nl.han.ica.yaeger.gameobjects.interfaces;

import javafx.geometry.Bounds;

public interface Bounded {


    /**
     * Return the Bounds, aka Bounding Box, of this GameObject.
     *
     * @return The Bounds of this GameObject.
     */
    public Bounds getBounds();
}
