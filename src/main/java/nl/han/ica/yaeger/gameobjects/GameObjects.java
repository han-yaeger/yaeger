package nl.han.ica.yaeger.gameobjects;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.delegates.CollisionDelegate;
import nl.han.ica.yaeger.exceptions.YaegerLifecycleException;
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

    private CollisionDelegate collisionDelegate;
    private boolean updateHasBeenCalled = false;

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
        this.collisionDelegate = new CollisionDelegate();
    }

    /**
     * Add a new GameObject. The GameObject is first added to the list of incubator Objects. Only after the first
     * update cycle, all incubators are added
     *
     * @param initialGameObjects A Set containing all GameObjects that should be present at initialization.
     */
    public void init(Set<GameObject> initialGameObjects) {
        if (updateHasBeenCalled) {
            throw new YaegerLifecycleException("The renderloop has already started, so it is no longer allowed to call" +
                    "init(). Please use an ObjectSpawner to add new GameObjects.");
        }
        initialGameObjects.stream().forEach(gameObject -> addToGameLoop(gameObject));
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
    public void notifyGameObjectsOfPressedKeys(Set<KeyCode> input) {
        gameObjects.stream().filter(gameObject -> gameObject instanceof KeyListener)
                .forEach(gameObject -> ((KeyListener) gameObject).onPressedKeysChange(input));
    }

    /**
     * Perform all operations required during one cycle of the GameLoop, being:
     *
     * <ul>
     * <li><b>Collect garbage</b></li> All GameObjects that have been marked as Garbage will be removed.
     * <li><b>Notify GameObjects</b> On all GameObjects that implement the interface Updatable, update()
     * will be called.</li>
     * <li><b>Collect spawned objects</b> All GameObjects created by the ObjectsSpawners will be collected
     * and added to the list of GameObjects.</li>
     * </ul>
     */
    public void update() {
        updateHasBeenCalled = true;

        collectGarbage();
        notifyUpdatableGameObjects();
        addSpawnedObjects();
        collisionDelegate.checkCollisions();
    }


    private void collectGarbage() {
        if (garbage.size() == 0) {
            return;
        }

        garbage.stream().forEach(gameObject -> removeGameObject(gameObject));
        gameObjects.removeAll(garbage);
        garbage.clear();
    }

    private void removeGameObject(GameObject gameObject) {
        this.group.getChildren().remove(gameObject.getGameNode());
        this.collisionDelegate.removeGameObject(gameObject);
    }

    private void addSpawnedObjects() {
        if (spawners.size() > 0) {
            spawners.stream().forEach(spawner -> spawner.getSpawnedGameObjects().stream().forEach(gameObject -> this.addToGameLoop(gameObject)));
        }
    }

    private void addToGameLoop(GameObject gameObject) {
        gameObjects.add(gameObject);
        collisionDelegate.registerForCollisionDetection(gameObject);
        attachEventListeners(gameObject);
        addToScene(gameObject);
    }

    private void addToScene(GameObject gameObject) {
        this.group.getChildren().add(gameObject.getGameNode());
    }

    private void attachEventListeners(GameObject gameObject) {
        gameObject.getGameNode().addEventHandler(EventTypes.DELETE, event -> markAsGarbage(event.getSource()));
    }

    private void notifyUpdatableGameObjects() {
        gameObjects.stream().filter(gameObject -> gameObject instanceof Updatable)
                .forEach(gameObject -> ((Updatable) gameObject).update());
    }
}