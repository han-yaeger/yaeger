package nl.meron.yaeger.engine.scenes;

import nl.meron.yaeger.engine.entities.EntitySupplier;
import nl.meron.yaeger.engine.entities.entitymap.EntityMap;
import nl.meron.yaeger.engine.entities.entitymap.WithEntityMaps;
import nl.meron.yaeger.engine.exceptions.YaegerEngineException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WithEntityMapsTest {

    private WithEntityMapsImpl sut;
    private List<EntityMap> entityMaps;

    @BeforeEach
    void setup() {
        sut = new WithEntityMapsImpl();
    }

    @Test
    void initEntityMapsClearsTheEntityMapList() {
        // Arrange
        entityMaps = mock(List.class);
        sut.setEntityMaps(entityMaps);

        // Act
        sut.initEntityMaps();

        // Assert
        verify(entityMaps).clear();
    }

    @Test
    void initCallsRegisterEntityMaps() {
        // Arrange
        entityMaps = mock(List.class);
        sut.setEntityMaps(entityMaps);

        // Act
        sut.initEntityMaps();

        // Assert
        assertTrue(sut.isRegisterEntityMapsCalled());
    }

    @Test
    void initCallsConfigureOnAllRegisterEntityMaps() {
        // Arrange
        var entityMap = new EntityMapImpl();
        sut.addMapToRegister(entityMap);

        // Act
        sut.initEntityMaps();

        // Assert
        assertTrue(entityMap.isConfigureCalled());
    }

    @Test
    void registeringAnEntityWhenGetEntityIsNullThrowsAYaegerException() {
        // Arrange
        sut.setEntityMaps(null);
        var entityMap = mock(EntityMap.class);

        // Act Assert
        assertThrows(YaegerEngineException.class, () -> sut.registerEntityMap(entityMap));
    }

    private class WithEntityMapsImpl implements WithEntityMaps {
        private List<EntityMap> mapsToRegister = new ArrayList<>();
        private boolean registerEntityMapsCalled = false;

        private List<EntityMap> entityMaps = new ArrayList<>();

        @Override
        public void registerEntityMaps() {
            mapsToRegister.forEach(map -> registerEntityMap(map));
            registerEntityMapsCalled = true;
        }

        @Override
        public List<EntityMap> getEntityMaps() {
            return entityMaps;
        }

        @Override
        public EntitySupplier getEntitySupplier() {
            return null;
        }

        public void setEntityMaps(List<EntityMap> entityMaps) {
            this.entityMaps = entityMaps;
        }

        public boolean isRegisterEntityMapsCalled() {
            return registerEntityMapsCalled;
        }

        public void addMapToRegister(EntityMap mapToRegister) {
            mapsToRegister.add(mapToRegister);
        }

        @Override
        public double getWidth() {
            return 0;
        }

        @Override
        public double getHeight() {
            return 0;
        }
    }

    private class EntityMapImpl extends EntityMap {

        private boolean configureCalled = false;

        @Override
        public void setupEntities() {

        }

        @Override
        public int[][] defineMap() {

            return new int[1][1];
        }

        @Override
        public void configure() {
            super.configure();
            configureCalled = true;
        }

        public boolean isConfigureCalled() {
            return configureCalled;
        }
    }
}
