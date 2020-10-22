package com.github.hanyaeger.api.engine.entities.entity.sprite;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.events.system.RemoveEntityEvent;
import com.github.hanyaeger.api.engine.entities.entity.sprite.delegates.SpriteAnimationDelegate;
import com.github.hanyaeger.api.engine.media.repositories.ImageRepository;
import com.github.hanyaeger.api.guice.factories.SpriteAnimationDelegateFactory;
import com.github.hanyaeger.api.javafx.image.ImageViewFactory;
import com.google.inject.Injector;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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
    private SpriteAnimationDelegate spriteAnimationDelegate;
    private ImageRepository imageRepository;
    private Injector injector;

    @BeforeEach
    void setup() {
        imageViewFactory = mock(ImageViewFactory.class);
        spriteAnimationDelegate = mock(SpriteAnimationDelegate.class);
        spriteAnimationDelegateFactory = mock(SpriteAnimationDelegateFactory.class);
        when(spriteAnimationDelegateFactory.create(any(ImageView.class), anyInt())).thenReturn(spriteAnimationDelegate);

        imageRepository = mock(ImageRepository.class);
        injector = mock(Injector.class);
    }

    @Test
    void setAnchorLocationSetsAnchorLocationOnNode() {
        // Arrange
        var sut = new SpriteEntityWithDefaultFramesImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE);
        sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
        sut.setImageRepository(imageRepository);
        sut.setImageViewFactory(imageViewFactory);

        var image = mock(Image.class);
        when(imageRepository.get(DEFAULT_RESOURCE, WIDTH, HEIGHT, true)).thenReturn(image);

        var imageView = mock(ImageView.class);
        when(imageViewFactory.create(image)).thenReturn(imageView);

        var expected = new Coordinate2D(1.1, 2.2);

        sut.init(injector);

        // Act
        sut.setAnchorLocation(expected);

        // Assert
        verify(imageView).setX(expected.getX());
        verify(imageView).setY(expected.getY());
    }

    @Test
    void getNodeReturnsEmptyNodeIfNodeNotSet() {
        // Arrange
        var sut = new SpriteEntityWithDefaultFramesImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE);

        // Act
        var gameNode = sut.getNode();

        // Assert
        Assertions.assertTrue(gameNode.isEmpty());
    }

    @Test
    void instantiatingASpriteEntityWithOneFrameGivesNoSideEffects() {
        // Setup

        // Test
        var sut = new SpriteEntityWithDefaultFramesImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE);

        // Assert
        Assertions.assertNotNull(sut);
    }

    @Test
    void instantiatingASpriteEntityWithTwoFrameGivesNoSideEffects() {
        // Setup

        // Test
        var sut = new SpriteEntityWithTwoFramesImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE, 2);

        // Assert
        Assertions.assertNotNull(sut);
    }

    @Test
    void callingInitAfterInstantiatingWithSingleFrameImageWiresDelegates() {
        // Setup
        var sut = new SpriteEntityWithDefaultFramesImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE);
        sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
        sut.setImageRepository(imageRepository);
        sut.setImageViewFactory(imageViewFactory);

        var image = mock(Image.class);
        when(imageRepository.get(DEFAULT_RESOURCE, WIDTH, HEIGHT, true)).thenReturn(image);

        var imageView = mock(ImageView.class);
        when(imageViewFactory.create(image)).thenReturn(imageView);

        // Test
        sut.init(injector);

        // Assert
        verifyNoMoreInteractions(spriteAnimationDelegateFactory);
    }

    @Test
    void callingInitAfterInstantiatingWithDoubleFrameImageWiresDelegates() {
        // Setup
        var spriteEntity = new SpriteEntityWithTwoFramesImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE, 2);
        spriteEntity.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
        spriteEntity.setImageRepository(imageRepository);
        spriteEntity.setImageViewFactory(imageViewFactory);

        var image = mock(Image.class);
        when(imageRepository.get(DEFAULT_RESOURCE, WIDTH * 2, HEIGHT, true)).thenReturn(image);

        var imageView = mock(ImageView.class);
        when(imageViewFactory.create(image)).thenReturn(imageView);

        // Test
        spriteEntity.init(injector);

        // Assert
        verify(spriteAnimationDelegateFactory).create(imageView, 2);
    }

    @Test
    void removingAnEntitySetsImageViewCorrectly() {
        // Setup
        var spriteEntity = new SpriteEntityWithDefaultFramesImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE);
        spriteEntity.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
        spriteEntity.setImageRepository(imageRepository);
        spriteEntity.setImageViewFactory(imageViewFactory);

        var image = mock(Image.class);
        when(imageRepository.get(DEFAULT_RESOURCE, WIDTH, HEIGHT, true)).thenReturn(image);

        var imageView = mock(ImageView.class);
        when(imageViewFactory.create(image)).thenReturn(imageView);
        spriteEntity.init(injector);

        // Test
        spriteEntity.remove();

        // Assert
        verify(imageView).setImage(null);
        verify(imageView).setVisible(false);
        verify(imageView).fireEvent(any(RemoveEntityEvent.class));
    }

    @Test
    void setPreserveAspectRatioDelegatesToTheImageRepository() {
        // Setup
        var sut = new SpriteEntityWithDefaultFramesImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE);
        sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
        sut.setImageRepository(imageRepository);
        sut.setImageViewFactory(imageViewFactory);

        var image = mock(Image.class);
        when(imageRepository.get(anyString(), anyDouble(), anyDouble(), anyBoolean())).thenReturn(image);

        var imageView = mock(ImageView.class);
        when(imageViewFactory.create(image)).thenReturn(imageView);

        // Test
        sut.setPreserveAspectRatio(false);
        sut.init(injector);

        // Assert
        verify(imageRepository).get(DEFAULT_RESOURCE, WIDTH, HEIGHT, false);
    }

    @Test
    void setFrameIndexBeforeInitCalledBufferesIndexAndDelegatesToSpriteAnimationDelegateAtInit() {
        // Setup
        var spriteEntity = new SpriteEntityWithTwoFramesImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE, 2);
        spriteEntity.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
        spriteEntity.setImageRepository(imageRepository);
        spriteEntity.setImageViewFactory(imageViewFactory);

        var frames = 2;
        var image = mock(Image.class);
        when(imageRepository.get(DEFAULT_RESOURCE, WIDTH * frames, HEIGHT, true)).thenReturn(image);

        var imageView = mock(ImageView.class);
        when(imageViewFactory.create(image)).thenReturn(imageView);


        var spriteAnimationDelegate = mock(SpriteAnimationDelegate.class);
        when(spriteAnimationDelegateFactory.create(imageView, 2)).thenReturn(spriteAnimationDelegate);

        // Test
        spriteEntity.setCurrentFrameIndex(frames);
        spriteEntity.init(injector);

        // Assert
        verify(spriteAnimationDelegate).setSpriteIndex(frames);
    }

    @Test
    void setFrameIndexDelegatesToSpriteAnimationDelegate() {
        // Setup
        var spriteEntity = new SpriteEntityWithTwoFramesImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE, 2);
        spriteEntity.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
        spriteEntity.setImageRepository(imageRepository);
        spriteEntity.setImageViewFactory(imageViewFactory);

        var frames = 2;
        var image = mock(Image.class);
        when(imageRepository.get(DEFAULT_RESOURCE, WIDTH * frames, HEIGHT, true)).thenReturn(image);

        var imageView = mock(ImageView.class);
        when(imageViewFactory.create(image)).thenReturn(imageView);


        var spriteAnimationDelegate = mock(SpriteAnimationDelegate.class);
        when(spriteAnimationDelegateFactory.create(imageView, 2)).thenReturn(spriteAnimationDelegate);

        spriteEntity.init(injector);

        // Test
        spriteEntity.setCurrentFrameIndex(frames);

        // Assert
        verify(spriteAnimationDelegate).setSpriteIndex(frames);
    }

    private class SpriteEntityWithDefaultFramesImpl extends SpriteEntity {

        SpriteEntityWithDefaultFramesImpl(String resource, Coordinate2D location, Size size) {
            super(resource, location, size);
        }
    }

    private class SpriteEntityWithTwoFramesImpl extends SpriteEntity {

        SpriteEntityWithTwoFramesImpl(String resource, Coordinate2D location, Size size, int frames) {
            super(resource, location, size, frames);
        }
    }
}
