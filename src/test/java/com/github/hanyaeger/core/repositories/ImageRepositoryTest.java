package com.github.hanyaeger.core.repositories;

import com.github.hanyaeger.core.factories.image.ImageFactory;
import javafx.scene.image.Image;
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
        // Arrange
        imageRepository.destroy();

        // Act
        int size = imageRepository.size();

        // Assert
        Assertions.assertEquals(0, size);
    }

    @Test
    void getCreatesAndReturnsAnImage() {
        // Arrange
        imageRepository.destroy();
        Image image = mock(Image.class);
        when(imageFactory.create(anyString())).thenReturn(image);

        // Act
        Image createdImage = imageRepository.get("waterworld/images/bubble.png");

        // Assert
        Assertions.assertEquals(image, createdImage);
        Assertions.assertEquals(1, imageRepository.size());
    }

    @Test
    void callingGetTwiceCreatesCreatesOnlyOneImage() {
        // Arrange
        imageRepository.destroy();
        Image image = mock(Image.class);
        when(imageFactory.create(anyString())).thenReturn(image);

        // Act
        Image image1 = imageRepository.get("waterworld/images/bubble.png");
        Image image2 = imageRepository.get("waterworld/images/bubble.png");

        // Assert
        Assertions.assertSame(image1, image2);
        Assertions.assertEquals(1, imageRepository.size());
    }

    @Test
    void callingGetForDifferentImagesCreatesDifferentImages() {
        // Arrange
        imageRepository.destroy();
        Image image = mock(Image.class);
        when(imageFactory.create(anyString())).thenReturn(image);

        // Act
        Image image1 = imageRepository.get("waterworld/images/bubble.png");
        Image image2 = imageRepository.get("waterworld/images/poison.png");

        // Assert
        Assertions.assertEquals(2, imageRepository.size());
    }

    @Test
    void callingGetWithSpecifiedWidthAndHeightReturnsAnImage() {
        // Arrange
        imageRepository.destroy();
        Image image = mock(Image.class);
        when(imageFactory.create(anyString(), anyDouble(), anyDouble(), anyBoolean())).thenReturn(image);

        // Act
        Image createdImage = imageRepository.get("waterworld/images/bubble.png", 20, 20, true);

        // Assert
        Assertions.assertSame(image, createdImage);
        Assertions.assertEquals(1, imageRepository.size());
    }

    @Test
    void callingTwiceGetWithSpecifiedWidthAndHeightReturnsSameImage() {
        // Arrange
        imageRepository.destroy();
        Image image = mock(Image.class);
        when(imageFactory.create(anyString(), anyDouble(), anyDouble(), anyBoolean())).thenReturn(image);

        // Act
        Image firstImage = imageRepository.get("waterworld/images/bubble.png", 20, 20, true);
        Image secondImage = imageRepository.get("waterworld/images/bubble.png", 20, 20, true);

        // Assert
        Assertions.assertSame(image, firstImage);
        Assertions.assertSame(firstImage, secondImage);
        Assertions.assertEquals(1, imageRepository.size());
    }
}

