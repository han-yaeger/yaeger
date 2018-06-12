package nl.han.ica.yaeger.gameobjects;

import javafx.scene.Group;
import nl.han.ica.yaeger.gameobjects.events.EventTypes;
import nl.han.ica.yaeger.gameobjects.interfaces.KeyListener;
import nl.han.ica.yaeger.gameobjects.interfaces.Updatable;

import java.util.HashSet;
import java.util.Set;

/**
 * A GameObjects contains all behavior related to all GameObjects.
 */
public class GameObjects extends HashSet<GameObject> {

    private Group group;
    private Set<GameObject> garbage = new HashSet<>();

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
        gameObject.getGameNode().addEventHandler(EventTypes.DELETE, event -> remove(event.getSource()));
        return super.add(gameObject);
    }

    /**
     * Remove a GameObject from the view. After this is done, the GameObject is set for Garbage Collection and will
     * be collected in the next Garbage Collection cycle.
     *
     * @param gameObject The GameObject to be removed.
     */
    public void remove(GameObject gameObject) {
        this.group.getChildren().remove(gameObject.getGameNode());
        this.garbage.add(gameObject);
    }

    /**
     * Perform Garbage Collection on all GameObjects.
     */
    public void collectGarbage() {
        if (garbage.size() > 0) {
            removeAll(garbage);
            garbage.clear();
        }
    }

    /**
     * Update all gameObjects that implement the interface Updatable.
     */
    public void update() {
        parallelStream().filter(gameObject -> gameObject instanceof Updatable)
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