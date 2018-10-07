package nl.han.ica.yaeger.engine.entities.entity;

/**
 * Implement this interface to be updated every cycle of the game loop.
 */
public interface Updatable {

    /**
     * The update() method is called each frame.
     *
     * <p>
     * Use this method to init frame-based behaviour to the game-object.
     * </p>
     *
     * @param timestamp the timestamp of the update
     */
    void update(long timestamp);
}
