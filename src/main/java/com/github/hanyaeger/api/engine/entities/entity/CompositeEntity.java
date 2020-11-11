package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.entities.EntityProcessor;
import com.github.hanyaeger.api.engine.entities.EntitySpawner;
import com.github.hanyaeger.api.engine.entities.entity.events.EventTypes;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * When a group of Entities are combined to create a single {@link YaegerEntity}, they are
 * a composition and this class should be used to perform that composition.
 * <p>
 * It is possible to add instances of {@link YaegerEntity} to this {@link CompositeEntity},
 * which ensures their behavior is managed by this {@link CompositeEntity}. They are still
 * part of the {@link com.github.hanyaeger.api.engine.scenes.YaegerScene} as any other
 * {@link YaegerEntity}, but are managed as a whole.
 * </p>
 * <p>
 * This means that this {@link CompositeEntity} has its own coordinate system, meaning (0,0) is the
 * origin of this {@link CompositeEntity}. The width and height is hence calculated, based on its content.
 * This is done only at initialization, so removing Entities from this {@link CompositeEntity} after
 * initialization, does nog change its width or height.
 * </p>
 * <p>
 * Removing Entities from this {@link CompositeEntity} removes them as expected. Removing the entire
 * {@link CompositeEntity}, also removes all Entities that are part of the composition.
 * </p>
 * <p>
 * A {@link CompositeEntity} does not listen to a Game World Update, but its children still can. In such a case
 * the children receive their {@link com.github.hanyaeger.api.engine.Updatable#update(long)}, and can act accordingly,
 * but the {@link CompositeEntity} itself will not so. If such behavior is required, use a {@link DynamicCompositeEntity},
 * which receives its own {@link com.github.hanyaeger.api.engine.Updatable#update(long)}.
 * </p>
 */
public abstract class CompositeEntity extends YaegerEntity {

    List<YaegerEntity> entities = new ArrayList<>();
    List<Removeable> garbage = new ArrayList<>();
    Optional<Group> group = Optional.empty();

    public CompositeEntity(final Coordinate2D initialLocation) {
        super(initialLocation);
    }

    /**
     * Add an {@link YaegerEntity} to this {@link YaegerScene}.
     * <p>
     * This method can only be used to add an instance of {@link YaegerEntity} during initialisation.If
     * one should be added during the game, a {@link EntitySpawner} should be used.
     * </p>
     *
     * @param yaegerEntity The {@link YaegerEntity} to be added.
     */
    protected void addEntity(final YaegerEntity yaegerEntity) {
        entities.add(yaegerEntity);
    }

    /**
     * Implement this method to setup all instances of {@link YaegerEntity} that should
     * be added to the {@link CompositeEntity} before activation.
     */
    protected abstract void setupEntities();

    @Override
    public void beforeInitialize() {
        setupEntities();

        entities.forEach(yaegerEntity -> yaegerEntity.beforeInitialize());
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        entities.forEach(yaegerEntity -> yaegerEntity.init(injector));
    }

    /**
     * At this stage we only ask the children to apply their transformation. The transformation that should be
     * applied to this {@link CompositeEntity} can only be applied after its children have been added to the
     * {@link Group} and the {@link Group} has been added to the {@link javafx.scene.Scene}.
     */
    @Override
    public void applyTranslationsForAnchorPoint() {
        entities.forEach(yaegerEntity -> yaegerEntity.applyTranslationsForAnchorPoint());

        super.applyTranslationsForAnchorPoint();
    }

    @Override
    public void applyEntityProcessor(final EntityProcessor processor) {
        super.applyEntityProcessor(processor);

        entities.forEach(yaegerEntity -> yaegerEntity.applyEntityProcessor(processor));
    }

    /**
     * Note that this method will become recursive it composition consists of more instance of {@link CompositeEntity}.
     */
    @Override
    public void addToParent(final EntityProcessor processor) {
        // First delegate the toParent call to all child Entities
        entities.forEach(yaegerEntity -> yaegerEntity.addToParent(entity -> addToParentNode(entity)));

        // After all child Entities have been added themself to this parent, add this to its own parent
        super.addToParent(processor);

        // The Node hierarchy has been created and the translations can be applied
        applyTranslationsForAnchorPoint();
    }

    @Override
    public void setAnchorLocation(final Coordinate2D anchorLocation) {
        super.setAnchorLocation(anchorLocation);

        group.ifPresent(group -> {
            group.setLayoutX(anchorLocation.getX());
            group.setLayoutY(anchorLocation.getY());
        });
    }

    @Override
    public Optional<Node> getNode() {
        if (group.isPresent()) {
            return Optional.of(group.get());
        }

        return Optional.empty();
    }

    @Inject
    public void setGroup(final Group group) {
        this.group = Optional.of(group);
    }

    @Override
    public void attachEventListener(final EventType eventType, final EventHandler eventHandler) {
        super.attachEventListener(eventType, eventHandler);

        entities.forEach(yaegerEntity -> {
                    yaegerEntity.attachEventListener(eventType, event -> handleEvent(eventHandler, event, yaegerEntity));
                }
        );
    }

    /**
     * Because the {@link Group} encapsulates the child nodes and its {@link javafx.geometry.BoundingBox}
     * depends on the space and location of those child nodes, first the child nodes receive their coordinates
     * and transformations. After this the {@link Group} nodes does the same.
     */
    @Override
    public void transferCoordinatesToNode() {
        entities.forEach(YaegerEntity::transferCoordinatesToNode);

        super.transferCoordinatesToNode();
    }

    @Override
    public void remove() {
        entities.forEach(yaegerEntity -> yaegerEntity.remove());

        super.remove();
    }

    private void handleEvent(final EventHandler eventHandler, final Event event, final YaegerEntity yaegerEntity) {
        eventHandler.handle(event);

        if (event.getEventType().equals(EventTypes.REMOVE)) {
            garbage.add(yaegerEntity);
        }
    }

    private void addToParentNode(final YaegerEntity entity) {
        group.ifPresent(group -> entity.getNode().ifPresent(node -> group.getChildren().add(node)));
    }
}
