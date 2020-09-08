package com.github.hanyaeger.api.engine.entities.tilemap;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.entities.entity.AnchorPoint;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.exceptions.EntityNotAvailableException;
import com.github.hanyaeger.api.engine.exceptions.YaegerEngineException;
import com.github.hanyaeger.api.engine.scenes.DimensionsProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TileMapTest {

    private TileMapEmptyConstructorImpl sut;

    private static final double WIDTH = 370;
    private static final double HEIGHT = 420;

    private static final Coordinate2D LOCATION = new Coordinate2D(10, 20);
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
    void tileMapIsNotEqualToDifferentTileMapWithSameEmptyContent() {
        // Arrange
        var sut2 = new TileMapEmptyConstructorImpl();

        // Arc, Assert
        assertFalse(sut.equals(sut2));
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
        Assertions.assertTrue(localSut.isEmpty());
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
        Assertions.assertEquals(actual, expected);
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
        assertEquals(Math.ceil(SIZE.getHeight() / 3), argument.getValue().getHeight());
        assertEquals(Math.ceil(SIZE.getWidth() / 3), argument.getValue().getWidth());
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
        assertEquals(Math.ceil(SIZE.getHeight() / 3), argument.getValue().getHeight());
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
        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
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
        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
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
        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
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
        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
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
        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
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
        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
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
        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
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
        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
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
        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
        verify(tileFactory).create(any(), argument.capture(), any());

        var expectedX = LOCATION.getX() - SIZE.getWidth();
        var expectedY = LOCATION.getY() - SIZE.getHeight();

        assertEquals(expectedX, argument.getValue().getX());
        assertEquals(expectedY, argument.getValue().getY());
    }

    @Test
    void tilesPlacedAtCorrectCoordinates() {
        // Arrange
        var sceneWidth = 1024d;
        var sceneHeight = 10d;

        var tileWidth = sceneWidth / 15d;

        var horizontalSut = new TileMap(new Coordinate2D(0, 0), new Size(sceneWidth, sceneHeight)) {
            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
                return map;
            }
        };

        horizontalSut.setAnchorPoint(AnchorPoint.TOP_LEFT);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        horizontalSut.setTileFactory(tileFactory);

        // Act
        horizontalSut.activate();

        // Assert

        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
        verify(tileFactory, times(15)).create(any(), argument.capture(), any());

        List<Coordinate2D> capturedCoordinates = argument.getAllValues();

        for (int i = 0; i < 15; i++) {
            assertEquals(Math.round(0 + (i * tileWidth)), capturedCoordinates.get(i).getX());
            assertEquals(0, capturedCoordinates.get(i).getY());
        }
    }

    @Test
    void tilesCreatedWithCorrectSize() {
        // Arrange
        var sceneWidth = 1024d;
        var sceneHeight = 10d;

        var expectedWidth = Math.ceil(sceneWidth / 15d);
        var expectedHeight = 10;

        var horizontalSut = new TileMap(new Coordinate2D(0, 0), new Size(sceneWidth, sceneHeight)) {
            @Override
            public void setupEntities() {
                addEntity(1, SpriteEntityOne.class);
            }

            @Override
            public int[][] defineMap() {
                int[][] map = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
                return map;
            }
        };

        horizontalSut.setAnchorPoint(AnchorPoint.TOP_LEFT);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(), any(), any())).thenReturn(entity);

        horizontalSut.setTileFactory(tileFactory);

        // Act
        horizontalSut.activate();

        // Assert

        ArgumentCaptor<Size> argument = ArgumentCaptor.forClass(Size.class);
        verify(tileFactory, times(15)).create(any(), any(), argument.capture());

        List<Size> capturedSizes = argument.getAllValues();

        for (int i = 0; i < 15; i++) {
            assertEquals(expectedWidth, capturedSizes.get(i).getWidth());
            assertEquals(expectedHeight, capturedSizes.get(i).getHeight());
        }
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

        public TileMapFilledConstructorImpl(final Coordinate2D location, final Size size) {
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

        public Optional<Coordinate2D> getLocation() {
            return location;
        }
    }
}
