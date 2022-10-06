package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.FiniteAnimation;
import com.github.hanyaeger.core.Updater;
import com.github.hanyaeger.core.entities.EntityCollection;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.entities.SpriteAnimationDelegate;
import com.github.hanyaeger.core.entities.motion.EntityMotionInitBuffer;
import com.github.hanyaeger.core.entities.motion.MotionApplier;
import com.github.hanyaeger.core.repositories.ImageRepository;
import com.github.hanyaeger.core.factories.image.SpriteAnimationDelegateFactory;
import com.github.hanyaeger.core.factories.image.ImageViewFactory;
import com.google.inject.Injector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DynamicSpriteEntityTest {

    private final static String DEFAULT_RESOURCE = "images/bubble.png";
    private final static int X_POSITION = 37;
    private final static int Y_POSITION = 37;
    private final static Coordinate2D DEFAULT_LOCATION = new Coordinate2D(X_POSITION, Y_POSITION);
    private final static int WIDTH = 39;
    private final static int HEIGHT = 41;
    private final static Size DEFAULT_SIZE = new Size(WIDTH, HEIGHT);
    public static final int ROTATION_SPEED = 37;
    public static final double SPEED = 37d;
    public static final double DIRECTION = 42d;

    private SpriteAnimationDelegateFactory spriteAnimationDelegateFactory;
    private ImageViewFactory imageViewFactory;
    private SpriteAnimationDelegate spriteAnimationDelegate;
    private ImageRepository imageRepository;
    private MotionApplier motionApplier;
    private Injector injector;
    private Updater updater;

    @BeforeEach
    void setup() {
        imageViewFactory = mock(ImageViewFactory.class);
        spriteAnimationDelegate = mock(SpriteAnimationDelegate.class);
        spriteAnimationDelegateFactory = mock(SpriteAnimationDelegateFactory.class);
        updater = mock(Updater.class);
        motionApplier = mock(MotionApplier.class);

        imageRepository = mock(ImageRepository.class);
        injector = mock(Injector.class);
    }

    @Nested
    class EntitiesWithDefaultSizeTest {
        private DynamicSpriteEntity sut;

        @BeforeEach
        void setup() {
            sut = new DynamicSpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION);
        }

        @Test
        void bufferIsSetInConstructor() {
            // Arrange

            // Act
            Optional<EntityMotionInitBuffer> buffer = sut.getBuffer();

            // Assert
            Assertions.assertTrue(buffer.isPresent());
        }

        @Test
        void bufferIsEmptiedAfterInitIsCalled() {
            // Arrange
            var image = mock(Image.class);
            var imageView = mock(ImageView.class);
            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setImageRepository(imageRepository);
            sut.setImageViewFactory(imageViewFactory);
            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setUpdater(updater);

            when(imageRepository.get(DEFAULT_RESOURCE)).thenReturn(image);

            when(imageViewFactory.create(image)).thenReturn(imageView);
            when(spriteAnimationDelegateFactory.create(imageView, 1, 1)).thenReturn(spriteAnimationDelegate);

            sut.setMotionApplier(motionApplier);

            // Act
            sut.init(injector);

            // Assert
            Assertions.assertFalse(sut.getBuffer().isPresent());
        }

        @Test
        void bufferTransfersMotionOnInit() {
            // Arrange
            var image = mock(Image.class);
            var imageView = mock(ImageView.class);
            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setImageRepository(imageRepository);
            sut.setImageViewFactory(imageViewFactory);
            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setUpdater(updater);

            when(imageRepository.get(DEFAULT_RESOURCE)).thenReturn(image);

            when(imageViewFactory.create(image)).thenReturn(imageView);
            when(spriteAnimationDelegateFactory.create(imageView, 1, 1)).thenReturn(spriteAnimationDelegate);

            sut.setMotionApplier(motionApplier);
            sut.setMotion(SPEED, DIRECTION);

            // Act
            sut.init(injector);

            // Assert
            verify(motionApplier).setMotion(SPEED, DIRECTION);
        }

        @Test
        void addedUpdaterIsUsedAsUpdater() {
            // Arrange
            var sut = new DynamicSpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, 1, 1);
            sut.setMotionApplier(motionApplier);
            var updater = mock(Updater.class);

            sut.setUpdater(updater);

            // Act
            Updater updater1 = sut.getUpdater();

            // Assert
            assertEquals(updater, updater1);
        }
    }

    @Nested
    class EntitiesWithSizeSetTest {
        private DynamicSpriteEntity sut;

        @BeforeEach
        void setup() {
            sut = new DynamicSpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE);
        }

        @Test
        void bufferIsSetInConstructor() {
            // Arrange

            // Act
            Optional<EntityMotionInitBuffer> buffer = sut.getBuffer();

            // Assert
            Assertions.assertTrue(buffer.isPresent());
        }

        @Test
        void bufferIsEmptiedAfterInitIsCalled() {
            // Arrange
            var image = mock(Image.class);
            var imageView = mock(ImageView.class);
            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setImageRepository(imageRepository);
            sut.setImageViewFactory(imageViewFactory);
            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setUpdater(updater);

            when(imageRepository.get(DEFAULT_RESOURCE, WIDTH, HEIGHT, true)).thenReturn(image);

            when(imageViewFactory.create(image)).thenReturn(imageView);
            when(spriteAnimationDelegateFactory.create(imageView, 1, 1)).thenReturn(spriteAnimationDelegate);

            sut.setMotionApplier(motionApplier);

            // Act
            sut.init(injector);

            // Assert
            Assertions.assertFalse(sut.getBuffer().isPresent());
        }

        @Test
        void bufferTransfersMotionOnInit() {
            // Arrange
            var image = mock(Image.class);
            var imageView = mock(ImageView.class);
            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setImageRepository(imageRepository);
            sut.setImageViewFactory(imageViewFactory);
            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setUpdater(updater);

            when(imageRepository.get(DEFAULT_RESOURCE, WIDTH, HEIGHT, true)).thenReturn(image);

            when(imageViewFactory.create(image)).thenReturn(imageView);
            when(spriteAnimationDelegateFactory.create(imageView, 1, 1)).thenReturn(spriteAnimationDelegate);

            sut.setMotionApplier(motionApplier);
            sut.setMotion(SPEED, DIRECTION);

            // Act
            sut.init(injector);

            // Assert
            verify(motionApplier).setMotion(SPEED, DIRECTION);
        }

        @Test
        void instantiatingAnUpdatableSpriteEntityWithOneFrameGivesNoSideEffects() {
            // Arrange
            sut.setMotionApplier(motionApplier);

            // Act
            var sut = new DynamicSpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE);

            // Assert
            Assertions.assertNotNull(sut);
        }

        @Test
        void setAutoCycleDoesNotBreakWithOnlyOneFrame() {
            // Arrange
            var autoCycleValue = 37;
            var image = mock(Image.class);
            var imageView = mock(ImageView.class);
            sut.setMotionApplier(motionApplier);
            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setImageRepository(imageRepository);
            sut.setImageViewFactory(imageViewFactory);
            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setUpdater(updater);

            when(imageRepository.get(DEFAULT_RESOURCE, WIDTH, HEIGHT, true)).thenReturn(image);

            when(imageViewFactory.create(image)).thenReturn(imageView);
            when(spriteAnimationDelegateFactory.create(imageView, 1, 1)).thenReturn(spriteAnimationDelegate);

            sut.setAutoCycle(autoCycleValue);

            // Act
            sut.init(injector);

            // Assert
            verifyNoInteractions(spriteAnimationDelegate);
        }

        @Test
        void addedUpdaterIsUsedAsUpdater() {
            // Arrange
            var sut = new DynamicSpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE, 1, 1);
            sut.setMotionApplier(motionApplier);
            var updater = mock(Updater.class);

            sut.setUpdater(updater);

            // Act
            Updater updater1 = sut.getUpdater();

            // Assert
            assertEquals(updater, updater1);
        }

        @Test
        void setMotionApplierIsUsed() {
            // Arrange
            sut.setMotionApplier(motionApplier);

            // Act
            var mA = sut.getMotionApplier();

            // Assert
            assertEquals(motionApplier, mA);
        }

        @Test
        void setRotationAngleIsUsed() {
            // Arrange
            sut.setMotionApplier(motionApplier);
            sut.setRotationSpeed(ROTATION_SPEED);

            // Act
            var rS = sut.getRotationSpeed();

            // Assert
            assertEquals(ROTATION_SPEED, rS);
        }

        @Test
        void addToEntityCollectionCallsAddDynamicEntity() {
            // Arrange
            var entityCollection = mock(EntityCollection.class);

            // Act
            sut.addToEntityCollection(entityCollection);

            // Assert
            verify(entityCollection).addDynamicEntity(sut);
        }

        @Test
        void updateGetsDelegated() {
            // Arrange
            var updater = mock(Updater.class);
            sut.setUpdater(updater);

            // Act
            long TIMESTAMP = 0L;
            sut.update(TIMESTAMP);

            // Assert
            verify(updater).update(TIMESTAMP);
        }
    }

    @Nested
    class EntitiesWithAutoCyclingTest {

        private DynamicSpriteEntityImpl sut;

        @BeforeEach
        void setup() {
            var rows = 1;
            var columns = 2;
            var image = mock(Image.class);
            var imageView = mock(ImageView.class);

            sut = new DynamicSpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE, rows, columns);
            sut.setMotionApplier(motionApplier);
            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setImageRepository(imageRepository);
            sut.setImageViewFactory(imageViewFactory);
            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setUpdater(updater);

            when(imageRepository.get(DEFAULT_RESOURCE, WIDTH * columns, HEIGHT * rows, true)).thenReturn(image);

            when(imageViewFactory.create(image)).thenReturn(imageView);
            when(spriteAnimationDelegateFactory.create(imageView, rows, columns)).thenReturn(spriteAnimationDelegate);
        }

        @Test
        void autoCycleGetsDelegatedToSpriteAnimationDelegate() {
            // Arrange
            sut.init(injector);

            // Act
            sut.setAutoCycle(2);

            // Assert
            verify(spriteAnimationDelegate).setAutoCycleInterval(2);
        }

        @Test
        void getAutoCycleRowDelegatedToSpriteAnimationDelegate() {
            // Arrange
            var expected = 42;
            sut.init(injector);
            when(spriteAnimationDelegate.getCyclingRow()).thenReturn(expected);

            // Act
            var actual = sut.getAutoCycleRow();

            // Assert
            Assertions.assertEquals(expected, actual);
        }

        @Test
        void autoCycleRowGetsDelegatedToSpriteAnimationDelegateOnInit() {
            // Arrange
            sut.setAutoCycleRow(2);

            // Act
            sut.init(injector);

            // Assert
            verify(spriteAnimationDelegate).setAutoCycle(0, 2);
        }

        @Test
        void autoCycleRowGetsDelegatedToSpriteAnimationDelegateAfterInit() {
            // Arrange
            sut.init(injector);

            // Act
            sut.setAutoCycleRow(2);

            // Assert
            verify(spriteAnimationDelegate).setAutoCycleRow(2);
        }
    }

    @Nested
    class EntitiesPlayingAnimationsTest {

        private DynamicSpriteEntityImpl sut;
        private FiniteAnimation animationMock;

        @BeforeEach
        void setup() {
            var rows = 1;
            var columns = 2;
            var imageMock = mock(Image.class);
            var imageViewMock = mock(ImageView.class);
            animationMock = mock(FiniteAnimation.class);

            sut = new DynamicSpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE, rows, columns);
            sut.setMotionApplier(motionApplier);
            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setImageRepository(imageRepository);
            sut.setImageViewFactory(imageViewFactory);
            sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
            sut.setUpdater(updater);

            when(imageRepository.get(DEFAULT_RESOURCE, WIDTH * columns, HEIGHT * rows, true)).thenReturn(imageMock);

            when(imageViewFactory.create(imageMock)).thenReturn(imageViewMock);
            when(spriteAnimationDelegateFactory.create(imageViewMock, rows, columns)).thenReturn(spriteAnimationDelegate);
        }

        @Test
        void initCallsPlayAnimationIfSetTest() {
            // Arrange
            sut.playAnimation(animationMock);

            // Act
            sut.init(injector);

            // Assert
            verify(spriteAnimationDelegate).setAutoCycle(0L, -1);
            verify(spriteAnimationDelegate).playAnimation(animationMock, true);
        }

        @Test
        void playAnimationDelegatesToSpriteAnimationDelegateWithRestartIfSameValueFalseTest() {
            // Arrange
            sut.init(injector);

            // Act
            sut.playAnimation(animationMock);

            // Assert
            verify(spriteAnimationDelegate).playAnimation(animationMock, false);
        }

        @Test
        void playAnimationDelegatesToSpriteAnimationDelegate() {
            // Arrange
            sut.init(injector);

            // Act
            sut.playAnimation(animationMock, true);

            // Assert
            verify(spriteAnimationDelegate).playAnimation(animationMock, true);
        }

        @Test
        void getAnimationIsDelegatedToTheDelegate() {
            // Arrange
            sut.init(injector);
            when(spriteAnimationDelegate.getCurrentAnimation()).thenReturn(animationMock);

            // Act & Assert
            assertEquals(animationMock, sut.getCurrentAnimation());
        }
    }

    private static class DynamicSpriteEntityImpl extends DynamicSpriteEntity {

        DynamicSpriteEntityImpl(String resource, Coordinate2D location) {
            super(resource, location);
        }

        DynamicSpriteEntityImpl(String resource, Coordinate2D location, int rows, int columns) {
            super(resource, location, rows, columns);
        }

        DynamicSpriteEntityImpl(String resource, Coordinate2D location, Size size) {
            super(resource, location, size);
        }

        DynamicSpriteEntityImpl(String resource, Coordinate2D location, Size size, int rows, int columns) {
            super(resource, location, size, rows, columns);
        }
    }
}
