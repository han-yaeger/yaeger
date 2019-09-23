package nl.meron.yaeger.engine.media.repositories;

import javafx.scene.image.Image;
import nl.meron.yaeger.javafx.image.ImageFactory;
import nl.meron.yaeger.javafx.image.ImageFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ImageRepositoryTest {

    private ImageRepository imageRepository;
    private ImageFactory imageFactory;

    @BeforeEach
    void setup() {
        imageRepository = new ImageRepository();
        imageFactory = mock(ImageFactory.class);
        imageRepository.setFactory(imageFactory);
    }

    @Test
    void afterDestroyRepositoryIsEmpty() {
        // Setup
        imageRepository.destroy();

        // Test
        int size = imageRepository.size();

        // Verify
        Assertions.assertEquals(0, size);
    }

    @Test
    void getCreatesAndReturnsAnImage() {
        // Setup
        imageRepository.destroy();
        Image image = mock(Image.class);
        when(imageFactory.create(anyString())).thenReturn(image);

        // Test
        Image createdImage = imageRepository.get("images/bubble.png");

        // Verify
        Assertions.assertEquals(image, createdImage);
        Assertions.assertEquals(1, imageRepository.size());
    }

    @Test
    void callingGetTwiceCreatesCreatesOnlyOneImage() {
        // Setup
        imageRepository.destroy();
        Image image = mock(Image.class);
        when(imageFactory.create(anyString())).thenReturn(image);

        // Test
        Image image1 = imageRepository.get("images/bubble.png");
        Image image2 = imageRepository.get("images/bubble.png");

        // Verify
        Assertions.assertSame(image1, image2);
        Assertions.assertEquals(1, imageRepository.size());
    }

    @Test
    void callingGetForDifferentImagesCreatesDifferentImages() {
        // Setup
        imageRepository.destroy();
        Image image = mock(Image.class);
        when(imageFactory.create(anyString())).thenReturn(image);

        // Test
        Image image1 = imageRepository.get("images/bubble.png");
        Image image2 = imageRepository.get("images/poison.png");

        // Verify
        Assertions.assertEquals(2, imageRepository.size());
    }

    @Test
    void callingGetWithSpecifiedWidthAndHeightReturnsAnImage() {
        // Setup
        imageRepository.destroy();
        Image image = mock(Image.class);
        when(imageFactory.create(anyString(), anyDouble(), anyDouble(), anyBoolean())).thenReturn(image);

        // Test
        Image createdImage = imageRepository.get("images/bubble.png", 20, 20, true);

        // Verify
        Assertions.assertSame(image, createdImage);
        Assertions.assertEquals(1, imageRepository.size());
    }

    @Test
    void callingTwiceGetWithSpecifiedWidthAndHeightReturnsSameImage() {
        // Setup
        imageRepository.destroy();
        Image image = mock(Image.class);
        when(imageFactory.create(anyString(), anyDouble(), anyDouble(), anyBoolean())).thenReturn(image);

        // Test
        Image firstImage = imageRepository.get("images/bubble.png", 20, 20, true);
        Image secondImage = imageRepository.get("images/bubble.png", 20, 20, true);

        // Verify
        Assertions.assertSame(image, firstImage);
        Assertions.assertSame(firstImage, secondImage);
        Assertions.assertEquals(1, imageRepository.size());
    }
}

