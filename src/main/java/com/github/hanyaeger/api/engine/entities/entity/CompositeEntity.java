package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.annotations.AnnotationProcessor;
import com.github.hanyaeger.api.engine.entities.EntityCollection;
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
 * When a group of Entities are combined to create a single Entity, they are
 * a composition and this class should be used to perform that composition.
 * <p>
 * It is possible to add instances of {@link YaegerEntity} to this {@link CompositeEntity},
 * which ensures their behavior is managed by this {@link CompositeEntity}. They are still
 * part of the {@link com.github.hanyaeger.api.engine.scenes.YaegerScene} as any other
 * {@link YaegerEntity}, but are managed as a whole.
 * </p>
 * <p>
 * Since the child Entities are part of this {@link CompositeEntity}, their event listeners will
 * only be called if their parent {@link CompositeEntity} implements the correct interfaces. The
 * such a case, the parent will be called first, after which it will direct the event to its children.
 * </p>
 */
public abstract class CompositeEntity extends YaegerEntity {

    protected List<YaegerEntity> entities = new ArrayList<>();
    protected List<Removeable> garbage = new ArrayList<>();
    protected Optional<Group> group = Optional.empty();
    private Injector injector;
    private AnnotationProcessor annotationProcessor;

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
    public void init(final Injector injector) {
        super.init(injector);

        this.injector = injector;
    }

    @Override
    public void afterInit() {
        setupEntities();
        entities.forEach(entity -> {
            injector.injectMembers(entity);
            entity.init(injector);
            annotationProcessor.invokeActivators(entity);
        });
    }

    @Override
    public void addToEntityCollection(final EntityCollection collection) {
        super.addToEntityCollection(collection);

        entities.forEach(yaegerEntity -> yaegerEntity.addToEntityCollection(collection));
    }

    @Override
    public void applyEntityProcessor(final EntityProcessor processor) {
        super.applyEntityProcessor(processor);

        entities.forEach(entity -> processor.process(entity));
    }

    @Override
    public void addToParent(final EntityProcessor processor) {
        super.addToParent(processor);

        group.ifPresent(groupNode -> entities.forEach(entity -> groupNode.getChildren().add(entity.getNode().get())));
    }

    @Override
    public void setAnchorLocation(Coordinate2D anchorLocation) {
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

    @Inject
    public void setAnnotationProcessor(final AnnotationProcessor annotationProcessor) {
        this.annotationProcessor = annotationProcessor;
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
        entities.forEach(yaegerEntity -> yaegerEntity.transferCoordinatesToNode());

        super.transferCoordinatesToNode();
    }

    private void handleEvent(final EventHandler eventHandler, final Event event, final YaegerEntity yaegerEntity) {
        eventHandler.handle(event);

        if (event.getEventType().equals(EventTypes.REMOVE)) {
            garbage.add(yaegerEntity);
        }
    }
}
