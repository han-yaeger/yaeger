package com.github.hanyaeger.api.engine.entities.entity.sprite;

import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.Updater;
import com.github.hanyaeger.api.engine.entities.EntityCollection;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.motion.DefaultMotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.EntityMotionInitBuffer;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplierType;
import com.github.hanyaeger.api.engine.entities.entity.sprite.delegates.SpriteAnimationDelegate;
import com.github.hanyaeger.api.engine.media.repositories.ImageRepository;
import com.github.hanyaeger.api.guice.factories.MotionApplierFactory;
import com.github.hanyaeger.api.guice.factories.SpriteAnimationDelegateFactory;
import com.github.hanyaeger.api.javafx.image.ImageViewFactory;
import com.google.inject.Injector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class DynamicSpriteEntityTest {

    private final long TIMESTAMP = 0L;

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
    private MotionApplierFactory motionApplierFactory;
    private Injector injector;
    private Updater updater;
    private DynamicSpriteEntityImpl sut;

    @BeforeEach
    void setup() {
        imageViewFactory = mock(ImageViewFactory.class);
        spriteAnimationDelegate = mock(SpriteAnimationDelegate.class);
        spriteAnimationDelegateFactory = mock(SpriteAnimationDelegateFactory.class);
        updater = mock(Updater.class);
        motionApplierFactory = mock(MotionApplierFactory.class);
        motionApplier = mock(MotionApplier.class);

        when(motionApplierFactory.create(any(MotionApplierType.class))).thenReturn(motionApplier);

        imageRepository = mock(ImageRepository.class);
        injector = mock(Injector.class);

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
        when(spriteAnimationDelegateFactory.create(imageView, 1)).thenReturn(spriteAnimationDelegate);

        sut.injectMotionApplierFactory(motionApplierFactory);

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
        when(spriteAnimationDelegateFactory.create(imageView, 1)).thenReturn(spriteAnimationDelegate);

        sut.injectMotionApplierFactory(motionApplierFactory);
        sut.setMotion(SPEED, DIRECTION);

        // Act
        sut.init(injector);

        // Assert
        verify(motionApplier).setMotion(SPEED, DIRECTION);
    }

    @Test
    void instantiatingAnUpdatableSpriteEntityWithOneFrameGivesNoSideEffects() {
        // Arrange
        sut.injectMotionApplierFactory(motionApplierFactory);

        // Act
        var sut = new DynamicSpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE);

        // Assert
        Assertions.assertNotNull(sut);
    }

    @Test
    void setAutocycleDoesNotBreakWithOnlyOneFrame() {
        // Arrange
        var autocycleValue = 37;
        var image = mock(Image.class);
        var imageView = mock(ImageView.class);
        var sut = new DynamicSpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE);
        sut.injectMotionApplierFactory(motionApplierFactory);
        sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
        sut.setImageRepository(imageRepository);
        sut.setImageViewFactory(imageViewFactory);
        sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
        sut.setUpdater(updater);

        when(imageRepository.get(DEFAULT_RESOURCE, WIDTH, HEIGHT, true)).thenReturn(image);

        when(imageViewFactory.create(image)).thenReturn(imageView);
        when(spriteAnimationDelegateFactory.create(imageView, 1)).thenReturn(spriteAnimationDelegate);

        sut.setAutoCycle(autocycleValue);

        // Act
        sut.init(injector);

        // Assert
        verifyNoInteractions(spriteAnimationDelegate);
    }

    @Test
    void autocycleGetsDelegatedToSpriteAnimationDelegate() {
        // Arrange
        var frames = 2;
        var image = mock(Image.class);
        var imageView = mock(ImageView.class);
        var sut = new AutoCyclingDynamicSpriteEntity(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE, frames);
        sut.injectMotionApplierFactory(motionApplierFactory);
        sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
        sut.setImageRepository(imageRepository);
        sut.setImageViewFactory(imageViewFactory);
        sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
        sut.setUpdater(updater);

        when(imageRepository.get(DEFAULT_RESOURCE, WIDTH * frames, HEIGHT, true)).thenReturn(image);

        when(imageViewFactory.create(image)).thenReturn(imageView);
        when(spriteAnimationDelegateFactory.create(imageView, frames)).thenReturn(spriteAnimationDelegate);

        // Act
        sut.init(injector);

        // Assert
        verify(spriteAnimationDelegate).setAutoCycle(2);
    }

    @Test
    void addedUpdaterIsUsedAsUpdater() {
        // Arrange
        var sut = new DynamicSpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE, 1);
        sut.injectMotionApplierFactory(motionApplierFactory);
        var updater = mock(Updater.class);

        sut.setUpdater(updater);

        // Act
        Updater updater1 = sut.getUpdater();

        // Assert
        Assertions.assertEquals(updater, updater1);
    }

    @Test
    void setMotionApplierIsUsed() {
        // Arrange
        var sut = new DynamicSpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE);
        sut.injectMotionApplierFactory(motionApplierFactory);

        // Act
        var mA = sut.getMotionApplier();

        // Assert
        Assertions.assertEquals(motionApplier, mA);
    }

    @Test
    void setRotationAngleIsUsed() {
        // Arrange
        var sut = new DynamicSpriteEntityImpl(DEFAULT_RESOURCE, DEFAULT_LOCATION, DEFAULT_SIZE);
        sut.injectMotionApplierFactory(motionApplierFactory);
        sut.setRotationSpeed(ROTATION_SPEED);

        // Act
        var rS = sut.getRotationSpeed();

        // Assert
        Assertions.assertEquals(ROTATION_SPEED, rS);
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
        sut.update(TIMESTAMP);

        // Assert
        verify(updater).update(TIMESTAMP);
    }

    private class DynamicSpriteEntityImpl extends DynamicSpriteEntity {

        DynamicSpriteEntityImpl(String resource, Coordinate2D location, Size size) {
            super(resource, location, size);
        }

        DynamicSpriteEntityImpl(String resource, Coordinate2D location, Size size, int frames) {
            super(resource, location, size, frames);
        }
    }

    private class AutoCyclingDynamicSpriteEntity extends DynamicSpriteEntity {

        AutoCyclingDynamicSpriteEntity(String resource, Coordinate2D location, Size size, int frames) {
            super(resource, location, size, frames);
            setAutoCycle(2);
        }
    }
}
