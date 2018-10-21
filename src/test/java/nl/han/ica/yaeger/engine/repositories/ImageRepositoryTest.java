package nl.han.ica.yaeger.engine.repositories;

import javafx.scene.image.Image;
import nl.han.ica.yaeger.javafx.factories.ImageFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ImageRepositoryTest {

    private ImageRepository imageRepository;
    private ImageFactory imageFactory;

    @BeforeEach
    void setup() {
        imageRepository = ImageRepository.getInstance();
        imageFactory = mock(ImageFactory.class);
        imageRepository.setFactory(imageFactory);
    }

    @Test
    void getCreatesAndReturnsASprite() {
        // Setup
        Image image = mock(Image.class);
        when(imageFactory.create(anyString())).thenReturn(image);

        // Test
        Image createdImage = imageRepository.get("images/bubble.png");

        // Verify
        Assertions.assertEquals(image, createdImage);
    }

    @Test
    void callingGetTwiceCreatesCreatesOnlyOneImage() {
        // Setup
        Image image = mock(Image.class);
        when(imageFactory.create(anyString())).thenReturn(image);

        // Test
        Image image1 = imageRepository.get("images/bubble.png");
        Image image2 = imageRepository.get("images/bubble.png");

        // Verify
        Assertions.assertSame(image1, image2);
    }

    @Test
    void callingGetWithSpecifiedWidthAndHeightReturnsAnImage() {
        // Setup
        Image image = mock(Image.class);
        when(imageFactory.create(anyString(), anyDouble(), anyDouble(), anyBoolean())).thenReturn(image);

        // Test
        Image createdImage = imageRepository.get("images/bubble.png", 20, 20, true);

        // Verify
        Assertions.assertSame(image, createdImage);
    }
}

