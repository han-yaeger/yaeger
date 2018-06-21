package nl.han.ica.yaeger.gameobjects;

import javafx.scene.Group;
import nl.han.ica.yaeger.gameobjects.events.EventTypes;
import nl.han.ica.yaeger.gameobjects.interfaces.KeyListener;
import nl.han.ica.yaeger.gameobjects.interfaces.Updatable;
import nl.han.ica.yaeger.gameobjects.spawners.ObjectSpawer;

import java.util.HashSet;
import java.util.Set;

/**
 * A GameObjects contains all behavior related to all GameObjects.
 */
public class GameObjects {

    private Group group;
    private Set<ObjectSpawer> spawners = new HashSet<>();
    private Set<GameObject> gameObjects = new HashSet<>();
    private Set<GameObject> garbage = new HashSet<>();
    private Set<GameObject> incubators = new HashSet<>();

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
     * Add a new GameObject. The GameObject is first added to the list of incubator Objects. Only after the first
     * update cycle, all incubators are added
     *
     * @param gameObject The GameObject to be added.
     * @return A boolean indicating if this GameObject is actually added.
     */
    public boolean add(GameObject gameObject) {
        return incubators.add(gameObject);
    }

    /**
     * Register an ObjectSpawner.
     *
     * @param spawner The ObjectSpawner to be registered.
     */
    public void registerSpawner(ObjectSpawer spawner) {
        this.spawners.add(spawner);
    }

    /**
     * Mark a GameObject as garbage. After this is done, the GameObject is set for Garbage Collection and will
     * be collected in the next Garbage Collection cycle.
     *
     * @param gameObject The GameObject to be removed.
     */
    public void markAsGarbage(GameObject gameObject) {

        this.garbage.add(gameObject);
    }

    /**
     * Notify all GameObjects that implement the interface KeyListener that keys are being pressed.
     *
     * @param input A Set<String> containing als keys currently pressed.
     */
    public void notifyGameObjectsOfPressedKeys(Set<String> input) {
        gameObjects.stream().filter(gameObject -> gameObject instanceof KeyListener)
                .forEach(gameObject -> ((KeyListener) gameObject).onPressedKeysChange(input));
    }

    /**
     * Update all gameObjects that implement the interface Updatable.
     */
    public void update() {
        collectGarbage();
        notifyUpdatableGameObjects();
        collectSpawnedObjects();
        addIncubators();
    }

    private void addIncubators() {
        if (incubators.size() > 0) {
            incubators.stream().forEach(gameObject -> addIncubator(gameObject));
            incubators.clear();
        }
    }

    private void collectGarbage() {
        if (garbage.size() > 0) {
            garbage.stream().forEach(gameObject -> this.group.getChildren().remove(gameObject.getGameNode()));
            gameObjects.removeAll(garbage);
            garbage.clear();
        }
    }

    private void collectSpawnedObjects() {
        if (spawners.size() > 0) {
            spawners.stream().forEach(spawner -> spawner.getSpawnedGameObjects().stream().forEach(gameObject -> this.add(gameObject)));
        }
    }

    private void addIncubator(GameObject gameObject) {
        gameObjects.add(gameObject);
        gameObject.getGameNode().addEventHandler(EventTypes.DELETE, event -> markAsGarbage(event.getSource()));
        this.group.getChildren().add(gameObject.getGameNode());

    }

    private void notifyUpdatableGameObjects() {
        gameObjects.stream().filter(gameObject -> gameObject instanceof Updatable)
                .forEach(gameObject -> ((Updatable) gameObject).update());
    }
}