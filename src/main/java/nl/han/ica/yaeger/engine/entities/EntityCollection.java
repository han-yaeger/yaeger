package nl.han.ica.yaeger.engine.entities;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.engine.debug.StatisticsObserver;
import nl.han.ica.yaeger.engine.collisions.Collided;
import nl.han.ica.yaeger.engine.collisions.Collider;
import nl.han.ica.yaeger.engine.collisions.CollisionDelegate;
import nl.han.ica.yaeger.engine.entities.entity.*;
import nl.han.ica.yaeger.engine.entities.spawners.EntitySpawner;
import nl.han.ica.yaeger.engine.entities.events.EventTypes;
import nl.han.ica.yaeger.engine.userinput.KeyListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An {@link EntityCollection} encapsulates all behaviour related to all instances of {@link Entity} that are part of
 * a {@link nl.han.ica.yaeger.engine.scenes.YaegerScene}.
 */
public class EntityCollection {

    private final EntityCollectionStatistics statistics;
    private final Group group;
    private final Set<EntitySpawner> spawners = new HashSet<>();
    private final Set<Entity> statics = new HashSet<>();
    private final Set<Updatable> updatables = new HashSet<>();
    private final Set<KeyListener> keyListeners = new HashSet<>();
    private final Set<Removeable> garbage = new HashSet<>();

    private final List<StatisticsObserver> statisticsObservers = new ArrayList<>();

    private CollisionDelegate collisionDelegate;

    /**
     * Instantiate an {@link EntityCollection} for a given {@link Group} and a {@link Set} of {@link Entity} instances.
     *
     * @param group           The {@link Group} to which all instances of {@link Entity}s should be added.
     * @param initialEntities A {@link Set} containing instances of {@link Entity} that should initially be added to this {@link EntityCollection}.
     */
    public EntityCollection(Group group, Set<Entity> initialEntities) {
        this.group = group;
        this.collisionDelegate = new CollisionDelegate();
        this.statistics = new EntityCollectionStatistics();

        if (initialEntities != null && !initialEntities.isEmpty()) {
            initialEntities.forEach(this::addToGameLoop);
        }
    }

    /**
     * Add a {@link StatisticsObserver}.
     *
     * @param observer the {@link StatisticsObserver} to be added
     */
    public void addStatisticsObserver(StatisticsObserver observer) {
        statisticsObservers.add(observer);
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
     * Mark an {@link Removeable} as garbage. After this is done, the {@link Removeable} is set for Garbage Collection and will
     * be collected in the next Garbage Collection cycle.
     *
     * @param entity The {@link Removeable} to be removed.
     */
    private void markAsGarbage(Removeable entity) {

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
     * Return the statistics related to this {@link EntityCollection}.
     *
     * @return An instance of {@link EntityCollectionStatistics}.
     */
    public EntityCollectionStatistics getStatistics() {
        return statistics;
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
     * {@link Collided} and
     * {@link Collider}. In such a case, the {@link Collided}
     * will be notified.
     * </li>
     * <li>
     * <b>Update Statics</b> Update the {@link EntityCollectionStatistics}.
     * </li>
     * <li>
     * <b>Notify Statistics Observer</b> Notify all registered {@link StatisticsObserver}.
     * </li>
     * </ul>
     *
     * @param timestamp the timestamp of the update
     */
    public void update(long timestamp) {
        collectGarbage();
        notifyUpdatables(timestamp);
        addSpawnedObjects();
        collisionDelegate.checkCollisions();
        updateStatistics();
        notifyStatisticsObservers();
    }

    /**
     * Clear this {@link EntityCollection}.
     */
    public void clear() {
        clearSpawners();
        statics.clear();
        updatables.clear();
        garbage.clear();
        keyListeners.clear();
    }

    private void clearSpawners() {
        spawners.forEach(EntitySpawner::destroy);
        spawners.clear();
    }

    private void notifyStatisticsObservers() {
        statisticsObservers.forEach(statisticsObserver -> statisticsObserver.update(statistics));
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

    private void removeGameObject(Removeable entity) {
        this.group.getChildren().remove(entity.getGameNode());
        this.collisionDelegate.remove(entity);
    }

    private void addSpawnedObjects() {
        if (!spawners.isEmpty()) {
            spawners.forEach(spawner -> spawner.getSpawnedEntities().forEach(this::addToGameLoop));
        }
    }

    private void addToGameLoop(Entity entity) {
        addToKeylisteners(entity);
        addToUpdatablesOrStatics(entity);

        collisionDelegate.register(entity);
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

    private void notifyUpdatables(long timestamp) {
        updatables.forEach(updatable -> updatable.update(timestamp));
    }

    private void updateStatistics() {
        statistics.setUpdatables(updatables.size());
        statistics.setStatics(statics.size());
        statistics.setGarbage(garbage.size());
        statistics.setKeyListeners(keyListeners.size());
        statistics.setSpawners(spawners.size());
    }
}