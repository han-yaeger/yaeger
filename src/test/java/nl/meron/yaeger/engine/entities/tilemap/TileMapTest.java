package nl.meron.yaeger.engine.entities.tilemap;

import nl.meron.yaeger.engine.exceptions.YaegerEngineException;
import nl.meron.yaeger.engine.scenes.DimensionsProvider;
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

    private class TileMapNonEmptyConstructor extends TileMap {

        @Override
        public void setupEntities() {

        }

        @Override
        public int[][] defineMap() {
            return new int[0][];
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
}
