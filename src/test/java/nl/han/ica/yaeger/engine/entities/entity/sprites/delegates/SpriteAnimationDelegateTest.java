package nl.han.ica.yaeger.engine.entities.entity.sprites.delegates;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nl.han.ica.yaeger.engine.media.ResourceConsumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

class SpriteAnimationDelegateTest implements ResourceConsumer {

    private static final double IMAGE_WIDTH = 100d;
    private static final double IMAGE_HEIGHT = 25d;
    private static final int FRAMES = 4;
    private static final double DELTA = 0.00000000000001d;

    private ImageView imageView;
    private Image image;

    @BeforeEach
    void setup() {
        // Setup
        imageView = mock(ImageView.class);
        image = mock(Image.class);

        when(imageView.getImage()).thenReturn(image);
        when(image.getWidth()).thenReturn(IMAGE_WIDTH);
    }

    @Test
    void viewPortIsSetOnCreation() {
        // Setup

        // Test
        var spriteAnimationDelegate = new SpriteAnimationDelegate(imageView, FRAMES);

        // Verify
        verify(imageView).setViewport(any(Rectangle2D.class));
    }

    @Test
    void viewPortRectangleIsCalculatedCorrectly() {
        // Setup
        when(image.getHeight()).thenReturn(IMAGE_HEIGHT);

        var argument = ArgumentCaptor.forClass(Rectangle2D.class);

        // Test
        var spriteAnimationDelegate = new SpriteAnimationDelegate(imageView, FRAMES);

        // Verify
        verify(imageView).setViewport(argument.capture());
        Assertions.assertEquals(IMAGE_HEIGHT, argument.getValue().getHeight(), DELTA);
        Assertions.assertEquals(IMAGE_WIDTH / FRAMES, argument.getValue().getWidth(), DELTA);
    }

    @Test
    void nextSetsNextSpriteIndex() {
        // Setup
        when(image.getHeight()).thenReturn(IMAGE_HEIGHT);

        // Test
        var spriteAnimationDelegate = new SpriteAnimationDelegate(imageView, FRAMES);
        spriteAnimationDelegate.next();

        // Verify
        var argument = ArgumentCaptor.forClass(Rectangle2D.class);

        verify(imageView, atLeastOnce()).setViewport(argument.capture());

        var values = argument.getAllValues();
        var nextRectangle = values.get(1);

        Assertions.assertEquals(IMAGE_WIDTH / FRAMES, nextRectangle.getMinX());
    }

    @Test
    void autoCycleNotCalledIfUpdateTimeDoesNotExceedCycleTime() {
        // Setup
        when(image.getHeight()).thenReturn(IMAGE_HEIGHT);

        // Test
        var spriteAnimationDelegate = new SpriteAnimationDelegate(imageView, FRAMES);
        spriteAnimationDelegate.setAutoCycle(10);
        spriteAnimationDelegate.update(11);
        spriteAnimationDelegate.update(2002);

        // Verify
        verify(imageView).setViewport(any());
    }

    @Test
    void autoCycleCalledIfUpdateTimeExceedsCycleTimeCorrectly() {
        // Setup
        when(image.getHeight()).thenReturn(IMAGE_HEIGHT);

        // Test
        var spriteAnimationDelegate = new SpriteAnimationDelegate(imageView, FRAMES);
        spriteAnimationDelegate.setAutoCycle(10);
        spriteAnimationDelegate.update(10000001);
        spriteAnimationDelegate.update(20000002);

        // Verify
        verify(imageView, atLeast(3)).setViewport(any());
    }
}
