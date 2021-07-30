package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.entities.events.RemoveEntityEvent;
import com.github.hanyaeger.core.entities.SpriteAnimationDelegate;
import com.github.hanyaeger.core.repositories.ImageRepository;
import com.github.hanyaeger.core.factories.image.SpriteAnimationDelegateFactory;
import com.github.hanyaeger.core.factories.image.ImageViewFactory;
import com.google.inject.Injector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpriteEntityTest {

    private final static String DEFAULT_RESOURCE = "images/bubble.png";
    private final static int X_POSITION = 37;
    private final static int Y_POSITION = 37;
    private final static Coordinate2D DEFAULT_LOCATION = new Coordinate2D(X_POSITION, Y_POSITION);
    private final static int WIDTH = 39;
    private final static int HEIGHT = 41;
    private final static Size DEFAULT_SIZE = new Size(WIDTH, HEIGHT);

    private SpriteAnimationDelegateFactory spriteAnimationDelegateFactory;
    private ImageViewFactory imageViewFactory;
    private ImageView imageView;
    private Image defaultImage;
    private SpriteAnimationDelegate spriteAnimationDelegate;
    private ImageRepository imageRepository;
    private Injector injector;

    @BeforeEach
    void setup() {
        defaultImage = mock(Image.class);
        imageView = mock(ImageView.class);
        imageViewFactory = mock(ImageViewFactory.class);
        when(imageViewFactory.create(defaultImage)).thenReturn(imageView);
        spriteAnimationDelegate = mock(SpriteAnimationDelegate.class);
        spriteAnimationDelegateFactory = mock(SpriteAnimationDelegateFactory.class);
        when(spriteAnimationDelegateFactory.create(any(ImageView.class), anyInt(), anyInt())).thenReturn(spriteAnimationDelegate);

        imageRepository = mock(ImageRepository.class);
        when(imageRepository.get(DEFAULT_RESOURCE)).thenReturn(defaultImage);

        injector = mock(Injector.class);
    }

    @Test
    void getNodeReturnsEmptyNodeIfNodeNotSet() {
        // Arrange
        var sut = new SpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION);

        // Act
        var gameNode = sut.getNode();

        // Assert
        assertTrue(gameNode.isEmpty());
    }

    @Test
    void instantiatingASpriteEntityWithOneFrameGivesNoSideEffects() {
        // Arrange

        // Act
        var sut = new SpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE);

        // Assert
        assertNotNull(sut);
    }

    @Test
    void instantiatingASpriteEntityWithTwoFrameGivesNoSideEffects() {
        // Arrange

        // Act
        var sut = new SpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE, 1, 2);

        // Assert
        assertNotNull(sut);
    }

    @Test
    void instantiatingASpriteEntityWithTwoFrameAndDefaultSizeGivesNoSideEffects() {
        // Arrange

        // Act
        var sut = new SpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, 1, 2);

        // Assert
        assertNotNull(sut);
    }

    @Test
    void instantiatingASpriteWithoutSizeShouldUseOriginalImageDimensions() {
        // Arrange
        var sut = new SpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION);
        when(defaultImage.getWidth()).thenReturn((double) WIDTH);
        when(defaultImage.getHeight()).thenReturn((double) HEIGHT);
        sut.setImageRepository(imageRepository);
        sut.setImageViewFactory(imageViewFactory);
        sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);

        // Act
        sut.init(injector);

        // Assert
        verify(defaultImage).getWidth();
        verify(defaultImage).getHeight();
    }


    @Nested
    class OneFrameSprite {
        private SpriteEntityImpl sut;


        @BeforeEach
        void setup() {
            sut = new SpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE);

            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setImageRepository(imageRepository);
            sut.setImageViewFactory(imageViewFactory);

            var image = mock(Image.class);
            when(imageRepository.get(anyString(), anyDouble(), anyDouble(), anyBoolean())).thenReturn(image);
            imageView = mock(ImageView.class);
            when(imageViewFactory.create(image)).thenReturn(imageView);
        }

        @Test
        void callingInitAfterInstantiatingWithSingleFrameImageWiresDelegates() {
            // Arrange

            // Act
            sut.init(injector);

            // Assert
            verifyNoMoreInteractions(spriteAnimationDelegateFactory);
        }

        @Test
        void setAnchorLocationSetsAnchorLocationOnNode() {
            // Arrange
            var expected = new Coordinate2D(1.1, 2.2);
            sut.init(injector);

            // Act
            sut.setAnchorLocation(expected);

            // Assert
            verify(imageView).setX(expected.getX());
            verify(imageView).setY(expected.getY());
        }

        @Test
        void setPreserveAspectRatioDelegatesToTheImageRepository() {
            // Arrange

            // Act
            sut.setPreserveAspectRatio(false);
            sut.init(injector);

            // Assert
            verify(imageRepository).get(DEFAULT_RESOURCE, WIDTH, HEIGHT, false);
        }

        @Test
        void removingAnEntitySetsImageViewCorrectly() {
            // Arrange
            sut.init(injector);

            // Act
            sut.remove();

            // Assert
            verify(imageView).setImage(null);
            verify(imageView).setVisible(false);
            verify(imageView).fireEvent(any(RemoveEntityEvent.class));
        }
    }

    @Nested
    class TwoFramesSprite {

        private static final int ROWS = 1;
        private static final int COLUMNS = 2;
        private SpriteEntityImpl sut;

        @BeforeEach
        void setup() {
            sut = new SpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE, ROWS, COLUMNS);

            var image = mock(Image.class);
            when(imageRepository.get(DEFAULT_RESOURCE, WIDTH * COLUMNS, HEIGHT, true)).thenReturn(image);
            imageView = mock(ImageView.class);
            when(imageViewFactory.create(image)).thenReturn(imageView);
            when(spriteAnimationDelegateFactory.create(imageView, 1, COLUMNS)).thenReturn(spriteAnimationDelegate);

            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setImageRepository(imageRepository);
            sut.setImageViewFactory(imageViewFactory);
        }

        @Test
        void callingInitAfterInstantiatingWithDoubleFrameImageWiresDelegates() {
            // Arrange

            // Act
            sut.init(injector);

            // Assert
            verify(spriteAnimationDelegateFactory).create(imageView, ROWS, COLUMNS);
        }

        @Test
        void setFrameIndexBeforeInitCalledBufferesIndexAndDelegatesToSpriteAnimationDelegateAtInit() {
            // Arrange

            // Act
            sut.setCurrentFrameIndex(COLUMNS);
            sut.init(injector);

            // Assert
            verify(spriteAnimationDelegate).setFrameIndex(COLUMNS);
        }


        @Test
        void setFrameIndexDelegatesToSpriteAnimationDelegate() {
            // Arrange
            sut.init(injector);

            // Act
            sut.setCurrentFrameIndex(COLUMNS);

            // Assert
            verify(spriteAnimationDelegate).setFrameIndex(COLUMNS);
        }

        @Test
        void getCurrentFrameIndexDelegatesToSpriteAnimationDelegate() {
            // Arrange
            sut.init(injector);
            sut.setCurrentFrameIndex(COLUMNS);

            // Act
            var currentFrameIndex = sut.getCurrentFrameIndex();

            // Assert
            verify(spriteAnimationDelegate).getFrameIndex();
        }

        @Test
        void getCurrentFrameIndexBeforeInitCalledReturns0IfNoFrameIndexSet() {
            // Arrange

            // Act
            var currentFrameIndex = sut.getCurrentFrameIndex();

            // Assert
            assertEquals(0, currentFrameIndex);
        }

        @Test
        void getCurrentFrameIndexBeforeInitCalledReturnsSetFrameIndex() {
            // Arrange
            sut.setCurrentFrameIndex(COLUMNS);

            // Act
            var currentFrameIndex = sut.getCurrentFrameIndex();

            // Assert
            assertEquals(COLUMNS, currentFrameIndex);
        }
    }

    @Nested
    class GridSprite {

        private SpriteEntityImpl sut;

        private static final int ROWS = 3;
        private static final int COLUMNS = 4;

        @BeforeEach
        void setup() {
            sut = new SpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE, ROWS, COLUMNS);

            var image = mock(Image.class);
            when(imageRepository.get(DEFAULT_RESOURCE, WIDTH * COLUMNS, HEIGHT * ROWS, true)).thenReturn(image);
            imageView = mock(ImageView.class);
            when(imageViewFactory.create(image)).thenReturn(imageView);
            when(spriteAnimationDelegateFactory.create(imageView, ROWS, COLUMNS)).thenReturn(spriteAnimationDelegate);

            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setImageRepository(imageRepository);
            sut.setImageViewFactory(imageViewFactory);
        }

        @Test
        void getFramesShouldReturnRowsTimesColumns() {
            // Arrange

            // Act
            var result = sut.getFrames();

            // Assert
            assertEquals(ROWS * COLUMNS, result);
        }
    }

    private class SpriteEntityImpl extends SpriteEntity {

        SpriteEntityImpl(String resource, Coordinate2D location) {
            super(resource, location);
        }

        SpriteEntityImpl(String resource, Coordinate2D location, int rows, int columns) {
            super(resource, location, rows, columns);
        }

        SpriteEntityImpl(String resource, Coordinate2D location, Size size) {
            super(resource, location, size);
        }

        SpriteEntityImpl(String resource, Coordinate2D location, Size size, int rows, int columns) {
            super(resource, location, size, rows, columns);
        }
    }
}
