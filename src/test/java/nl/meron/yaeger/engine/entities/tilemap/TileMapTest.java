package nl.meron.yaeger.engine.entities.tilemap;

import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.AnchorPoint;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.YaegerEntity;
import nl.meron.yaeger.engine.exceptions.EntityNotAvailableException;
import nl.meron.yaeger.engine.exceptions.YaegerEngineException;
import nl.meron.yaeger.engine.scenes.DimensionsProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TileMapTest {

    private TileMapEmptyConstructorImpl sut;

    private static final double WIDTH = 370;
    private static final double HEIGHT = 420;

    private static final Location LOCATION = new Location(10, 20);
    private static final Size SIZE = new Size(200, 100);

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
    void tileMapHashIsEqualToDifferentTileMapWithSameEmptyContentHash() {
        // Arrange
        var sut2 = new TileMapEmptyConstructorImpl();

        // Arc, Assert
        assertEquals(sut.hashCode(), sut2.hashCode());
    }

    @Test
    void sizeAndPositionOfTilemapWithNonEmptyConstructorIsUsed() {
        // Arrange

        // Act
        var sutNonEmptyConstructor = new TileMapFilledConstructorImpl(LOCATION, SIZE);

        // Assert
        assertTrue(sutNonEmptyConstructor.getLocation().isPresent());
        assertTrue(sutNonEmptyConstructor.getSize().isPresent());

        assertEquals(sutNonEmptyConstructor.getLocation().get(), LOCATION);
        assertEquals(sutNonEmptyConstructor.getSize().get(), SIZE);
    }

    @Test
    void callingConfigureWithoutSettingDimensionProviderOnEmptyConstructorThrowsYaegerException() {
        // Arrange

        // Act, Assert
        assertThrows(YaegerEngineException.class, () -> sut.activate());
    }

    @Test
    void callingConfigureCallsSetupEntities() {
        // Arrange
        var dimensionsProvider = mock(DimensionsProvider.class);
        when(dimensionsProvider.getWidth()).thenReturn(WIDTH);
        when(dimensionsProvider.getHeight()).thenReturn(HEIGHT);
        sut.setDimensionsProvider(dimensionsProvider);

        // Act
        sut.activate();

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
        sut.activate();

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

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

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

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

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

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

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

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act, Assert
        var entityNotAvailableException = assertThrows(EntityNotAvailableException.class, () -> localSut.activate());
        assertTrue(entityNotAvailableException.getMessage().contains(Integer.toString(KEY)));
    }

    @Test
    void inAOneByOneTileMapTheEntityGetsFullWidthAndHeight() {
        // Arrange
        var localSut = new TileMap(LOCATION, SIZE) {

            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {
                        {1}
                };
                return map;
            }
        };

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Size> argument = ArgumentCaptor.forClass(Size.class);
        verify(tileFactory).create(any(), any(), argument.capture());
        assertEquals(SIZE.getHeight(), argument.getValue().getHeight());
        assertEquals(SIZE.getWidth(), argument.getValue().getWidth());
    }

    @Test
    void anchorPointIsAvailableThroughGetter() {
        // Arrange
        var expected = AnchorPoint.CENTER_CENTER;
        sut.setAnchorPoint(expected);

        // Act
        var actual = sut.getAnchorPoint();

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    void inARectangularTileMapTheEntityGetsCorrectWidthAndHeight() {
        // Arrange
        var localSut = new TileMap(LOCATION, SIZE) {

            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {{0, 0, 0},
                        {0, 1, 0},
                        {0, 0, 0}
                };
                return map;
            }
        };

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Size> argument = ArgumentCaptor.forClass(Size.class);
        verify(tileFactory).create(any(), any(), argument.capture());
        assertEquals(SIZE.getHeight() / 3, argument.getValue().getHeight());
        assertEquals(SIZE.getWidth() / 3, argument.getValue().getWidth());
    }

    @Test
    void inANonRectangularTileMapTheEntityGetsCorrectWidthAndHeight() {
        // Arrange
        var localSut = new TileMap(LOCATION, SIZE) {

            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {{0, 0, 0},
                        {1},
                        {0, 0, 0}
                };
                return map;
            }
        };

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Size> argument = ArgumentCaptor.forClass(Size.class);
        verify(tileFactory).create(any(), any(), argument.capture());
        assertEquals(SIZE.getHeight() / 3, argument.getValue().getHeight());
        assertEquals(SIZE.getWidth(), argument.getValue().getWidth());
    }

    @Test
    void topLeftAnchoringPutsTopLeftTileOnOrigin() {
        // Arrange
        var localSut = new TileMap(LOCATION, SIZE) {

            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
                return map;
            }
        };

        localSut.setAnchorPoint(AnchorPoint.TOP_LEFT);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Location> argument = ArgumentCaptor.forClass(Location.class);
        verify(tileFactory).create(any(), argument.capture(), any());
        assertEquals(LOCATION.getX(), argument.getValue().getX());
        assertEquals(LOCATION.getY(), argument.getValue().getY());
    }

    @Test
    void topCenterAnchoringPutsTopLeftTileOnCorrectLocation() {
        // Arrange
        var localSut = new TileMap(LOCATION, SIZE) {

            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
                return map;
            }
        };

        localSut.setAnchorPoint(AnchorPoint.TOP_CENTER);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Location> argument = ArgumentCaptor.forClass(Location.class);
        verify(tileFactory).create(any(), argument.capture(), any());

        var expectedX = LOCATION.getX() - SIZE.getWidth() / 2;

        assertEquals(expectedX, argument.getValue().getX());
        assertEquals(LOCATION.getY(), argument.getValue().getY());
    }

    @Test
    void topRightAnchoringPutsTopLeftTileOnCorrectLocation() {
        // Arrange
        var localSut = new TileMap(LOCATION, SIZE) {

            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
                return map;
            }
        };

        localSut.setAnchorPoint(AnchorPoint.TOP_RIGHT);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Location> argument = ArgumentCaptor.forClass(Location.class);
        verify(tileFactory).create(any(), argument.capture(), any());

        var expectedX = LOCATION.getX() - SIZE.getWidth();

        assertEquals(expectedX, argument.getValue().getX());
        assertEquals(LOCATION.getY(), argument.getValue().getY());
    }

    @Test
    void leftCenterAnchoringPutsTopLeftTileCorrectLocation() {
        // Arrange
        var localSut = new TileMap(LOCATION, SIZE) {

            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
                return map;
            }
        };

        localSut.setAnchorPoint(AnchorPoint.CENTER_LEFT);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Location> argument = ArgumentCaptor.forClass(Location.class);
        verify(tileFactory).create(any(), argument.capture(), any());

        var expectedX = LOCATION.getX();
        var expectedY = LOCATION.getY() - SIZE.getHeight() / 2;

        assertEquals(expectedX, argument.getValue().getX());
        assertEquals(expectedY, argument.getValue().getY());
    }

    @Test
    void centerCenterAnchoringPutsTopLeftTileOnCorrectLocation() {
        // Arrange
        var localSut = new TileMap(LOCATION, SIZE) {

            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
                return map;
            }
        };

        localSut.setAnchorPoint(AnchorPoint.CENTER_CENTER);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Location> argument = ArgumentCaptor.forClass(Location.class);
        verify(tileFactory).create(any(), argument.capture(), any());

        var expectedX = LOCATION.getX() - SIZE.getWidth() / 2;
        var expectedY = LOCATION.getY() - SIZE.getHeight() / 2;

        assertEquals(expectedX, argument.getValue().getX());
        assertEquals(expectedY, argument.getValue().getY());
    }

    @Test
    void centerRightAnchoringPutsTopLeftTileOnCorrectLocation() {
        // Arrange
        var localSut = new TileMap(LOCATION, SIZE) {

            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
                return map;
            }
        };

        localSut.setAnchorPoint(AnchorPoint.CENTER_RIGHT);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Location> argument = ArgumentCaptor.forClass(Location.class);
        verify(tileFactory).create(any(), argument.capture(), any());

        var expectedX = LOCATION.getX() - SIZE.getWidth();
        var expectedY = LOCATION.getY() - SIZE.getHeight() / 2;

        assertEquals(expectedX, argument.getValue().getX());
        assertEquals(expectedY, argument.getValue().getY());
    }

    // --------

    @Test
    void bottomLeftAnchoringPutsTopLeftTileOnCorrectLocation() {
        // Arrange
        var localSut = new TileMap(LOCATION, SIZE) {

            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
                return map;
            }
        };

        localSut.setAnchorPoint(AnchorPoint.BOTTOM_LEFT);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Location> argument = ArgumentCaptor.forClass(Location.class);
        verify(tileFactory).create(any(), argument.capture(), any());

        var expectedX = LOCATION.getX();
        var expectedY = LOCATION.getY() - SIZE.getHeight();

        assertEquals(expectedX, argument.getValue().getX());
        assertEquals(expectedY, argument.getValue().getY());
    }

    @Test
    void bottomCenterAnchoringPutsTopLeftTileOnCorrectLocation() {
        // Arrange
        var localSut = new TileMap(LOCATION, SIZE) {

            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
                return map;
            }
        };

        localSut.setAnchorPoint(AnchorPoint.BOTTOM_CENTER);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Location> argument = ArgumentCaptor.forClass(Location.class);
        verify(tileFactory).create(any(), argument.capture(), any());

        var expectedX = LOCATION.getX() - SIZE.getWidth() / 2;
        var expectedY = LOCATION.getY() - SIZE.getHeight();

        assertEquals(expectedX, argument.getValue().getX());
        assertEquals(expectedY, argument.getValue().getY());
    }

    @Test
    void bottomRightAnchoringPutsTopLeftTileOnCorrectLocation() {
        // Arrange
        var localSut = new TileMap(LOCATION, SIZE) {

            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
                return map;
            }
        };

        localSut.setAnchorPoint(AnchorPoint.BOTTOM_RIGHT);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Location> argument = ArgumentCaptor.forClass(Location.class);
        verify(tileFactory).create(any(), argument.capture(), any());

        var expectedX = LOCATION.getX() - SIZE.getWidth();
        var expectedY = LOCATION.getY() - SIZE.getHeight();

        assertEquals(expectedX, argument.getValue().getX());
        assertEquals(expectedY, argument.getValue().getY());
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

    private class TileMapFilledConstructorImpl extends TileMap {

        public TileMapFilledConstructorImpl(final Location location, final Size size) {
            super(location, size);
        }

        @Override
        public void setupEntities() {

        }

        @Override
        public int[][] defineMap() {
            return new int[0][];
        }

        public Optional<Size> getSize() {
            return size;
        }

        public Optional<Location> getLocation() {
            return location;
        }
    }
}
