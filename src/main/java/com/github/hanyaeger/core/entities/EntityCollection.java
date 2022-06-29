package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.entities.*;
import com.github.hanyaeger.core.Initializable;
import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.core.YaegerConfig;
import com.github.hanyaeger.core.annotations.AnnotationProcessor;
import com.github.hanyaeger.api.userinput.KeyListener;
import com.github.hanyaeger.core.exceptions.YaegerEngineException;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.core.factories.EntitySupplierFactory;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import com.github.hanyaeger.core.entities.events.EventTypes;
import javafx.scene.layout.Pane;

import java.util.*;

/**
 * An {@link EntityCollection} encapsulates all behaviour related to all instances of {@link YaegerEntity} that are part of
 * a {@link YaegerScene}. Therefore, an {@link EntityCollection}, is also responsible for creating the Game World update loop.
 */
public class EntityCollection implements Initializable {

    private static final String NO_SHOW_BB_ERROR = "A BoundingBoxVisualizer can only be added when the Game is run with the commandline argument -showBB.";
    private final EntityCollectionStatistics statistics;
    private Injector injector;
    private final List<EntitySupplier> suppliers = new ArrayList<>();
    private final List<YaegerEntity> statics = new ArrayList<>();
    private final List<Updatable> updatables = new ArrayList<>();
    private final List<KeyListener> keyListeners = new ArrayList<>();
    private final List<YaegerEntity> garbage = new ArrayList<>();

    private Map<Pane, EntitySupplier> boundingBoxVisualizersMap;
    private List<Updatable> boundingBoxVisualizers;

    private final List<StatisticsObserver> statisticsObservers = new ArrayList<>();

    private final CollisionDelegate collisionDelegate;
    private EntitySupplierFactory entitySupplierFactory;
    private AnnotationProcessor annotationProcessor;
    private final YaegerConfig config;

    /**
     * Instantiate an {@link EntityCollection} for a given {@link Group} and a {@link Set} of {@link YaegerEntity} instances.
     *
     * @param config the {@link YaegerConfig} that should be used with this {@link EntityCollection}
     */
    public EntityCollection(final YaegerConfig config) {
        this.config = config;
        this.collisionDelegate = new CollisionDelegate();
        this.statistics = new EntityCollectionStatistics();

        if (config.showBoundingBox()) {
            boundingBoxVisualizers = new ArrayList<>();
            boundingBoxVisualizersMap = new HashMap<>();
        }
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
     * @param supplier the {@link EntitySupplier} to be registered
     */
    public void registerSupplier(final EntitySupplier supplier) {
        this.suppliers.add(supplier);
    }

    /**
     * Remove the given {@link EntitySupplier} from the list of suppliers.
     *
     * @param supplier the {@link EntitySupplier} to be removed
     */
    public void removeSupplier(final EntitySupplier supplier) {
        supplier.clear();
        this.suppliers.remove(supplier);
    }

    /**
     * Register a {@link KeyListener}.
     *
     * @param keyListener the {@link KeyListener} to be registered
     */
    public void registerKeyListener(final KeyListener keyListener) {
        this.keyListeners.add(keyListener);
    }

    /**
     * Mark an {@link YaegerEntity} as garbage. After this is done, the {@link YaegerEntity} is set for Garbage Collection and will
     * be collected in the next Garbage Collection cycle.
     *
     * @param entity the {@link YaegerEntity} to be removed
     */
    private void markAsGarbage(final YaegerEntity entity) {
        this.garbage.add(entity);
    }

    /**
     * Notify all {@link YaegerEntity} that implement the interface {@link KeyListener} that keys are being pressed.
     *
     * @param input a {@link Set} containing all keys currently pressed
     */
    public void notifyGameObjectsOfPressedKeys(final Set<KeyCode> input) {
        keyListeners.forEach(gameObject -> gameObject.onPressedKeysChange(input));
    }

    /**
     * Return the statistics related to this {@link EntityCollection}.
     *
     * @return an instance of {@link EntityCollectionStatistics}
     */
    public EntityCollectionStatistics getStatistics() {
        return statistics;
    }

    /**
     * Perform all operations required during one cycle of the Game Loop, being:
     *
     * <ul>
     * <li>
     * <b>Collect garbage</b> All EntityCollection that have been marked as Garbage will be removed.
     *
     * <li>
     * <b>Notify Entities</b> On all Entities that implement the interface {@link Updatable}, update()
     * will be called.
     * </li>
     * <li><b>Add supplied entities</b> Entities that should be added are so-called supplied. This means that
     * an {@link EntitySupplier} is registered with this {@link EntityCollection}. Each cycle of the Game Loop all
     * instances of {@link YaegerEntity} that are supplied by all registered {@link EntitySupplier} are transferred to
     * the appropriate collection.
     * </li>
     * <li>
     * <b>Check for collisions</b> Check if collisions have occurred between instances of
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
     * @param timestamp the timestamp of the update as a {@code long}
     */
    public void update(final long timestamp) {
        collectGarbage();

        try {
            updatables.forEach(updatable -> updatable.update(timestamp));
        } catch (ConcurrentModificationException ce) {
            // in case of this exception, the user requested a different Scene to be loaded,
            // so this scene can be gracefully destroyed.
            return;
        }

        collisionDelegate.checkCollisions();

        if (config.showBoundingBox()) {
            boundingBoxVisualizers.forEach(updatable -> updatable.update(timestamp));
        }

        addSuppliedEntities();
        updateStatistics();
        notifyStatisticsObservers(timestamp);
    }

    /**
     * Perform the initial update, to ensure all available entities are transferred from their {@link EntitySupplier}
     * to the actual collections to become part of the {@link EntityCollection}.
     */
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

        if (config.showBoundingBox()) {
            boundingBoxVisualizers.clear();
            boundingBoxVisualizersMap.values().forEach(ArrayList::clear);
            boundingBoxVisualizersMap.clear();
        }
    }

