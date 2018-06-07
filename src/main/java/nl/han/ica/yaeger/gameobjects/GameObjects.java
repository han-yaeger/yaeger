package nl.han.ica.yaeger.gameobjects;

import javafx.scene.Group;
import nl.han.ica.yaeger.gameobjects.interfaces.KeyListener;
import nl.han.ica.yaeger.gameobjects.interfaces.Updatable;

import java.util.HashSet;
import java.util.Set;

/**
 * A GameObjects contains all behavior related to all GameObjects.
 */
public class GameObjects extends HashSet<GameObject> {

    private Group group;

    /**
     * Create a new GameObjects.
     * <p>
     * The GameObjects is a subclass of a HashSet and encapsulates all responsibility of adding and removing the
     * GameObjects to and from the Group.
     *
     * @param group The Group that all GameObjects should belong to.
     */
    public GameObjects(Group group) {
        this.group = group;
    }

    /**
     * Add a new GameObject. The GameObject is both added to the screen and this Set.
     *
     * @param gameObject The GameObject to be added.
     * @return A boolean indicating if this GameObject is actually added.
     */
    @Override
    public boolean add(GameObject gameObject) {
        this.group.getChildren().add(gameObject.getGameNode());
        return super.add(gameObject);
    }

    /**
     * Update all gameObjects that implement the interface Updatable.
     */
    public void update() {
        stream().filter(gameObject -> gameObject instanceof Updatable)
                .forEach(gameObject -> ((Updatable) gameObject).update());
    }

    /**
     * Notify all GameObjects that implement the interface KeyListener that keys are being pressed.
     *
     * @param input A Set<String> containing als keys currently pressed.
     */
    public void notifyGameObjectsOfPressedKeys(Set<String> input) {
        stream().filter(gameObject -> gameObject instanceof KeyListener)
                .forEach(gameObject -> ((KeyListener) gameObject).onPressedKeysChange(input));
    }
}
