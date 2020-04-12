package nl.meron.yaeger.engine.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EntitySpawnerTest {

    private static final int INTERVAL = 1000;

    private EntitySpawnerImpl sut;
    private EntitySupplier supplier;


    @BeforeEach
    void setup() {
        supplier = mock(EntitySupplier.class);

        sut = new EntitySpawnerImpl(INTERVAL);
        sut.setSupplier(supplier);
    }

    @Test
    void getSupplierReturnsInjectedSupplier() {
        // Arrange

        // Act
        var actual = sut.getSupplier();

        // Assert
        Assertions.assertEquals(supplier, actual);
    }

    @Test
    void handleIsCalledOnAnimationUpdateIfTimestampIsMoreThanInterval() {
        // Arrange

        // Act
        sut.handle(1);
        sut.handle(1500 * 1_000_000);

        // Assert
        Assertions.assertTrue(sut.isUpdateCalled());
    }

    private class EntitySpawnerImpl extends EntitySpawner {

        private boolean updateCalled = false;

        /**
         * Create a new instance of {@link EntitySpawner} for the given interval in milliseconds.
         *
         * @param intervalInMs The interval in milleseconds.
         */
        public EntitySpawnerImpl(long intervalInMs) {
            super(intervalInMs);
        }

        @Override
        protected void spawnEntities() {
            updateCalled = true;

        }

        public boolean isUpdateCalled() {
            return updateCalled;
        }
    }
}