    /**
     * Add a Dynamic Entity to this {@link EntityCollection}. By definition, a Dynamic Entity
     * will implement the {@link Updatable} interface.
     *
     * @param dynamicEntity a Dynamic Entity, being an Entity that implements the interface
     *                      {@link Updatable}
     */
    public void addDynamicEntity(final Updatable dynamicEntity) {
        annotationProcessor.configureUpdateDelegators(dynamicEntity);
        updatables.add(dynamicEntity);
    }

    /**
     * Add a {@link BoundingBoxVisualizer} to this {@link EntityCollection}.
     *
     * @param boundingBoxVisualizer a {@link BoundingBoxVisualizer}
     */
    public void addBoundingBoxVisualizer(final BoundingBoxVisualizer boundingBoxVisualizer) {
        if (config.showBoundingBox()) {
            annotationProcessor.configureUpdateDelegators(boundingBoxVisualizer);
            boundingBoxVisualizers.add(boundingBoxVisualizer);
        } else {
            throw new YaegerEngineException(NO_SHOW_BB_ERROR);
        }
    }

    /**
     * Add a Static Entity to this {@link EntityCollection}.
     *
     * @param staticEntity a Static Entity, being a child of {@link YaegerEntity}
     */
    public void addStaticEntity(final YaegerEntity staticEntity) {
        statics.add(staticEntity);
    }

    private void clearSuppliers() {
        for (var supplier : suppliers) {
            supplier.clear();
        }

        if (config.showBoundingBox()) {
            boundingBoxVisualizersMap.values().forEach(ArrayList::clear);
        }

        suppliers.clear();
    }

    private void notifyStatisticsObservers(final long timestamp) {
        statisticsObservers.forEach(statisticsObserver -> statisticsObserver.update(statistics, timestamp));
    }

    private void collectGarbage() {
        if (garbage.isEmpty()) {
            return;
        }

        for (var entity : garbage) {
            removeGameObject(entity);
        }

        statics.removeAll(garbage);
        updatables.removeAll(garbage);
        if (config.showBoundingBox()) {
            boundingBoxVisualizers.removeAll(garbage);
        }
        garbage.clear();
    }

    private void removeGameObject(final YaegerEntity entity) {
        entity.getNode().ifPresent(node -> entity.getRootPane().getChildren().remove(node));
        this.collisionDelegate.remove(entity);
    }

    private void addSuppliedEntities() {
        if (!suppliers.isEmpty()) {
            suppliers.forEach(supplier -> supplier.get().forEach(yaegerEntity -> initialize(yaegerEntity, supplier.getPane())));
        }

        if (config.showBoundingBox()) {
            boundingBoxVisualizersMap.keySet().forEach(key -> boundingBoxVisualizersMap.get(key).get().forEach(yaegerEntity -> initialize(yaegerEntity, key)));

        }
    }

    private void initialize(final YaegerEntity entity, final Pane paneToBeUsed) {
        entity.beforeInitialize();

        entity.applyEntityProcessor(yaegerEntity -> injector.injectMembers(yaegerEntity));
        entity.init(injector);

        entity.applyEntityProcessor(yaegerEntity -> yaegerEntity.addToEntityCollection(this));
        entity.attachEventListener(EventTypes.REMOVE, event -> markAsGarbage((YaegerEntity) event.getSource()));
        entity.transferCoordinatesToNode();
        entity.applyTranslationsForAnchorPoint();

        entity.applyEntityProcessor(this::registerIfKeyListener);
        // Entities will receive knowledge of the RootPane they are attached to
        entity.setRootPane(paneToBeUsed);
        // When registering a Collidable, the RootPane should be present to ensure
        // the BoundingBoxVisualizer is attached to the same RootPane.
        entity.applyEntityProcessor(this::registerCollidable);

        entity.addToParent(this::addToParentNode);

        entity.applyEntityProcessor(yaegerEntity -> annotationProcessor.invokeActivators(yaegerEntity));
    }

    private void registerCollidable(final YaegerEntity yaegerEntity) {
        final var hasBoundingBox = collisionDelegate.register(yaegerEntity);

        if (hasBoundingBox && config.showBoundingBox()) {
            if (!boundingBoxVisualizersMap.containsKey(yaegerEntity.getRootPane())) {
                var supplier = entitySupplierFactory.create(yaegerEntity.getRootPane());
                boundingBoxVisualizersMap.put(yaegerEntity.getRootPane(), supplier);
            }

            boundingBoxVisualizersMap.get(yaegerEntity.getRootPane()).add(new BoundingBoxVisualizer(yaegerEntity));
        }
    }

    private void registerIfKeyListener(final YaegerEntity entity) {
        if (entity instanceof KeyListener keyListener) {
            registerKeyListener(keyListener);
        }
    }

    private void addToParentNode(final YaegerEntity entity) {
        entity.getNode().ifPresent(node -> entity.getRootPane().getChildren().add(node));
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

    /**
     * Set the {@link AnnotationProcessor} to be used
     *
     * @param annotationProcessor the {@link AnnotationProcessor} to be used
     */
    @Inject
    public void setAnnotationProcessor(final AnnotationProcessor annotationProcessor) {
        this.annotationProcessor = annotationProcessor;
    }

    /**
     * Set the {@link EntitySupplierFactory} to be used
     *
     * @param entitySupplierFactory the {@link EntitySupplierFactory} to be used
     */
    @Inject
    public void setEntitySupplierFactory(final EntitySupplierFactory entitySupplierFactory) {
        this.entitySupplierFactory = entitySupplierFactory;
    }
}
