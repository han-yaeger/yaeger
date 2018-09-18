package nl.han.ica.yaeger.entities;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.debug.Debugger;
import nl.han.ica.yaeger.delegates.CollisionDelegate;
import nl.han.ica.yaeger.entities.spawners.EntitySpawner;
import nl.han.ica.yaeger.entities.events.EventTypes;
import nl.han.ica.yaeger.entities.interfaces.KeyListener;
import nl.han.ica.yaeger.entities.interfaces.Updatable;

import java.util.HashSet;
import java.util.Set;

/**
 * An {@link EntityCollection} encapsulates all behaviour related to all instances of {@link Entity} that are part of
 * a {@link nl.han.ica.yaeger.scene.YaegerScene}.
 */
public class EntityCollection {

    private final EntityCollectionStatistics statistics;
    private final Group group;
    private final Debugger debugger;
    private final Set<EntitySpawner> spawners = new HashSet<>();
    private final Set<Entity> statics = new HashSet<>();
    private final Set<Updatable> updatables = new HashSet<>();
    private final Set<KeyListener> keyListeners = new HashSet<>();
    private final Set<Entity> garbage = new HashSet<>();

    private CollisionDelegate collisionDelegate;

    /**
     * Instantiate an {@link EntityCollection} for a given {@link Group} and a {@link Set} of {@link Entity} instances.
     *
     * @param group           The {@link Group} to which all instances of {@link Entity}s should be added.
     * @param initialEntities A {@link Set} containing instances of {@link Entity} that should initially be added to this {@link EntityCollection}.
     * @param debugger
     */
    public EntityCollection(Group group, Set<Entity> initialEntities, Debugger debugger) {
        this.group = group;
        this.debugger = debugger;
        this.collisionDelegate = new CollisionDelegate();
        this.statistics = new EntityCollectionStatistics();

        if (initialEntities != null && !initialEntities.isEmpty()) {
            initialEntities.forEach(this::addToGameLoop);
        }
    }

    /**
     * Register an {@link EntitySpawner}.
     *
     * @param spawner The {@link EntitySpawner} to be registered.
     */
    public void registerSpawner(EntitySpawner spawner) {
        this.spawners.add(spawner);
    }

    /**
     * Mark an {@link Entity} as garbage. After this is done, the {@link Entity} is set for Garbage Collection and will
     * be collected in the next Garbage Collection cycle.
     *
     * @param entity The {@link Entity} to be removed.
     */
    private void markAsGarbage(Entity entity) {

        this.garbage.add(entity);
    }

    /**
     * Notify all {@link Entity} that implement the interface {@link KeyListener} that keys are being pressed.
     *
     * @param input A {@link Set<KeyCode>} containing als keys currently pressed.
     */
    public void notifyGameObjectsOfPressedKeys(Set<KeyCode> input) {
        keyListeners.forEach(gameObject -> gameObject.onPressedKeysChange(input));
    }

    /**
     * Perform all operations required during one cycle of the GameLoop, being:
     *
     * <ul>
     * <li>
     * <b>Collect garbage</b> All EntityCollection that have been marked as Garbage will be removed.
     * </i>
     * <li>
     * <b>Notify Entities</b> On all Entities that implement the interface {@link Updatable}, update()
     * will be called.
     * </li>
     * <li><b>Add spawned objects</b> All Entities created by the {@link EntitySpawner}s will be collected
     * and added to the correct collection.
     * </li>
     * <li>
     * <b>Check for collisions</b> Check if collisions have occured between instances of
     * {@link nl.han.ica.yaeger.entities.interfaces.Collided} and
     * {@link nl.han.ica.yaeger.entities.interfaces.Collider}. In such a case, the {@link nl.han.ica.yaeger.entities.interfaces.Collided}
     * will be notified.
     * </li>
     * <li>
     * <b>Update Statics</b> Update the of this cycle.
     * </li>
     * </ul>
     */
    public void update() {
        collectGarbage();
        notifyUpdatables();
        addSpawnedObjects();
        collisionDelegate.checkCollisions();
        updateStatistics();
        notifyDebugger();
    }

    private void notifyDebugger() {
        debugger.update(statistics);
    }


    /**
     * Clear this {@link EntityCollection}.
     */
    public void clear() {
        spawners.clear();
        statics.clear();
        updatables.clear();
        garbage.clear();
        keyListeners.clear();
    }

    /**
     * Return all statistics from this {@link EntityCollection}.
     *
     * @return An {@link EntityCollectionStatistics}.
     */
    public EntityCollectionStatistics getStatistics() {
        return statistics;
    }

    private void collectGarbage() {
        if (garbage.isEmpty()) {
            return;
        }

        garbage.forEach(this::removeGameObject);
        statics.removeAll(garbage);
        updatables.removeAll(garbage);
        garbage.clear();
    }

    private void removeGameObject(Entity entity) {
        this.group.getChildren().remove(entity.getGameNode());
        this.collisionDelegate.removeGameObject(entity);
    }

    private void addSpawnedObjects() {
        if (!spawners.isEmpty()) {
            spawners.forEach(spawner -> spawner.getSpawnedEntities().forEach(this::addToGameLoop));
        }
    }

    private void addToGameLoop(Entity entity) {
        addToKeylisteners(entity);
        addToUpdatablesOrStatics(entity);

        collisionDelegate.registerForCollisionDetection(entity);
        attachEventListeners(entity);
        addToScene(entity);
    }

    private void addToUpdatablesOrStatics(Entity entity) {
        if (entity instanceof Updatable) {
            updatables.add((Updatable) entity);
        } else {
            statics.add(entity);
        }
    }

    private void addToKeylisteners(Entity entity) {
        if (entity instanceof KeyListener) {
            keyListeners.add((KeyListener) entity);
        }
    }

    private void addToScene(Entity entity) {
        this.group.getChildren().add(entity.getGameNode());
    }

    private void attachEventListeners(Entity entity) {
        entity.getGameNode().addEventHandler(EventTypes.REMOVE, event -> markAsGarbage(event.getSource()));
    }

    private void notifyUpdatables() {
        updatables.forEach(Updatable::update);
    }

    private void updateStatistics() {
        statistics.setUpdatables(updatables.size());
        statistics.setStatics(statics.size());
        statistics.setGarbage(garbage.size());
        statistics.setKeyListeners(keyListeners.size());
        statistics.setSpawners(spawners.size());
    }
}