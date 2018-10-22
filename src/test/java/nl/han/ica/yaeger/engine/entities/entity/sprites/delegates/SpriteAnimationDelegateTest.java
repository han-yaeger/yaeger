package nl.han.ica.yaeger.engine.entities.entity.sprites.delegates;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nl.han.ica.yaeger.engine.media.ResourceConsumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

class SpriteAnimationDelegateTest implements ResourceConsumer {

    private static final double IMAGE_WIDTH = 100d;
    private static final double IMAGE_HEIGHT = 25d;
    private static final int FRAMES = 4;
    private static final double DELTA = 0.00000000000001d;

    @Test
    void viewPortIsSetOnCreation() {

        // Setup
        ImageView imageView = mock(ImageView.class);
        Image image = mock(Image.class);

        when(imageView.getImage()).thenReturn(image);
        when(image.getWidth()).thenReturn(IMAGE_WIDTH);

        // Test
        SpriteAnimationDelegate spriteAnimationDelegate = new SpriteAnimationDelegate(imageView, FRAMES);

        // Verify
        verify(imageView).setViewport(any(Rectangle2D.class));
    }

    @Test
    void viewPortRectangleIsCalculatedCorrectly() {

        // Setup
        ImageView imageView = mock(ImageView.class);
        Image image = mock(Image.class);

        when(imageView.getImage()).thenReturn(image);
        when(image.getWidth()).thenReturn(IMAGE_WIDTH);
        when(image.getHeight()).thenReturn(IMAGE_HEIGHT);

        ArgumentCaptor<Rectangle2D> argument = ArgumentCaptor.forClass(Rectangle2D.class);

        // Test
        SpriteAnimationDelegate spriteAnimationDelegate = new SpriteAnimationDelegate(imageView, FRAMES);

        // Verify
        verify(imageView).setViewport(argument.capture());
        Assertions.assertEquals(IMAGE_HEIGHT, argument.getValue().getHeight(), DELTA);
        Assertions.assertEquals(IMAGE_WIDTH / FRAMES, argument.getValue().getWidth(), DELTA);
    }
}
