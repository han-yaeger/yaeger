package nl.meron.yaeger.engine.entities.entity.sprites;

import com.google.inject.Injector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.Updater;
import nl.meron.yaeger.engine.entities.entity.sprites.delegates.SpriteAnimationDelegate;
import nl.meron.yaeger.engine.entities.entity.motion.MotionVector;
import nl.meron.yaeger.engine.media.repositories.ImageRepository;
import nl.meron.yaeger.javafx.image.ImageViewFactory;
import nl.meron.yaeger.guice.factories.SpriteAnimationDelegateFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class UpdatableSpriteEntityTest {

    private final static String DEFAULT_RESOURCE = "images/bubble.png";
    private final static int X_POSITION = 37;
    private final static int Y_POSITION = 37;
    private final static Point DEFAULT_POINT = new Point(X_POSITION, Y_POSITION);
    private final static int WIDTH = 39;
    private final static int HEIGHT = 41;
    private final static Size DEFAULT_SIZE = new Size(WIDTH, HEIGHT);
    private final static Double DIRECTION = MotionVector.Direction.UP;
    private final static int SPEED = 1;

    private final static MotionVector MOVEMENT_VECTOR = new MotionVector(DIRECTION, SPEED);

    private SpriteAnimationDelegateFactory spriteAnimationDelegateFactory;
    private ImageViewFactory imageViewFactory;
    private SpriteAnimationDelegate spriteAnimationDelegate;
    private ImageRepository imageRepository;
    private Injector injector;
    private Updater updater;

    @BeforeEach
    void setup() {
        imageViewFactory = mock(ImageViewFactory.class);
        spriteAnimationDelegate = mock(SpriteAnimationDelegate.class);
        spriteAnimationDelegateFactory = mock(SpriteAnimationDelegateFactory.class);
        updater = mock(Updater.class);

        imageRepository = mock(ImageRepository.class);
        injector = mock(Injector.class);
    }

    @Test
    void instantiatingAnUpdatableSpriteEntityWithOneFrameGivesNoSideEffects() {
        // Setup

        // Test
        var sut = new TestUpdatableSpriteEntity(DEFAULT_RESOURCE, DEFAULT_POINT, DEFAULT_SIZE);

        // Verify
        Assertions.assertNotNull(sut);
    }

    @Test
    void setAutocycleDoesNotBreakWithOnlyOneFrame() {
        // Setup
        var autocycleValue = 37;
        var image = mock(Image.class);
        var imageView = mock(ImageView.class);
        var sut = new TestUpdatableSpriteEntity(DEFAULT_RESOURCE, DEFAULT_POINT, DEFAULT_SIZE);
        sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
        sut.setImageRepository(imageRepository);
        sut.setImageViewFactory(imageViewFactory);
        sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
        sut.setUpdater(updater);

        when(imageRepository.get(DEFAULT_RESOURCE, WIDTH, HEIGHT, true)).thenReturn(image);

        when(imageViewFactory.create(image)).thenReturn(imageView);
        when(spriteAnimationDelegateFactory.create(imageView, 1)).thenReturn(spriteAnimationDelegate);

        sut.setAutoCycle(autocycleValue);

        // Test
        sut.init(injector);

        // Verify
        verifyNoInteractions(spriteAnimationDelegate);
    }

    @Test
    void autocycleGetsDelegatedToSpriteAnimationDelegate() {
        // Setup
        var frames = 2;
        var image = mock(Image.class);
        var imageView = mock(ImageView.class);
        var sut = new AutoCyclingUpdatableSpriteEntity(DEFAULT_RESOURCE, DEFAULT_POINT, DEFAULT_SIZE, frames);
        sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
        sut.setImageRepository(imageRepository);
        sut.setImageViewFactory(imageViewFactory);
        sut.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
        sut.setUpdater(updater);

        when(imageRepository.get(DEFAULT_RESOURCE, WIDTH * frames, HEIGHT, true)).thenReturn(image);

        when(imageViewFactory.create(image)).thenReturn(imageView);
        when(spriteAnimationDelegateFactory.create(imageView, frames)).thenReturn(spriteAnimationDelegate);

        // Test
        sut.init(injector);

        // Verify
        verify(spriteAnimationDelegate).setAutoCycle(2);
    }

    @Test
    void addedUpdaterIsUsedAsUpdater() {
        // Setup
        var sut = new TestUpdatableSpriteEntity(DEFAULT_RESOURCE, DEFAULT_POINT, DEFAULT_SIZE, 1, MOVEMENT_VECTOR);
        var updater = mock(Updater.class);

        sut.setUpdater(updater);

        // Test
        Updater updater1 = sut.getUpdater();

        // Verify
        Assertions.assertEquals(updater, updater1);
    }

    private class TestUpdatableSpriteEntity extends UpdatableSpriteEntity {

        TestUpdatableSpriteEntity(String resource, Point point, Size size) {
            super(resource, point, size);
        }

        TestUpdatableSpriteEntity(String resource, Point point, Size size, int frames, MotionVector motionVector) {
            super(resource, point, size, frames, motionVector);
        }
    }

    private class AutoCyclingUpdatableSpriteEntity extends UpdatableSpriteEntity {

        AutoCyclingUpdatableSpriteEntity(String resource, Point point, Size size, int frames) {
            super(resource, point, size, frames);
            setAutoCycle(2);
        }
    }
}
