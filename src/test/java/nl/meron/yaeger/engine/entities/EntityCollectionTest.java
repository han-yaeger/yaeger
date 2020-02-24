package nl.meron.yaeger.engine.entities;

import com.google.inject.Injector;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import nl.meron.yaeger.engine.Timer;
import nl.meron.yaeger.engine.Updatable;
import nl.meron.yaeger.engine.annotations.AnnotationProcessor;
import nl.meron.yaeger.engine.debug.Debugger;
import nl.meron.yaeger.engine.entities.entity.*;
import nl.meron.yaeger.engine.entities.entity.events.userinput.KeyListener;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;

class EntityCollectionTest {

    private EntityCollection sut;
    private Injector injector;
    private AnnotationProcessor annotationProcessor;

    @BeforeEach
    void setup() {
        injector = mock(Injector.class);
        annotationProcessor = mock(AnnotationProcessor.class);
    }

    @Test
    void newInstanceIsEmpty() {
        // Setup
        var group = mock(Group.class);
        var debugger = mock(Debugger.class);

        // Test
        sut = new EntityCollection(group);
        sut.addStatisticsObserver(debugger);
        sut.setAnnotationProcessor(annotationProcessor);

        // Verify
        Assertions.assertEquals(0, sut.getStatistics().getStatics());
        Assertions.assertEquals(0, sut.getStatistics().getUpdatables());
        Assertions.assertEquals(0, sut.getStatistics().getGarbage());
        Assertions.assertEquals(0, sut.getStatistics().getKeyListeners());
        Assertions.assertEquals(0, sut.getStatistics().getSuppliers());
    }

    @Test
    void clearClearsSupplier() {
        // Setup
        var supplier = mock(EntitySupplier.class);
        var group = mock(Group.class);
        sut = new EntityCollection(group);
        sut.setAnnotationProcessor(annotationProcessor);
        sut.registerSupplier(supplier);

        // Test
        sut.clear();

        // Verify
        verify(supplier).clear();
    }

    @Test
    void suppliersEntitiesAreTransferredAtUpdate() {
        // Setup
        var updatableEntity = mock(UpdatableEntity.class);
        var node = mock(Node.class, withSettings().withoutAnnotations());
        when(updatableEntity.getGameNode()).thenReturn(Optional.of(node));

        Set<Entity> updatables = new HashSet<>();
        updatables.add(updatableEntity);
        var supplier = mock(EntitySupplier.class);
        when(supplier.get()).thenReturn(updatables);

        var group = mock(Group.class);
        var children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        sut = new EntityCollection(group);
        sut.setAnnotationProcessor(annotationProcessor);
        sut.init(injector);

        // Test
        sut.registerSupplier(supplier);
        sut.initialUpdate();

        // Verify
        verify(supplier).get();
    }

    @Test
    void keyListeningEntityGetsNotifiedWhenKeyInputChangeAndSetIsEmpty() {
        // Setup
        var keyListeningEntity = mock(KeyListeningEntity.class);
        var node = mock(Node.class, withSettings().withoutAnnotations());
        when(keyListeningEntity.getGameNode()).thenReturn(Optional.of(node));

        var group = mock(Group.class);
        var children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        var entitySupplier = new EntitySupplier();
        entitySupplier.add(keyListeningEntity);

        Set<KeyCode> keycodes = new HashSet<>();

        // Test
        sut = new EntityCollection(group);
        sut.setAnnotationProcessor(annotationProcessor);
        sut.init(injector);
        sut.registerSupplier(entitySupplier);
        sut.update(0);
        sut.notifyGameObjectsOfPressedKeys(keycodes);

        // Verify
        verify(keyListeningEntity).onPressedKeysChange(keycodes);
    }

    @Test
    void keyListeningEntityGetsNotifiedWhenKeyInputChangeAndSetIsFilled() {
        // Setup
        var keyListeningEntity = mock(KeyListeningEntity.class);
        var node = mock(Node.class, withSettings().withoutAnnotations());
        when(keyListeningEntity.getGameNode()).thenReturn(Optional.of(node));

        var group = mock(Group.class);
        var children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        var entitySupplier = new EntitySupplier();
        entitySupplier.add(keyListeningEntity);

        Set<KeyCode> keycodes = new HashSet<>();
        keycodes.add(KeyCode.Y);
        keycodes.add(KeyCode.A);
        keycodes.add(KeyCode.E);
        keycodes.add(KeyCode.G);
        keycodes.add(KeyCode.E);
        keycodes.add(KeyCode.R);

        // Test
        sut = new EntityCollection(group);
        sut.setAnnotationProcessor(annotationProcessor);
        sut.init(injector);
        sut.registerSupplier(entitySupplier);
        sut.update(0);
        sut.notifyGameObjectsOfPressedKeys(keycodes);

        // Verify
        verify(keyListeningEntity).onPressedKeysChange(keycodes);
    }

    @Test
    void annotationProcessorIsCalledForEachEntity() {
        // Setup
        var updatableEntity = mock(UpdatableEntity.class);
        var node = mock(Node.class, withSettings().withoutAnnotations());
        when(updatableEntity.getGameNode()).thenReturn(Optional.of(node));

        Set<Entity> updatables = new HashSet<>();
        updatables.add(updatableEntity);
        var supplier = mock(EntitySupplier.class);
        when(supplier.get()).thenReturn(updatables);

        var group = mock(Group.class);
        var children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        sut = new EntityCollection(group);
        sut.setAnnotationProcessor(annotationProcessor);
        sut.init(injector);

        // Test
        sut.registerSupplier(supplier);
        sut.initialUpdate();

        // Verify
        verify(annotationProcessor).invokeInitializers(updatableEntity);
        verify(annotationProcessor).configureUpdateDelegators(updatableEntity);
    }


    private class UpdatableEntity implements Entity, Updatable {

        @Override
        public void remove() {
            // Not required here.
        }

        @Override
        public Optional<Node> getGameNode() {
            return null;
        }

        @Override
        public AnchorPoint getAnchorPoint() {
            return null;
        }

        @Override
        public void setX(double x) {
            // Not required here.
        }

        @Override
        public void setY(double y) {
            // Not required here.
        }

        @Override
        public void placeOnScene() {

        }

        @Override
        public void update(long timestamp) {
            // Not required here.
        }

        @Override
        public void init(Injector injector) {
            // Not required here.
        }

        @Override
        public List<Timer> getTimers() {
            return null;
            // Not required here.
        }
    }

    private class KeyListeningEntity implements Entity, KeyListener {

        @Override
        public void remove() {
            // Not required here.
        }

        @Override
        public Optional<Node> getGameNode() {
            return null;
        }

        @Override
        public AnchorPoint getAnchorPoint() {
            return null;
        }

        @Override
        public void setX(double x) {
            // Not required here.
        }

        @Override
        public void setY(double y) {
            // Not required here.
        }

        @Override
        public void placeOnScene() {

        }

        @Override
        public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
            // Not required here.
        }

        @Override
        public void init(Injector injector) {
            // Not required here.
        }

        @Override
        public List<Timer> getTimers() {
            return null;
            // Not required here.
        }
    }
}
