package com.github.hanyaeger.api.engine.entities.entity.sprite.delegates;

import com.github.hanyaeger.api.engine.media.ResourceConsumer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpriteAnimationDelegateTest implements ResourceConsumer {

    private static final double IMAGE_WIDTH = 100d;
    private static final double IMAGE_HEIGHT = 25d;
    private static final int ROWS = 4;
    private static final int COLUMNS = 3;
    private static final double DELTA = 0.00000000000001d;

    private ImageView imageView;
    private Image image;
    private SpriteAnimationDelegate sut;

    @BeforeEach
    void setup() {
        // Arrange
        imageView = mock(ImageView.class);
        image = mock(Image.class);

        when(imageView.getImage()).thenReturn(image);
        when(image.getWidth()).thenReturn(IMAGE_WIDTH);
        when(image.getHeight()).thenReturn(IMAGE_HEIGHT);

        sut = new SpriteAnimationDelegate(imageView, ROWS, COLUMNS);
    }

    @Test
    void viewPortIsSetOnCreation() {
        // Arrange

        // Act

        // Assert
        verify(imageView).setViewport(any(Rectangle2D.class));
    }

    @Test
    void viewPortRectangleIsCalculatedCorrectly() {
        // Arrange
        var argument = ArgumentCaptor.forClass(Rectangle2D.class);

        // Act

        // Assert
        verify(imageView).setViewport(argument.capture());
        assertEquals(IMAGE_HEIGHT / ROWS, argument.getValue().getHeight(), DELTA);
        assertEquals(IMAGE_WIDTH / COLUMNS, argument.getValue().getWidth(), DELTA);
    }

    @Test
    void setSpriteIndexDelegatesToImageView() {
        // Arrange

        // Act
        sut.setSpriteIndex(1);

        // Assert
        var argument = ArgumentCaptor.forClass(Rectangle2D.class);

        verify(imageView, atLeastOnce()).setViewport(argument.capture());

        var values = argument.getAllValues();
        var nextRectangle = values.get(1);

        assertEquals(IMAGE_WIDTH / COLUMNS, nextRectangle.getMinX());
        assertEquals(0, nextRectangle.getMinY());
    }

    @Test
    void getSpriteIndexReturnsLastSetSpriteIndex() {
        // Arrange
        var expected = 2;
        sut.setSpriteIndex(expected);

        // Act
        var actual = sut.getFrameIndex();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void nextSetsNextSpriteIndex() {
        // Arrange

        // Act
        sut.next();

        // Assert
        var argument = ArgumentCaptor.forClass(Rectangle2D.class);

        verify(imageView, atLeastOnce()).setViewport(argument.capture());

        var values = argument.getAllValues();
        var nextRectangle = values.get(1);

        assertEquals(IMAGE_WIDTH / COLUMNS, nextRectangle.getMinX());
        assertEquals(0, nextRectangle.getMinY());
    }

    @Test
    void autoCycleNotCalledIfUpdateTimeDoesNotExceedCycleTime() {
        // Arrange

        // Act
        sut.setAutoCycle(10);
        sut.update(11);
        sut.update(2002);

        // Assert
        verify(imageView).setViewport(any());
    }

    @Test
    void autoCycleCalledIfUpdateTimeExceedsCycleTimeCorrectly() {
        // Arrange

        // Act
        sut.setAutoCycle(10);
        sut.update(10000001);
        sut.update(20000002);

        // Assert
        verify(imageView, atLeast(3)).setViewport(any());
    }

    @Test
    void autoCycleGoesToStartOfRow() {
        // Arrange
        sut.setSpriteIndex(4);

        // Act
        sut.setAutoCycle(10, 0);

        // Assert
        assertEquals(0, sut.getFrameIndex());
    }

    @Test
    void autoCycleWrapsToStartOfRowWhenEndOfRowIsReached() {
        // Arrange

        // Act
        sut.setAutoCycle(10, 0);
        sut.update(10000001);
        sut.update(20000002);
        assertEquals(2, sut.getFrameIndex());
        sut.update(30000003);

        // Assert
        assertEquals(0, sut.getFrameIndex());
    }

    @Test
    void autoCyclingThroughNonexistentRowThrowsException() {
        // Arrange

        // Act, Assert
        assertThrows(IllegalArgumentException.class,
                () -> sut.setAutoCycle(10, 4));
    }
}
