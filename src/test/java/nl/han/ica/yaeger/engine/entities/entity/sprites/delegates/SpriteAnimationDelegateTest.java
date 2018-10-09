package nl.han.ica.yaeger.engine.entities.entity.sprites.delegates;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nl.han.ica.yaeger.engine.resourceconsumer.ResourceConsumer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SpriteAnimationDelegateTest implements ResourceConsumer {

    private static final double IMAGE_WIDTH = 100d;

    @Test
    void viewPortIsSetOnCreation() {

        // Setup
        ImageView imageView = Mockito.mock(ImageView.class);
        Image image = Mockito.mock(Image.class);

        Mockito.when(imageView.getImage()).thenReturn(image);
        Mockito.when(image.getWidth()).thenReturn(IMAGE_WIDTH);

        // Test
        SpriteAnimationDelegate spriteAnimationDelegate = new SpriteAnimationDelegate(imageView, 2);

        // Verify
        Mockito.verify(imageView).setViewport(Mockito.any(Rectangle2D.class));
    }
}
