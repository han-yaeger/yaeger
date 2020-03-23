package nl.meron.yaeger.engine.entities.tilemap;

import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.entity.sprite.SpriteEntity;
import nl.meron.yaeger.engine.exceptions.EntityNotAvailableException;
import nl.meron.yaeger.engine.exceptions.YaegerEngineException;
import nl.meron.yaeger.engine.scenes.DimensionsProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TileMapTest {

    private TileMapEmptyConstructorImpl sut;

    private static final double WIDTH = 370;
    private static final double HEIGHT = 420;

    @BeforeEach
    void setup() {
        sut = new TileMapEmptyConstructorImpl();
    }

    @Test
    void tileMapIsEqualToSelf() {
        // Arrange
        var sut2 = sut;

        // Act, Assert
        assertTrue(sut.equals(sut2));
    }

    @Test
    void tileMapIsNotEqualToNull() {
        // Arrange Act Assert
        assertFalse(sut.equals(null));
    }

    @Test
    void tileMapIsEqualToDifferentTileMapWithSameEmptyContent() {
        // Arrange
        var sut2 = new TileMapEmptyConstructorImpl();

        // Arc, Assert
        assertTrue(sut.equals(sut2));
    }

    @Test
    void callingConfigureWithoutSettingDimensionProviderOnEmptyConstructorThrowsYaegerException() {
        // Arrange

        // Act, Assert
        assertThrows(YaegerEngineException.class, () -> sut.configure());
    }

    @Test
    void callingConfigureCallsSetupEntities() {
        // Arrange
        var dimensionsProvider = mock(DimensionsProvider.class);
        when(dimensionsProvider.getWidth()).thenReturn(WIDTH);
        when(dimensionsProvider.getHeight()).thenReturn(HEIGHT);
        sut.setDimensionsProvider(dimensionsProvider);

        // Act
        sut.configure();

        // Assert
        assertTrue(sut.isSetupEntitiesCalled());
    }

    @Test
    void callingConfigureCallsDefineMap() {
        // Arrange
        var dimensionsProvider = mock(DimensionsProvider.class);
        when(dimensionsProvider.getWidth()).thenReturn(WIDTH);
        when(dimensionsProvider.getHeight()).thenReturn(HEIGHT);
        sut.setDimensionsProvider(dimensionsProvider);

        // Act
        sut.configure();

        // Assert
        assertTrue(sut.isDefineMapCalled());
    }

    @Test
    void aTileMapWithNoEntitiesCreatesNoInstance() {
        // Arrange
        var localSut = new TileMap() {

            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
                addEntity(2, SpriteEntityTwo.class);
                addEntity(3, SpriteEntityThree.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {
                        {0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0}};
                return map;
            }
        };

        var dimensionsProvider = mock(DimensionsProvider.class);
        when(dimensionsProvider.getWidth()).thenReturn(WIDTH);
        when(dimensionsProvider.getHeight()).thenReturn(HEIGHT);
        localSut.setDimensionsProvider(dimensionsProvider);

        var entity = mock(Entity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.configure();

        // Assert
        assertTrue(localSut.isEmpty());
    }

    @Test
    void aTileMapWithOnlyOneEntityCreatesOnlyOneInstance() {
        // Arrange
        var localSut = new TileMap() {

            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {
                        {0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0}};
                return map;
            }
        };

        var dimensionsProvider = mock(DimensionsProvider.class);
        when(dimensionsProvider.getWidth()).thenReturn(WIDTH);
        when(dimensionsProvider.getHeight()).thenReturn(HEIGHT);
        localSut.setDimensionsProvider(dimensionsProvider);

        var entity = mock(Entity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.configure();

        // Assert
        assertTrue(localSut.size() == 1);
    }

    @Test
    void aTileMapWithMultipleEntitiesCallsTheFactoryTheCorrectNumberTimes() {
        // Arrange
        var localSut = new TileMap() {

            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
                addEntity(2, SpriteEntityTwo.class);
                addEntity(3, SpriteEntityThree.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {
                        {1, 0, 0, 3, 0, 0, 1},
                        {2, 0, 0, 1, 0, 0, 2},
                        {1, 0, 0, 3, 0, 0, 1}};
                return map;
            }
        };

        var dimensionsProvider = mock(DimensionsProvider.class);
        when(dimensionsProvider.getWidth()).thenReturn(WIDTH);
        when(dimensionsProvider.getHeight()).thenReturn(HEIGHT);
        localSut.setDimensionsProvider(dimensionsProvider);

        var entity = mock(Entity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.configure();

        // Assert
        verify(tileFactory, times(9)).create(any(), any(), any());
    }

    @Test
    void aTileMapWithMissingKeysThrowsTheCorrectExceptionWithMessage() {
        // Arrange
        var KEY = 1;
        var localSut = new TileMap() {

            @Override
            public void setupEntities() {
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {
                        {0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, KEY, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0}};
                return map;
            }
        };

        var dimensionsProvider = mock(DimensionsProvider.class);
        when(dimensionsProvider.getWidth()).thenReturn(WIDTH);
        when(dimensionsProvider.getHeight()).thenReturn(HEIGHT);
        localSut.setDimensionsProvider(dimensionsProvider);

        var entity = mock(Entity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act, Assert
        var entityNotAvailableException = assertThrows(EntityNotAvailableException.class, () -> localSut.configure());
        assertTrue(entityNotAvailableException.getMessage().contains(Integer.toString(KEY)));
    }


    private class TileMapEmptyConstructorImpl extends TileMap {

        private boolean setupEntitiesCalled;
        private boolean defineMapCalled;

        @Override
        public void setupEntities() {
            setupEntitiesCalled = true;
        }

        @Override
        public int[][] defineMap() {

            defineMapCalled = true;
            return new int[0][];
        }

        public boolean isSetupEntitiesCalled() {
            return setupEntitiesCalled;
        }

        public boolean isDefineMapCalled() {
            return defineMapCalled;
        }
    }
}
