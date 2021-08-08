package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.core.Initializable;
import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.core.YaegerConfig;
import com.github.hanyaeger.core.annotations.AnnotationProcessor;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.userinput.KeyListener;
import com.github.hanyaeger.core.exceptions.YaegerEngineException;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.github.hanyaeger.api.entities.YaegerEntity;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import com.github.hanyaeger.core.entities.events.EventTypes;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * An {@link EntityCollection} encapsulates all behaviour related to all instances of {@link YaegerEntity} that are part of
 * a {@link YaegerScene}. Therefore, an {@link EntityCollection}, is also responsible for creating the Game World update loop.
 */
public class EntityCollection implements Initializable {

    private static final String NO_SHOW_BB_ERROR = "A BoundingBoxVisualizer can only be added when the Game is run with the commandline argument -showBB.";
    private final EntityCollectionStatistics statistics;
    private Injector injector;
    private final Pane pane;
    private final List<EntitySupplier> suppliers = new ArrayList<>();
    private final List<YaegerEntity> statics = new ArrayList<>();
    private final List<Updatable> updatables = new ArrayList<>();
    private final List<KeyListener> keyListeners = new ArrayList<>();
    private final List<YaegerEntity> garbage = new ArrayList<>();

    private EntitySupplier boundingBoxVisualizerSupplier;
    private List<Updatable> boundingBoxVisualizers;

    private final List<StatisticsObserver> statisticsObservers = new ArrayList<>();

    private final CollisionDelegate collisionDelegate;
    private AnnotationProcessor annotationProcessor;
    private final YaegerConfig config;

    /**
     * Instantiate an {@link EntityCollection} for a given {@link Group} and a {@link Set} of {@link YaegerEntity} instances.
     *
     * @param pane   the {@link Group} to which all instances of {@link YaegerEntity}s should be added
     * @param config the {@link YaegerConfig} that should be used with this {@link EntityCollection}
     */
    public EntityCollection(final Pane pane, final YaegerConfig config) {
        this.pane = pane;
        this.config = config;
        this.collisionDelegate = new CollisionDelegate();
        this.statistics = new EntityCollectionStatistics();

        if (config.showBoundingBox()) {
            boundingBoxVisualizers = new ArrayList<>();
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
     * @param timestamp the timestamp of the update
     */
    public void update(final long timestamp) {
        collectGarbage();

        updatables.forEach(updatable -> updatable.update(timestamp));
        collisionDelegate.checkCollisions();

        if (config.showBoundingBox()) {
            boundingBoxVisualizers.forEach(updatable -> updatable.update(timestamp));
        }

        addSuppliedEntities();
        updateStatistics();
        notifyStatisticsObservers();
    }

    /**
     * Perform the initial update, to ensure all available entities are transferred fron their {@link EntitySupplier}
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
        suppliers.forEach(EntitySupplier::clear);
        suppliers.clear();

        if (config.showBoundingBox()) {
            boundingBoxVisualizerSupplier.clear();
        }
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
        if (config.showBoundingBox()) {
            boundingBoxVisualizers.removeAll(garbage);
        }
        garbage.clear();
    }

    private void removeGameObject(final Removable entity) {
        entity.getNode().ifPresent(node -> this.pane.getChildren().remove(node));
        this.collisionDelegate.remove(entity);
    }

    private void addSuppliedEntities() {
        if (!suppliers.isEmpty()) {
            suppliers.forEach(supplier -> supplier.get().forEach(this::initialize));
        }

        if (config.showBoundingBox() && !boundingBoxVisualizerSupplier.isEmpty()) {
            boundingBoxVisualizerSupplier.get().forEach(this::initialize);
        }
    }

    private void initialize(final YaegerEntity entity) {
        entity.beforeInitialize();

        entity.applyEntityProcessor(yaegerEntity -> injector.injectMembers(yaegerEntity));
        entity.init(injector);

        entity.applyEntityProcessor(yaegerEntity -> yaegerEntity.addToEntityCollection(this));
        entity.attachEventListener(EventTypes.REMOVE, event -> markAsGarbage((YaegerEntity) event.getSource()));
        entity.transferCoordinatesToNode();
        entity.applyTranslationsForAnchorPoint();

        entity.applyEntityProcessor(this::registerIfKeyListener);
        entity.applyEntityProcessor(this::registerIfCollider);
        entity.addToParent(this::addToParentNode);

        entity.applyEntityProcessor(yaegerEntity -> annotationProcessor.invokeActivators(yaegerEntity));
    }

    private void registerIfCollider(final YaegerEntity yaegerEntity) {
        final var collider = collisionDelegate.register(yaegerEntity);

        if (collider && config.showBoundingBox()) {
            boundingBoxVisualizerSupplier.add(new BoundingBoxVisualizer(yaegerEntity));
        }
    }

    private void registerIfKeyListener(final YaegerEntity entity) {
        if (entity instanceof KeyListener keyListener) {
            registerKeyListener(keyListener);
        }
    }

    private void addToParentNode(final YaegerEntity entity) {
        entity.getNode().ifPresent(node -> this.pane.getChildren().add(node));
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
     * Set the {@link EntitySupplier} that should be used for the bounding-box visualizers.
     *
     * @param boundingBoxVisualizerSupplier the {@link EntitySupplier} to be used
     */
    @Inject
    public void setBoundingBoxVisualizerSupplier(final EntitySupplier boundingBoxVisualizerSupplier) {
        if (config.showBoundingBox()) {
            this.boundingBoxVisualizerSupplier = boundingBoxVisualizerSupplier;
        }
    }
}
