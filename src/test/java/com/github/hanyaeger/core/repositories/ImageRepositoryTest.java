package com.github.hanyaeger.core.repositories;

import com.github.hanyaeger.core.factories.image.ImageFactory;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals(0, size);
    }

    @Test
    void getCreatesAndReturnsAnImage() {
        // Arrange
        imageRepository.destroy();
        Image image = mock(Image.class);
        when(imageFactory.create(anyString())).thenReturn(image);

        // Act
        var createdImage = imageRepository.get("waterworld/images/bubble.png");

        // Assert
        assertEquals(image, createdImage);
        assertEquals(1, imageRepository.size());
    }

    @Test
    void callingGetTwiceCreatesCreatesOnlyOneImage() {
        // Arrange
        imageRepository.destroy();
        var image = mock(Image.class);
        when(imageFactory.create(anyString())).thenReturn(image);

        // Act
        var image1 = imageRepository.get("waterworld/images/bubble.png");
        var image2 = imageRepository.get("waterworld/images/bubble.png");

        // Assert
        assertSame(image1, image2);
        assertEquals(1, imageRepository.size());
    }

    @Test
    void callingGetForDifferentImagesCreatesDifferentImages() {
        // Arrange
        imageRepository.destroy();
        var image = mock(Image.class);
        when(imageFactory.create(anyString())).thenReturn(image);

        // Act
        var image1 = imageRepository.get("waterworld/images/bubble.png");
        var image2 = imageRepository.get("waterworld/images/poison.png");

        // Assert
        assertEquals(2, imageRepository.size());
    }

    @Test
    void callingGetWithSpecifiedWidthAndHeightReturnsAnImage() {
        // Arrange
        imageRepository.destroy();
        var image = mock(Image.class);
        when(imageFactory.create(anyString(), anyDouble(), anyDouble(), anyBoolean())).thenReturn(image);

        // Act
        var createdImage = imageRepository.get("waterworld/images/bubble.png", 20, 20, true);

        // Assert
        assertSame(image, createdImage);
        assertEquals(1, imageRepository.size());
    }

    @Test
    void callingTwiceGetWithSpecifiedWidthAndHeightReturnsSameImage() {
        // Arrange
        imageRepository.destroy();
        var image = mock(Image.class);
        when(imageFactory.create(anyString(), anyDouble(), anyDouble(), anyBoolean())).thenReturn(image);

        // Act
        var firstImage = imageRepository.get("waterworld/images/bubble.png", 20, 20, true);
        var secondImage = imageRepository.get("waterworld/images/bubble.png", 20, 20, true);

        // Assert
        assertSame(image, firstImage);
        assertSame(firstImage, secondImage);
        assertEquals(1, imageRepository.size());
    }
}

