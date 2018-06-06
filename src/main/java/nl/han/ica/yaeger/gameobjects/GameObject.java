package nl.han.ica.yaeger.gameobjects;

import javafx.scene.Node;

/**
 * A GameObject is the root object of all objects that should be updated during the gameloop.
 * By itself it exposes no behaviour besides the update() method, that should be implemented
 * by all it's children.
 */
public abstract class GameObject {

    /**
     * The update() method is called each frame.
     *
     * <p>
     * Use this method to add frame-based behaviour to the game-object.
     * </p>
     */
    public abstract void update();

    /**
     * Return the Node that belongs to this GameObject.
     *
     * @return Node
     */
    public abstract Node getGameNode();


}
