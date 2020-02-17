package nl.meron.yaeger.engine.entities;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import nl.meron.yaeger.engine.Updatable;
import nl.meron.yaeger.engine.annotations.AnnotationProcessor;
import nl.meron.yaeger.engine.Initializable;
import nl.meron.yaeger.engine.debug.StatisticsObserver;
import nl.meron.yaeger.engine.entities.entity.collisions.Collided;
import nl.meron.yaeger.engine.entities.entity.collisions.Collider;
import nl.meron.yaeger.engine.entities.entity.collisions.CollisionDelegate;
import nl.meron.yaeger.engine.entities.entity.*;
import nl.meron.yaeger.engine.entities.entity.events.EventTypes;
import nl.meron.yaeger.engine.entities.entity.events.userinput.*;
import nl.meron.yaeger.engine.scenes.YaegerScene;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An {@link EntityCollection} encapsulates all behaviour related to all instances of {@link Entity} that are part of
 * a {@link YaegerScene}.
 */
public class EntityCollection implements Initializable {

    private final EntityCollectionStatistics statistics;
    private Injector injector;
    private final Group group;
    private final Set<EntitySupplier> suppliers = new HashSet<>();
    private final Set<Entity> statics = new HashSet<>();
    private final Set<Updatable> updatables = new HashSet<>();
    private final Set<KeyListener> keyListeners = new HashSet<>();
    private final Set<Removeable> garbage = new HashSet<>();

    private final List<StatisticsObserver> statisticsObservers = new ArrayList<>();

    private CollisionDelegate collisionDelegate;
    private AnnotationProcessor annotationProcessor;

    /**
     * Instantiate an {@link EntityCollection} for a given {@link Group} and a {@link Set} of {@link Entity} instances.
     *
     * @param group The {@link Group} to which all instances of {@link Entity}s should be added.
     */
    public EntityCollection(final Group group) {
        this.group = group;
        this.collisionDelegate = new CollisionDelegate();
        this.statistics = new EntityCollectionStatistics();
    }

    /**
     * Add a {@link StatisticsObserver}.
     *
     * @param observer the {@link StatisticsObserver} to be added
     */
    public void addStatisticsObserver(final StatisticsObserver observer) {
        statisticsObservers.add(observer);
    }

    /**
     * Register an {@link EntitySupplier}.
     *
     * @param supplier The {@link EntitySupplier} to be registered.
     */
    public void registerSupplier(final EntitySupplier supplier) {
        this.suppliers.add(supplier);
    }

    /**
     * Mark an {@link Removeable} as garbage. After this is done, the {@link Removeable} is set for Garbage Collection and will
     * be collected in the next Garbage Collection cycle.
     *
     * @param entity The {@link Removeable} to be removed.
     */
    private void markAsGarbage(final Removeable entity) {

        this.garbage.add(entity);
    }

    /**
     * Notify all {@link Entity} that implement the interface {@link KeyListener} that keys are being pressed.
     *
     * @param input A {@link Set<KeyCode>} containing als keys currently pressed.
     */
    public void notifyGameObjectsOfPressedKeys(final Set<KeyCode> input) {
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
    public void update(final long timestamp) {
        collectGarbage();
        notifyUpdatables(timestamp);
        addSuppliedEntities();
        collisionDelegate.checkCollisions();

        updateStatistics();
        notifyStatisticsObservers();
    }

    public void initialUpdate() {
        addSuppliedEntities();
    }

    /**
     * Clear this {@link EntityCollection}.
     */
    public void clear() {
        clearSuppliers();
        statics.clear();
        updatables.clear();
        garbage.clear();
        keyListeners.clear();
    }

    private void clearSuppliers() {
        suppliers.forEach(EntitySupplier::clear);
        suppliers.clear();
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

    private void removeGameObject(final Removeable entity) {
        this.group.getChildren().remove(entity.getGameNode());
        this.collisionDelegate.remove(entity);
    }

    private void addSuppliedEntities() {
        if (!suppliers.isEmpty()) {
            suppliers.forEach(supplier -> supplier.get().forEach(this::addToGameLoop));
        }
    }

    private void addToGameLoop(final Entity entity) {
        initialize(entity);
        addToKeylisteners(entity);
        attachGameEventListeners(entity);
        addToUpdatablesOrStatics(entity);
        collisionDelegate.register(entity);
        addToScene(entity);
    }

    private void initialize(final Entity entity) {
        injector.injectMembers(entity);
        entity.init(injector);
        annotationProcessor.invokeInitializers(entity);
    }

    private void addToUpdatablesOrStatics(final Entity entity) {
        if (entity instanceof Updatable) {
            var updatable = (Updatable) entity;
            annotationProcessor.configureUpdateDelegators(updatable);
            updatables.add(updatable);
        } else {
            statics.add(entity);
        }
    }

    private void addToKeylisteners(final Entity entity) {
        if (entity instanceof KeyListener) {
            keyListeners.add((KeyListener) entity);
        }
    }

    private void addToScene(final Entity entity) {
        this.group.getChildren().add(entity.getGameNode());
    }

    private void attachGameEventListeners(final Entity entity) {
        entity.getGameNode().addEventHandler(EventTypes.REMOVE, event -> markAsGarbage(event.getSource()));
    }

    private void notifyUpdatables(final long timestamp) {
        updatables.forEach(updatable -> updatable.update(timestamp));
    }

    private void updateStatistics() {
        statistics.setUpdatables(updatables.size());
        statistics.setStatics(statics.size());
        statistics.setGarbage(garbage.size());
        statistics.setKeyListeners(keyListeners.size());
        statistics.setSuppliers(suppliers.size());
    }

    @Override
    public void init(final Injector injector) {
        this.injector = injector;
    }

    @Inject
    public void setAnnotationProcessor(AnnotationProcessor annotationProcessor) {
        this.annotationProcessor = annotationProcessor;
    }
}
