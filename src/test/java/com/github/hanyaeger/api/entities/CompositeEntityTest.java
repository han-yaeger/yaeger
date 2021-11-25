package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.entities.events.EventTypes;
import com.github.hanyaeger.core.entities.EntityProcessor;
import com.google.inject.Injector;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class CompositeEntityTest {

    private static final Coordinate2D DEFAULT_LOCATION = new Coordinate2D(0, 0);
    private static final Coordinate2D LOCATION = new Coordinate2D(37, 42);

    private CompositeEntityImpl sut;

    private Pane pane;
    private Group group;

    @BeforeEach
    void setup() {
        group = mock(Group.class);
        pane = mock(Pane.class);

        sut = new CompositeEntityImpl(DEFAULT_LOCATION);
        sut.setGroup(group);
    }

    @Test
    void addedEntitiesAreAddedToTheCompositeEntityOnBeforeInitialize() {
        // Arrange
        var entity1 = mock(YaegerEntity.class);
        var entity2 = mock(YaegerEntity.class);
        var entity3 = mock(YaegerEntity.class);

        // Act
        sut.addEntityToAdd(entity1);
        sut.addEntityToAdd(entity2);
        sut.addEntityToAdd(entity3);

        sut.beforeInitialize();

        // Assert
        assertEquals(3, sut.entities.size());
    }

    @Test
    void setAnchorLocationDelegatesToNode() {
        // Arrange

        // Act
        sut.setAnchorLocation(LOCATION);

        // Assert
        verify(group).setLayoutX(LOCATION.getX());
        verify(group).setLayoutY(LOCATION.getY());
    }

    @Test
    void getNodeReturnsGroupIfSet() {
        // Arrange

        // Act
        var optionalNode = sut.getNode();

        // Assert
        if (optionalNode.isPresent()) {
            var node = optionalNode.get();
            assertEquals(group, node);
        } else {
            fail();
        }
    }

    @Test
    void getNodeReturnsNullIfNotSet() {
        // Arrange
        var sutWithoutNode = new CompositeEntityImpl(DEFAULT_LOCATION);

        // Act
        var optionalNode = sutWithoutNode.getNode();

        // Assert
        assertTrue(optionalNode.isEmpty());
    }

    @Nested
    class WithAddedEntities {
        private YaegerEntity entity1;
        private YaegerEntity entity2;
        private YaegerEntity entity3;
        private Injector injector;

        @BeforeEach
        void setup() {
            injector = mock(Injector.class);

            entity1 = mock(YaegerEntity.class);
            entity2 = mock(YaegerEntity.class);
            entity3 = mock(YaegerEntity.class);

            sut.addEntityToAdd(entity1);
            sut.addEntityToAdd(entity2);
            sut.addEntityToAdd(entity3);

            sut.beforeInitialize();
        }

        @Test
        void initDelegatesToChildren() {
            // Arrange

            // Act
            sut.init(injector);

            // Assert
            verify(entity1).init(injector);
            verify(entity2).init(injector);
            verify(entity3).init(injector);
        }

        @Test
        void setRootPaneDelegatedToChildren(){
            // Arrange

            // Act
            sut.setRootPane(pane);

            // Assert
            verify(entity1).setRootPane(pane);
            verify(entity2).setRootPane(pane);
            verify(entity3).setRootPane(pane);
        }

        @Test
        void applyTranslationsForAnchorPointDelegatesToChildren() {
            // Arrange

            // Act
            sut.applyTranslationsForAnchorPoint();

            // Assert
            verify(entity1).applyTranslationsForAnchorPoint();
            verify(entity2).applyTranslationsForAnchorPoint();
            verify(entity3).applyTranslationsForAnchorPoint();
        }

        @Test
        void applyEntityProcessorDelegatesToChildren() {
            // Arrange
            var processor = Mockito.mock(EntityProcessor.class);

            // Act
            sut.applyEntityProcessor(processor);

            // Assert
            verify(entity1).applyEntityProcessor(processor);
            verify(entity2).applyEntityProcessor(processor);
            verify(entity3).applyEntityProcessor(processor);
        }

        @Test
        void transferCoordinatesToNodeDelegatesToChildren() {
            // Arrange

            // Act
            sut.transferCoordinatesToNode();

            // Assert
            verify(entity1).transferCoordinatesToNode();
            verify(entity2).transferCoordinatesToNode();
            verify(entity3).transferCoordinatesToNode();
        }

        @Test
        void transferCoordinatesToNodeFirstDelegatesToChildrenAndThenToSelf() {
            // Arrange
            var inOrder = inOrder(entity1, entity2, entity3, group);

            // Act
            sut.transferCoordinatesToNode();

            // Assert
            inOrder.verify(entity1).transferCoordinatesToNode();
            inOrder.verify(entity2).transferCoordinatesToNode();
            inOrder.verify(entity3).transferCoordinatesToNode();

            inOrder.verify(group).setLayoutX(DEFAULT_LOCATION.getX());
            inOrder.verify(group).setLayoutY(DEFAULT_LOCATION.getY());
        }

        @Test
        void removeDelegatesToChildren() {
            // Arrange

            // Act
            sut.remove();

            // Assert
            verify(entity1).remove();
            verify(entity2).remove();
            verify(entity3).remove();
        }

        @Test
        void addToParentDelegatesToChildren() {
            // Arrange
            var processor = mock(EntityProcessor.class);

            // Act
            sut.addToParent(processor);

            // Assert
            verify(entity1).addToParent(any(EntityProcessor.class));
            verify(entity2).addToParent(any(EntityProcessor.class));
            verify(entity3).addToParent(any(EntityProcessor.class));
        }

        @Test
        void addToParentCallsApplyTranslationsForAnchorPointOnAllChildren() {
            // Arrange
            var processor = mock(EntityProcessor.class);

            // Act
            sut.addToParent(processor);

            // Assert
            verify(entity1).applyTranslationsForAnchorPoint();
            verify(entity2).applyTranslationsForAnchorPoint();
            verify(entity3).applyTranslationsForAnchorPoint();
        }


        @Test
        void attachEventListenersDelegatesToChildren() {
            // Arrange
            var eventType = EventTypes.REMOVE;
            var eventHandler = mock(EventHandler.class);

            // Act
            sut.attachEventListener(eventType, eventHandler);

            // Assert
            verify(entity1).attachEventListener(eq(eventType), any());
            verify(entity2).attachEventListener(eq(eventType), any());
            verify(entity3).attachEventListener(eq(eventType), any());
        }
    }

    private static class CompositeEntityImpl extends CompositeEntity {

        private final List<YaegerEntity> entitiesToAdd = new ArrayList<>();

        public CompositeEntityImpl(Coordinate2D initialLocation) {
            super(initialLocation);
        }

        @Override
        protected void setupEntities() {
            entitiesToAdd.forEach(this::addEntity);
        }

        void addEntityToAdd(final YaegerEntity yaegerEntity) {
            entitiesToAdd.add(yaegerEntity);
        }
    }
}
