package com.github.hanyaeger.api.engine.entities;

import com.github.hanyaeger.api.engine.Initializable;
import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.YaegerConfig;
import com.github.hanyaeger.api.engine.annotations.AnnotationProcessor;
import com.github.hanyaeger.api.engine.debug.StatisticsObserver;
import com.github.hanyaeger.api.engine.entities.entity.Removeable;
import com.github.hanyaeger.api.engine.entities.entity.collisions.Collided;
import com.github.hanyaeger.api.engine.entities.entity.collisions.Collider;
import com.github.hanyaeger.api.engine.entities.entity.collisions.CollisionDelegate;
import com.github.hanyaeger.api.engine.entities.entity.events.userinput.KeyListener;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import com.github.hanyaeger.api.engine.entities.entity.events.EventTypes;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * An {@link EntityCollection} encapsulates all behaviour related to all instances of {@link YaegerEntity} that are part of
 * a {@link YaegerScene}. Therefore, an {@link EntityCollection}, is also responsible for creating the Game World update loop.
 */
public class EntityCollection implements Initializable {

    private final EntityCollectionStatistics statistics;
    private Injector injector;
    private final Pane pane;
    private final EntitySupplier boundingBoxes = new EntitySupplier();
    private final List<EntitySupplier> suppliers = new ArrayList<>();
    private final List<YaegerEntity> statics = new ArrayList<>();
    private final List<Updatable> updatables = new ArrayList<>();
    private final List<KeyListener> keyListeners = new ArrayList<>();
    private final List<Removeable> garbage = new ArrayList<>();

    private final List<StatisticsObserver> statisticsObservers = new ArrayList<>();

    private final CollisionDelegate collisionDelegate;
    private AnnotationProcessor annotationProcessor;
    private YaegerConfig config;

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

        if (config.isShowBoundingBox()) {
            registerSupplier(boundingBoxes);
        }
    }

    /**
     * Add a {@link StatisticsObserver}.
     *
     * @param observer the {@link StatisticsObserver} to be added.
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
     * Regist a {@link KeyListener}.
     *
     * @param keyListener The {@link KeyListener} to be registered.
     */
    public void registerKeyListener(final KeyListener keyListener) {
        this.keyListeners.add(keyListener);
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
     * Notify all {@link YaegerEntity} that implement the interface {@link KeyListener} that keys are being pressed.
     *
     * @param input A {@link Set} containing all keys currently pressed.
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

        updatables.forEach(updatable -> updatable.update(timestamp));
        collisionDelegate.checkCollisions();

        addSuppliedEntities();
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
        this.pane.getChildren().remove(entity.getNode());
        this.collisionDelegate.remove(entity);
    }

    private void addSuppliedEntities() {
        if (!suppliers.isEmpty()) {
            suppliers.forEach(supplier -> supplier.get().forEach(this::initialize));
        }
    }

    private void initialize(final YaegerEntity entity) {
        entity.beforeInitialize();

        entity.applyEntityProcessor(yaegerEntity -> injector.injectMembers(yaegerEntity));
        entity.init(injector);
        entity.applyEntityProcessor(yaegerEntity -> annotationProcessor.invokeActivators(yaegerEntity));

        entity.applyEntityProcessor(yaegerEntity -> yaegerEntity.addToEntityCollection(this));
        entity.attachEventListener(EventTypes.REMOVE, event -> markAsGarbage((Removeable) event.getSource()));
        entity.transferCoordinatesToNode();
        entity.applyTranslationsForAnchorPoint();

        entity.applyEntityProcessor(this::registerKeylistener);
        entity.applyEntityProcessor(this::registerCollider);
        entity.addToParent(this::addToParentNode);
    }

    private void registerCollider(final YaegerEntity yaegerEntity) {
        var collider = collisionDelegate.register(yaegerEntity);

        if (collider && config.isShowBoundingBox()) {
            boundingBoxes.add(new BoundingBoxVisualizer(yaegerEntity));
        }
    }

    /**
     * Add a Dynamic Entity to this {@link EntityCollection}. By definition, a Dynamic Entity
     * will implement the {@link Updatable} interface.
     *
     * @param dynamicEntity A Dynamic Entity, being an Entity that implements the interface
     *                      {@link Updatable}.
     */
    public void addDynamicEntity(final Updatable dynamicEntity) {
        annotationProcessor.configureUpdateDelegators(dynamicEntity);
        updatables.add(dynamicEntity);
    }

    /**
     * Add a Static Entity to this {@link EntityCollection}.
     *
     * @param staticEntity A Static Entity, being a child of {@link YaegerEntity}.
     */
    public void addStaticEntity(YaegerEntity staticEntity) {
        statics.add(staticEntity);
    }

    private void registerKeylistener(final YaegerEntity entity) {
        if (entity instanceof KeyListener) {
            registerKeyListener((KeyListener) entity);
        }
    }

    private void addToParentNode(final YaegerEntity entity) {
        this.pane.getChildren().add(entity.getNode().get());
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
    public void setAnnotationProcessor(final AnnotationProcessor annotationProcessor) {
        this.annotationProcessor = annotationProcessor;
    }
}
