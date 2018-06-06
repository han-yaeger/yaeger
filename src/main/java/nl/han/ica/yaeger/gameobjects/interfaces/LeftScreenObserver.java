package nl.han.ica.yaeger.gameobjects.interfaces;

import nl.han.ica.yaeger.gameobjects.enumerations.LeftScreenLocation;

/**
 * Implement this interface to be notified when the GameObject has left the screen.
 */
public interface LeftScreenObserver {

    void hasLeftTheScreen(LeftScreenLocation location);

}
