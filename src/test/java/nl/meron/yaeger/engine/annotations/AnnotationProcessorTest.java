package nl.meron.yaeger.engine.annotations;

import com.google.inject.Injector;
import javafx.scene.Node;
import nl.meron.yaeger.engine.Timer;

import nl.meron.yaeger.engine.entities.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class AnnotationProcessorTest {

    private static final Location LOCATION = new Location(37, 37);

    private AnnotationProcessor sut;

    @BeforeEach
    void setUp() {
        sut = new AnnotationProcessor();
    }

    @Test
    void invokeInitializersFindsandInvokesAnnotatedMethod() {
        var entityWithInitializer = new EntityWithInitializer(LOCATION);

        // Test
        sut.invokeInitializers(entityWithInitializer);

        // Verify
        Assertions.assertTrue(entityWithInitializer.isInitialized());
    }

    @Test
    void entityWithAsFirstUpdateGetsAddedToTheUpdatersAsFirst() {
        var firstUpdateDelegatingEntity = new FirstUpdateDelegatingEntity(LOCATION);
        var updater = mock(Updater.class);
        firstUpdateDelegatingEntity.setUpdater(updater);

        // Test
        sut.configureUpdateDelegators(firstUpdateDelegatingEntity);

        // Verify
        verify(updater).addUpdatable(any(Updatable.class), eq(true));
    }

    @Test
    void entityWithExtraUpdateGetsAddedToTheUpdaters() {
        var updateDelegatingEntity = new UpdateDelegatingEntity(LOCATION);
        var updater = mock(Updater.class);
        updateDelegatingEntity.setUpdater(updater);

        // Test
        sut.configureUpdateDelegators(updateDelegatingEntity);

        // Verify
        verify(updater).addUpdatable(any(Updatable.class), eq(false));
    }

    private class FirstUpdateDelegatingEntity extends JavaFXEntity implements UpdateDelegator {

        private Updater updater;

        public FirstUpdateDelegatingEntity(Location initialPosition) {
            super(initialPosition);
        }

        @UpdatableProvider(asFirst = true)
        public Updatable provideUpdate() {
            return timestamp -> {
                // Not required here
            };
        }

        @Override
        public Node getGameNode() {
            return null;
        }

        @Override
        public Updater getUpdater() {
            return updater;
        }

        public void setUpdater(Updater updater) {
            this.updater = updater;
        }

        @Override
        public void update(long timestamp) {
            updater.update(timestamp);
        }

        @Override
        public List<Timer> getTimers() {
            return null;
            // Not required here.
        }

        @Override
        public void placeOnLocation(double x, double y) {
            // Not required here.
        }
    }

    private class UpdateDelegatingEntity extends JavaFXEntity implements UpdateDelegator {

        private Updater updater;

        public UpdateDelegatingEntity(Location initialPosition) {
            super(initialPosition);
        }

        @UpdatableProvider
        public Updatable provideUpdate() {
            return timestamp -> {
                // Not required here
            };
        }

        @Override
        public Node getGameNode() {
            return null;
        }

        @Override
        public Updater getUpdater() {
            return updater;
        }

        public void setUpdater(Updater updater) {
            this.updater = updater;
        }

        @Override
        public void update(long timestamp) {
            updater.update(timestamp);
        }

        @Override
        public List<Timer> getTimers() {
            return null;
            // Not required here.
        }

        @Override
        public void placeOnLocation(double x, double y) {
            // Not required here.
        }
    }

    private class EntityWithInitializer extends JavaFXEntity {

        private boolean initialized = false;
        private Node node;

        public EntityWithInitializer(Location initialPosition) {
            super(initialPosition);
        }

        public void setNode(Node node) {
            this.node = node;
        }

        @Override
        public void init(Injector injector) {
            // Not required here.
        }

        @Override
        public void placeOnLocation(double x, double y) {
            // Not required here.
        }

        @Override
        public void remove() {
            // Not required here.
        }

        @Override
        public Node getGameNode() {
            return node;  // Not required here.
        }

        @Initializer
        public void initializerMethod() {
            this.initialized = true;
        }

        public boolean isInitialized() {
            return initialized;
        }

        @Override
        public List<Timer> getTimers() {
            return null;
            // Not required here.
        }
    }
}

