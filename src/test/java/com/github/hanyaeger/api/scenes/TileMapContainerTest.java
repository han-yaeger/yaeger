package com.github.hanyaeger.api.scenes;

import com.github.hanyaeger.core.entities.EntitySupplier;
import com.github.hanyaeger.core.factories.TileFactory;
import com.github.hanyaeger.core.exceptions.YaegerEngineException;
import com.google.inject.Injector;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TileMapContainerTest {

    private TileMapContainerImpl sut;
    private Injector injector;
    private List<TileMap> tileMaps;

    @BeforeEach
    void setup() {
        sut = new TileMapContainerImpl();

        injector = mock(Injector.class);
        sut.setInjector(injector);
    }

    @Test
    void initEntityMapsClearsTheEntityMapList() {
        // Arrange
        tileMaps = mock(List.class);
        sut.setTileMaps(tileMaps);

        // Act
        sut.initTileMaps();

        // Assert
        verify(tileMaps).clear();
    }

    @Test
    void initCallsRegisterEntityMaps() {
        // Arrange
        tileMaps = mock(List.class);
        sut.setTileMaps(tileMaps);

        // Act
        sut.initTileMaps();

        // Assert
        assertTrue(sut.isRegisterEntityMapsCalled());
    }

    @Test
    void initCallsConfigureOnAllRegisterEntityMaps() {
        // Arrange
        var tileMap = new TileMapImpl();
        var tileFactory = mock(TileFactory.class);
        tileMap.setTileFactory(tileFactory);
        sut.addMapToRegister(tileMap);

        // Act
        sut.initTileMaps();

        // Assert
        assertTrue(tileMap.isConfigureCalled());
    }

    @Test
    void initCallsInjectorToInjectDependenciesOnRegisterEntityMaps() {
        // Arrange
        var tileMap = new TileMapImpl();
        var tileFactory = mock(TileFactory.class);
        tileMap.setTileFactory(tileFactory);
        sut.addMapToRegister(tileMap);

        // Act
        sut.initTileMaps();

        // Assert
        verify(injector).injectMembers(tileMap);
    }

    @Test
    void registeringAnEntityMapWhenGetEntityMapsIsNullThrowsAYaegerException() {
        // Arrange
        sut.setTileMaps(null);
        var entityMap = mock(TileMap.class);

        // Act Assert
        assertThrows(YaegerEngineException.class, () -> sut.addTileMap(entityMap));
    }

    private static class TileMapContainerImpl implements TileMapContainer {
        private final List<TileMap> mapsToRegister = new ArrayList<>();
        private boolean registerEntityMapsCalled = false;

        private Injector injector;
        private List<TileMap> tileMaps = new ArrayList<>();

        @Override
        public void setupTileMaps() {
            mapsToRegister.forEach(this::addTileMap);
            registerEntityMapsCalled = true;
        }

        @Override
        public List<TileMap> getTileMaps() {
            return tileMaps;
        }

        @Override
        public EntitySupplier getEntitySupplier() {
            return null;
        }

        public void setTileMaps(List<TileMap> tileMaps) {
            this.tileMaps = tileMaps;
        }

        public boolean isRegisterEntityMapsCalled() {
            return registerEntityMapsCalled;
        }

        public void addMapToRegister(TileMap mapToRegister) {
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

        @Override
        public Injector getInjector() {
            return injector;
        }

        public void setInjector(Injector injector) {
            this.injector = injector;
        }
    }

    private static class TileMapImpl extends TileMap {

        private boolean configureCalled = false;

        @Override
        public void setupEntities() {

        }

        @Override
        public int[][] defineMap() {

            return new int[1][1];
        }

        @Override
        public void activate() {
            super.activate();
            configureCalled = true;
        }

        public boolean isConfigureCalled() {
            return configureCalled;
        }
    }
}
