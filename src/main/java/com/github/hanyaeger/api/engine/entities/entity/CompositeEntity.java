package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.annotations.AnnotationProcessor;
import com.google.inject.Inject;
import com.google.inject.Injector;
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
 */
public abstract class CompositeEntity extends YaegerEntity {

    private List<YaegerEntity> entities = new ArrayList<>();
    private Group group;
    private Injector injector;
    private AnnotationProcessor annotationProcessor;

    public CompositeEntity(final Coordinate2D initialLocation) {
        super(initialLocation);
    }

    protected void AddEntity(final YaegerEntity yaegerEntity) {
        entities.add(yaegerEntity);
    }

    @Override
    public void init(Injector injector) {
        super.init(injector);

        this.injector = injector;
    }

    //    @OnActivation
//    public void initComposition() {
//        setupEntities();
//        addNodesToGroup();
//    }

    private void addNodesToGroup() {
        entities.forEach(entity -> group.getChildren().add(entity.getNode().get()));
    }

    /**
     *
     */
    protected abstract void setupEntities();

    @Override
    public void setReferenceX(final double x) {
        group.setLayoutX(x);
    }

    @Override
    public void setReferenceY(final double y) {
        group.setLayoutY(y);
    }

    @Override
    public Optional<Node> getNode() {
        return Optional.empty();
    }

    @Inject
    public void setGroup(final Group group) {
        this.group = group;
    }

    @Inject
    public void setAnnotationProcessor(AnnotationProcessor annotationProcessor) {
        this.annotationProcessor = annotationProcessor;
    }

    @Override
    public void attachEventListener(EventType eventType, EventHandler eventHandler) {
        super.attachEventListener(eventType, eventHandler);

        entities.forEach(entity -> entity.attachEventListener(eventType, eventHandler));
    }
}
