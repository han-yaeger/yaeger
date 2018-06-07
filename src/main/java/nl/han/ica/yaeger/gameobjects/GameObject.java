package nl.han.ica.yaeger.gameobjects;

import javafx.scene.Node;

/**
 * A GameObject is the root object of all objects that compose the game.
 */
public abstract class GameObject {

    /**
     * Return the Node that belongs to this GameObject.
     *
     * @return Node
     */
    public abstract Node getGameNode();


}
