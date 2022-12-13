package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.entities.events.EventTypes;
import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.core.entities.EntityProcessor;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * When a group of entities is combined to create a single {@link YaegerEntity}, they are
 * a composition and this class should be used to perform that composition.
 * <p>
 * It is possible to add instances of {@link YaegerEntity} to this {@link CompositeEntity},
 * which ensures their behavior is managed by this {@link CompositeEntity}. They are still
 * part of the {@link YaegerScene} as any other
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
 * the children receive their {@link Updatable#update(long)}, and can act accordingly,
 * but the {@link CompositeEntity} itself will not so. If such behavior is required, use a {@link DynamicCompositeEntity},
 * which receives its own {@link Updatable#update(long)}.
 * </p>
 */
public abstract class CompositeEntity extends YaegerEntity {

    List<YaegerEntity> entities = new ArrayList<>();
    List<YaegerEntity> garbage = new ArrayList<>();
    Optional<Group> group = Optional.empty();

    /**
     * Create a new {@code CompositeEntity} on the given {@code initialLocation}.
     *
     * @param initialLocation the initial position at which this {@link CompositeEntity} should be placed
     */
    protected CompositeEntity(final Coordinate2D initialLocation) {
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
     * Implement this method to set up all instances of {@link YaegerEntity} that should
     * be added to the {@link CompositeEntity} before activation.
     */
    protected abstract void setupEntities();

    @Override
    public void beforeInitialize() {
        setupEntities();

        for (var entity : entities) {
            entity.beforeInitialize();
        }
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        for (var entity : entities) {
            entity.init(injector);
        }
    }

    /**
     * At this stage we only ask the children to apply their transformation. The transformation that should be
     * applied to this {@link CompositeEntity} can only be applied after its children have been added to the
     * {@link Group} and the {@link Group} has been added to the {@link javafx.scene.Scene}.
     */
    @Override
    public void applyTranslationsForAnchorPoint() {

        for (var entity : entities) {
            entity.applyTranslationsForAnchorPoint();
        }

        super.applyTranslationsForAnchorPoint();
    }

    @Override
    public void applyEntityProcessor(final EntityProcessor processor) {
        super.applyEntityProcessor(processor);

        for (var entity : entities) {
            entity.applyEntityProcessor(processor);
        }
    }

    /**
     * Note that this method will become recursive if the composition consists of more instance of {@link CompositeEntity}.
     */
    @Override
    public void addToParent(final EntityProcessor processor) {
        // First delegate the toParent call to all child Entities
        for (var entity : entities) {
            entity.addToParent(this::addToParentNode);
        }

        // After all child Entities have been added themselves to this parent, add this to its own parent
        super.addToParent(processor);

        // The Node hierarchy has been created and the translations can be applied
        applyTranslationsForAnchorPoint();
    }

    @Override
    public void setAnchorLocation(final Coordinate2D anchorLocation) {
        super.setAnchorLocation(anchorLocation);

        group.ifPresent(g -> {
            g.setLayoutX(anchorLocation.getX());
            g.setLayoutY(anchorLocation.getY());
        });
    }

    @Override
    public Optional<Node> getNode() {
        if (group.isPresent()) {
            return Optional.of(group.get());
        }

        return Optional.empty();
    }

    @Override
    public void setRootPane(Pane rootPane) {
        super.setRootPane(rootPane);

        for (var entity : entities) {
            entity.setRootPane(rootPane);
        }
    }

    /**
     * Set the {@link Group} that is used within this {@code CompositeEntity}. All
     * instances of {@link YaegerEntity} that are added to this {@code CompositeEntity}
     * are packaged together within this {@link Group}. Within it, they have their own
     * coordinate-space and can be placed as a whole on the {@link YaegerScene}.
     *
     * @param group the {@link Group} to be used
     */
    @Inject
    public void setGroup(final Group group) {
        this.group = Optional.of(group);
    }

    @Override
    public void attachEventListener(final EventType eventType, final EventHandler eventHandler) {
        super.attachEventListener(eventType, eventHandler);

        for (var entity : entities) {
            entity.attachEventListener(eventType, event -> handleEvent(eventHandler, event, entity
            ));
        }
    }

    /**
     * Because the {@link Group} encapsulates the child nodes and its {@link javafx.geometry.BoundingBox}
     * depends on the space and location of those child nodes, first the child nodes receive their coordinates
     * and transformations. After this the {@link Group} node does the same.
     */
    @Override
    public void transferCoordinatesToNode() {
        for (var entity : entities) {
            entity.transferCoordinatesToNode();
        }

        super.transferCoordinatesToNode();
    }

    @Override
    public void remove() {
        for (var entity : entities) {
            entity.remove();
        }

        super.remove();
    }

    private void handleEvent(final EventHandler eventHandler, final Event event, final YaegerEntity yaegerEntity) {
        eventHandler.handle(event);

        if (event.getEventType().equals(EventTypes.REMOVE)) {
            garbage.add(yaegerEntity);
        }
    }

    private void addToParentNode(final YaegerEntity entity) {
        group.ifPresent(g -> entity.getNode().ifPresent(node -> g.getChildren().add(node)));
    }
}
