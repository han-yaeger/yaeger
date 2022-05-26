package com.github.hanyaeger.api.scenes;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.entities.EntityConfiguration;
import com.github.hanyaeger.core.factories.TileFactory;
import com.github.hanyaeger.core.exceptions.EntityNotAvailableException;
import com.github.hanyaeger.core.exceptions.YaegerEngineException;
import com.github.hanyaeger.core.scenes.DimensionsProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TileMapTest {

    private TileMapEmptyConstructorImpl sut;

    private static final double WIDTH = 370;
    private static final double HEIGHT = 420;
    public final static String DEFAULT_RESOURCE = "images/bubble.png";

    private static final Coordinate2D LOCATION = new Coordinate2D(10, 20);
    private static final Size SIZE = new Size(200, 100);

    @BeforeEach
    void setup() {
        sut = new TileMapEmptyConstructorImpl();
    }

    @Nested
    class EqualsTest {

        @Test
        void tileMapIsEqualToSelf() {
            // Arrange
            var sut2 = sut;

            // Act, Assert
            assertEquals(sut, sut2);
        }

        @Test
        void tileMapIsNotEqualToNull() {
            // Arrange, Act, Assert
            assertNotEquals(null, sut);
        }

        @Test
        void tileMapIsEqualToOtherOfSameClassWithSameContentAndLocation() {
            // Arrange
            var localSut1 = new FullyImplementedTileMap();
            var localSut2 = new FullyImplementedTileMap();

            // Act, Assert
            assertEquals(localSut1, localSut2);
        }

        @Test
        void tileMapIsEqualToDifferentTileMapWithSameEmptyContent() {
            // Arrange
            var sut2 = new TileMapEmptyConstructorImpl();

            // Arc, Assert
            assertEquals(sut2, sut);
        }

        @Test
        void tileMapHashIsEqualToDifferentTileMapWithSameEmptyContentHash() {
            // Arrange
            var sut2 = new TileMapEmptyConstructorImpl();

            // Arc, Assert
            assertEquals(sut.hashCode(), sut2.hashCode());
        }
    }

    @Test
    void sizeAndPositionOfTileMapWithNonEmptyConstructorIsUsed() {
        // Arrange

        // Act
        var sutNonEmptyConstructor = new TileMapFilledConstructorImpl(LOCATION, SIZE);

        // Assert
        assertTrue(sutNonEmptyConstructor.getLocation().isPresent());
        assertTrue(sutNonEmptyConstructor.getSize().isPresent());

        assertEquals(LOCATION, sutNonEmptyConstructor.getLocation().get());
        assertEquals(SIZE, sutNonEmptyConstructor.getSize().get());
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
                return new int[][]{
                        {0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0}};
            }
        };

        var dimensionsProvider = mock(DimensionsProvider.class);
        when(dimensionsProvider.getWidth()).thenReturn(WIDTH);
        when(dimensionsProvider.getHeight()).thenReturn(HEIGHT);
        localSut.setDimensionsProvider(dimensionsProvider);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(Class.class), any(), any())).thenReturn(entity);

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
                return new int[][]{
                        {0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0}};
            }
        };

        var dimensionsProvider = mock(DimensionsProvider.class);
        when(dimensionsProvider.getWidth()).thenReturn(WIDTH);
        when(dimensionsProvider.getHeight()).thenReturn(HEIGHT);
        localSut.setDimensionsProvider(dimensionsProvider);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        assertEquals(1, localSut.size());
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
                return new int[][]{
                        {1, 0, 0, 3, 0, 0, 1},
                        {2, 0, 0, 1, 0, 0, 2},
                        {1, 0, 0, 3, 0, 0, 1}};
            }
        };

        var dimensionsProvider = mock(DimensionsProvider.class);
        when(dimensionsProvider.getWidth()).thenReturn(WIDTH);
        when(dimensionsProvider.getHeight()).thenReturn(HEIGHT);
        localSut.setDimensionsProvider(dimensionsProvider);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        verify(tileFactory, times(9)).create(any(EntityConfiguration.class), any(), any());
    }

    @Test
    void tileMapCreatesACorrectInstanceMap() {
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
                return new int[][]{
                        {1, 0, 0, 3, 0, 0, 1},
                        {2, 0, 0, 1, 0, 0, 2},
                        {1, 0, 0, 3, 0, 0, 1}};
            }
        };

        var dimensionsProvider = mock(DimensionsProvider.class);
        when(dimensionsProvider.getWidth()).thenReturn(WIDTH);
        when(dimensionsProvider.getHeight()).thenReturn(HEIGHT);
        localSut.setDimensionsProvider(dimensionsProvider);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        assertNotNull(localSut.getInstanceMap()[0][0]);
        assertNotNull(localSut.getInstanceMap()[0][3]);
        assertNotNull(localSut.getInstanceMap()[0][6]);
        assertNotNull(localSut.getInstanceMap()[1][0]);
        assertNotNull(localSut.getInstanceMap()[1][3]);
        assertNotNull(localSut.getInstanceMap()[1][6]);
        assertNotNull(localSut.getInstanceMap()[2][0]);
        assertNotNull(localSut.getInstanceMap()[2][3]);
        assertNotNull(localSut.getInstanceMap()[2][6]);
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
                return new int[][]{
                        {0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, KEY, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0}};
            }
        };

        var dimensionsProvider = mock(DimensionsProvider.class);
        when(dimensionsProvider.getWidth()).thenReturn(WIDTH);
        when(dimensionsProvider.getHeight()).thenReturn(HEIGHT);
        localSut.setDimensionsProvider(dimensionsProvider);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(Class.class), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act, Assert
        var entityNotAvailableException = assertThrows(EntityNotAvailableException.class, localSut::activate);
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
                return new int[][]{
                        {1}
                };
            }
        };

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        var argument = ArgumentCaptor.forClass(Size.class);
        verify(tileFactory).create(any(EntityConfiguration.class), any(), argument.capture());
        assertEquals(SIZE.height(), argument.getValue().height());
        assertEquals(SIZE.width(), argument.getValue().width());
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
                return new int[][]{{0, 0, 0},
                        {0, 1, 0},
                        {0, 0, 0}
                };
            }
        };

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        var argument = ArgumentCaptor.forClass(Size.class);
        verify(tileFactory).create(any(EntityConfiguration.class), any(), argument.capture());
        assertEquals(Math.ceil(SIZE.height() / 3), argument.getValue().height());
        assertEquals(Math.ceil(SIZE.width() / 3), argument.getValue().width());
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
                return new int[][]{{0, 0, 0},
                        {1},
                        {0, 0, 0}
                };
            }
        };

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        var argument = ArgumentCaptor.forClass(Size.class);
        verify(tileFactory).create(any(EntityConfiguration.class), any(), argument.capture());
        assertEquals(Math.ceil(SIZE.height() / 3), argument.getValue().height());
        assertEquals(SIZE.width(), argument.getValue().width());
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
                return new int[][]{{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
            }
        };

        localSut.setAnchorPoint(AnchorPoint.TOP_LEFT);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        var argument = ArgumentCaptor.forClass(Coordinate2D.class);
        verify(tileFactory).create(any(EntityConfiguration.class), argument.capture(), any());
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
                return new int[][]{{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
            }
        };

        localSut.setAnchorPoint(AnchorPoint.TOP_CENTER);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        var argument = ArgumentCaptor.forClass(Coordinate2D.class);
        verify(tileFactory).create(any(EntityConfiguration.class), argument.capture(), any());

        var expectedX = LOCATION.getX() - SIZE.width() / 2;

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
                return new int[][]{{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
            }
        };

        localSut.setAnchorPoint(AnchorPoint.TOP_RIGHT);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
        verify(tileFactory).create(any(EntityConfiguration.class), argument.capture(), any());

        var expectedX = LOCATION.getX() - SIZE.width();

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
                return new int[][]{{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
            }
        };

        localSut.setAnchorPoint(AnchorPoint.CENTER_LEFT);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
        verify(tileFactory).create(any(EntityConfiguration.class), argument.capture(), any());

        var expectedX = LOCATION.getX();
        var expectedY = LOCATION.getY() - SIZE.height() / 2;

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
                return new int[][]{{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
            }
        };

        localSut.setAnchorPoint(AnchorPoint.CENTER_CENTER);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
        verify(tileFactory).create(any(EntityConfiguration.class), argument.capture(), any());

        var expectedX = LOCATION.getX() - SIZE.width() / 2;
        var expectedY = LOCATION.getY() - SIZE.height() / 2;

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
                return new int[][]{{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
            }
        };

        localSut.setAnchorPoint(AnchorPoint.CENTER_RIGHT);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
        verify(tileFactory).create(any(EntityConfiguration.class), argument.capture(), any());

        var expectedX = LOCATION.getX() - SIZE.width();
        var expectedY = LOCATION.getY() - SIZE.height() / 2;

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
                return new int[][]{{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
            }
        };

        localSut.setAnchorPoint(AnchorPoint.BOTTOM_LEFT);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
        verify(tileFactory).create(any(EntityConfiguration.class), argument.capture(), any());

        var expectedX = LOCATION.getX();
        var expectedY = LOCATION.getY() - SIZE.height();

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
                return new int[][]{{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
            }
        };

        localSut.setAnchorPoint(AnchorPoint.BOTTOM_CENTER);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
        verify(tileFactory).create(any(EntityConfiguration.class), argument.capture(), any());

        var expectedX = LOCATION.getX() - SIZE.width() / 2;
        var expectedY = LOCATION.getY() - SIZE.height();

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
                return new int[][]{{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
            }
        };

        localSut.setAnchorPoint(AnchorPoint.BOTTOM_RIGHT);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        localSut.setTileFactory(tileFactory);

        // Act
        localSut.activate();

        // Assert
        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
        verify(tileFactory).create(any(EntityConfiguration.class), argument.capture(), any());

        var expectedX = LOCATION.getX() - SIZE.width();
        var expectedY = LOCATION.getY() - SIZE.height();

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
                return new int[][]{{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
            }
        };

        horizontalSut.setAnchorPoint(AnchorPoint.TOP_LEFT);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        horizontalSut.setTileFactory(tileFactory);

        // Act
        horizontalSut.activate();

        // Assert

        ArgumentCaptor<Coordinate2D> argument = ArgumentCaptor.forClass(Coordinate2D.class);
        verify(tileFactory, times(15)).create(any(EntityConfiguration.class), argument.capture(), any());

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
                return new int[][]{{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
            }
        };

        horizontalSut.setAnchorPoint(AnchorPoint.TOP_LEFT);

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        horizontalSut.setTileFactory(tileFactory);

        // Act
        horizontalSut.activate();

        // Assert

        ArgumentCaptor<Size> argument = ArgumentCaptor.forClass(Size.class);
        verify(tileFactory, times(15)).create(any(EntityConfiguration.class), any(), argument.capture());

        List<Size> capturedSizes = argument.getAllValues();

        for (int i = 0; i < 15; i++) {
            assertEquals(expectedWidth, capturedSizes.get(i).width());
            assertEquals(expectedHeight, capturedSizes.get(i).height());
        }
    }

    @Test
    void addConfigurableEntityShouldCallTileFactoryWithEntityConfiguration() {
        // Arrange
        var configuredTileSut = new TileMap(LOCATION, SIZE) {
            @Override
            public void setupEntities() {
                addEntity(1, YaegerEntity.class, "config");
            }

            @Override
            public int[][] defineMap() {
                return new int[][]{{1}};
            }
        };

        var entity = mock(YaegerEntity.class);
        var tileFactory = mock(TileFactory.class);
        when(tileFactory.create(any(EntityConfiguration.class), any(), any())).thenReturn(entity);

        configuredTileSut.setTileFactory(tileFactory);
        ArgumentCaptor<EntityConfiguration> argument = ArgumentCaptor.forClass(EntityConfiguration.class);

        // Act
        configuredTileSut.activate();

        // Assert
        verify(tileFactory, times(1)).create(argument.capture(), any(), any());
        var entityConfiguration = argument.getValue();
        assertEquals(YaegerEntity.class, entityConfiguration.getEntityClass());
        assertEquals("config", entityConfiguration.getConfiguration());
    }

    @Test
    void tileMapIsEqualToSelf() {
        // Arrange

        // Act
        var equals = sut.equals(sut);

        // Assert
        assertTrue(equals);
    }

    @Test
    void tileMapIsNotEqualToNull() {
        // Arrange

        // Act
        var equals = sut.equals(null);

        // Assert
        assertFalse(equals);
    }

    @Test
    void tileMapIsNotEqualToInstanceOfOtherClass() {
        // Arrange

        // Act
        var equals = sut.equals(new ArrayList<String>());

        // Assert
        assertFalse(equals);
    }

    private static class TileMapEmptyConstructorImpl extends TileMap {

        private boolean setupEntitiesCalled;
        private boolean defineMapCalled;

        @Override
        public void setupEntities() {
            setupEntitiesCalled = true;
        }

        @Override
        public int[][] defineMap() {
            defineMapCalled = true;
            return new int[1][1];
        }

        public boolean isSetupEntitiesCalled() {
            return setupEntitiesCalled;
        }

        public boolean isDefineMapCalled() {
            return defineMapCalled;
        }
    }

    private static class TileMapFilledConstructorImpl extends TileMap {

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

    private static class FullyImplementedTileMap extends TileMap {

        FullyImplementedTileMap() {
            super(new Coordinate2D(37, 37), new Size(42, 42));
        }

        @Override
        public void setupEntities() {
            addEntity(1, SpriteEntityOne.class);
            addEntity(2, SpriteEntityTwo.class);
            addEntity(3, SpriteEntityThree.class);
        }

        @Override
        public int[][] defineMap() {
            return new int[][]{
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0}};
        }
    }
}
