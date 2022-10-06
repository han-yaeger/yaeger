package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.entities.FiniteAnimation;
import com.github.hanyaeger.api.entities.LoopingAnimation;
import com.github.hanyaeger.api.entities.LinkedAnimation;
import com.github.hanyaeger.core.ResourceConsumer;
import com.github.hanyaeger.core.exceptions.YaegerEngineException;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpriteFiniteAnimationDelegateTest implements ResourceConsumer {

    private static final double IMAGE_WIDTH = 100d;
    private static final double IMAGE_HEIGHT = 25d;
    private static final int ROWS = 4;
    private static final int COLUMNS = 3;
    private static final double DELTA = 0.00000000000001d;

    private ImageView imageView;
    private SpriteAnimationDelegate sut;

    @BeforeEach
    void setup() {
        imageView = mock(ImageView.class);
        var image = mock(Image.class);

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
        sut.setFrameIndex(1);

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
        sut.setFrameIndex(expected);

        // Act
        var actual = sut.getFrameIndex();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getAutoCycleIntervalReturnsSameAutoCycleAsSet() {
        // Arrange
        var expected = 37;

        // Act
        sut.setAutoCycleInterval(expected);

        // Assert
        assertEquals(expected, sut.getAutoCycleInterval());
    }

    @Test
    void autoCycleNotCalledIfUpdateTimeDoesNotExceedCycleTime() {
        // Arrange

        // Act
        sut.setAutoCycle(10, -1);
        sut.update(11);
        sut.update(2002);

        // Assert
        verify(imageView).setViewport(any());
    }

    @Test
    void autoCycleCalledIfUpdateTimeExceedsCycleTimeCorrectly() {
        // Arrange

        // Act
        sut.setAutoCycle(10, -1);
        sut.update(10000001);
        sut.update(20000002);

        // Assert
        verify(imageView, atLeast(3)).setViewport(any());
    }

    @Test
    void changingAutoCycleDoesNotAlterRow() {
        // Arrange

        final var ROW = 3;
        sut.setAutoCycle(2, ROW);

        // Act
        sut.setAutoCycleInterval(10);

        // Assert
        assertEquals(ROW, sut.getCyclingRow());
    }

    @Test
    void autoCycleContinuesOnCurrentRow() {
        // Arrange
        sut.setFrameIndex(4);

        // Act
        sut.setAutoCycle(10, 0);

        // Assert
        assertEquals(4, sut.getFrameIndex());
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
    void setAutoCycleRowThrowsExceptionIfRowIsNonExistent() {
        // Arrange

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sut.setAutoCycleRow(4));
    }

    @Test
    void setAutoCycleRowThrowsExceptionIfRowHasNegativeValue() {
        // Arrange

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sut.setAutoCycleRow(-2));
    }

    @Test
    void autoCyclingThroughNonexistentRowThrowsException() {
        // Arrange

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sut.setAutoCycle(10, 4));
    }

    @Test
    void autoCyclingThroughNegativeRowThrowsException() {
        // Arrange

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sut.setAutoCycle(10, -2));
    }

    @Nested
    class AnimationTest {

        @Test
        void thePlayedAnimationIsReturnedAsTheCurrentAnimationTest() {
            // Arrange
            var expected = new FiniteAnimation(0, 2, 1, 3);
            sut.playAnimation(expected, false);

            // Act
            var actual = sut.getCurrentAnimation();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        void playAnimationSetsAutoCycleRowToMinusOneTest() {
            // Arrange
            sut.setAutoCycleRow(2);
            var animation = new FiniteAnimation(0, 2, 1, 3);

            // Act
            sut.playAnimation(animation, false);

            // Assert
            assertEquals(-1, sut.getCyclingRow());
        }

        @Test
        void playAnimationKeepsCurrentAutoCycleIntervalIfItHasBeenSetTest() {
            // Arrange
            var actual = 37;
            sut.setAutoCycleInterval(actual);
            var animation = new FiniteAnimation(0, 2, 1, 3);

            // Act
            sut.playAnimation(animation, false);

            // Assert
            assertEquals(actual, sut.getAutoCycleInterval());
        }

        @Test
        void playAnimationSetsAutoCycleIntervalToDefaultIfNoneIsSetOrGivenTest() {
            // Arrange
            var animation = new FiniteAnimation(0, 2, 1, 3);

            // Act
            sut.playAnimation(animation, false);

            // Assert
            var actual = sut.getAutoCycleInterval();
            assertEquals(FiniteAnimation.DEFAULT_AUTOCYCLE_INTERVAL, actual);
        }

        @Test
        void playAnimationSetsCurrentIndexOnFirstFrameFromAnimationWhenAnimationStartsOnFirstRowTest() {
            // Arrange
            var animation = new FiniteAnimation(0, 2, 1, 3);

            // Act
            sut.playAnimation(animation, false);

            // Assert
            assertEquals(2, sut.getFrameIndex());
        }

        @Test
        void playAnimationSetsCurrentIndexOnFirstFrameFromAnimationWhenAnimationStartsOnThirdRowTest() {
            // Arrange
            var animation = new FiniteAnimation(2, 2, 1, 3);

            // Act
            sut.playAnimation(animation, false);

            // Assert
            assertEquals(8, sut.getFrameIndex());
        }

        @Test
        void animationGetAnimatedTest() {
            // Arrange
            var animation = new FiniteAnimation(0, 0, 0, 2);
            sut.playAnimation(animation, false);

            // Act
            sut.update(100000001);

            // Assert
            assertEquals(1, sut.getFrameIndex());
        }

        @Test
        void loopingAnimationLoopsTest() {
            // Arrange
            var animation = new LoopingAnimation(0, 0, 0, 2);
            sut.playAnimation(animation, false);

            // Act
            sut.update(100000001);
            sut.update(200000002);
            sut.update(300000003);
            // Assert
            assertEquals(0, sut.getFrameIndex());
        }

        @Test
        void linkedAnimationPlaysNextTest() {
            // Arrange
            var expected = new LoopingAnimation(0, 0, 1, 1);
            var animation = new LinkedAnimation(0, 0, 0, 2, expected);
            sut.playAnimation(animation, false);

            // Act
            sut.update(100000001);
            sut.update(200000002);
            sut.update(300000003);
            // Assert
            assertEquals(expected, sut.getCurrentAnimation());
        }

        @Test
        void nonLoopingAnimationStopsAutoCycleTest() {
            // Arrange
            var animation = new FiniteAnimation(0, 0, 0, 2);
            sut.playAnimation(animation, false);

            // Act
            sut.update(100000001);
            sut.update(200000002);
            sut.update(300000003);

            // Assert
            assertEquals(2, sut.getFrameIndex());
            assertEquals(0, sut.getAutoCycleInterval());
        }

        @Test
        void queuedAnimationPlayedAfterFiniteAnimation() {
            // Arrange
            var animation = new FiniteAnimation(0, 0, 0, 2);
            var queuedAnimation = new FiniteAnimation(0, 0, 0, 2);
            sut.playAnimation(animation, false);
            sut.update(100000001);
            sut.queueAnimation(queuedAnimation);

            // Act
            sut.update(200000002);
            sut.update(300000003);

            // Assert
            assertEquals(queuedAnimation, sut.getCurrentAnimation());
        }

        @Test
        void queuedAnimationPlayedAfterLoopingAnimation() {
            // Arrange
            var animation = new LoopingAnimation(0, 0, 0, 2);
            var queuedAnimation = new FiniteAnimation(0, 0, 0, 2);
            sut.playAnimation(animation, false);
            sut.update(100000001);
            sut.queueAnimation(queuedAnimation);

            // Act
            sut.update(200000002);
            sut.update(300000003);

            // Assert
            assertEquals(queuedAnimation, sut.getCurrentAnimation());
        }

        @Test
        void queuedAnimationPlayedAfterLinkedAnimation() {
            // Arrange
            var nexAnimation = new FiniteAnimation(0, 0, 0, 2);
            var animation = new LinkedAnimation(0, 0, 0, 2, nexAnimation);
            var queuedAnimation = new FiniteAnimation(0, 0, 0, 3);
            sut.playAnimation(animation, false);
            sut.update(100000001);
            sut.queueAnimation(queuedAnimation);

            // Act
            sut.update(200000002);
            sut.update(300000003);

            // Assert
            assertEquals(queuedAnimation, sut.getCurrentAnimation());
        }

        @Test
        void queAnimationThrowsExceptionIfNoAnimationIsPlayedTest() {
            // Arrange
            var queuedAnimation = new FiniteAnimation(0, 0, 0, 3);

            // Act & Assert
            assertThrows(YaegerEngineException.class, () -> sut.queueAnimation(queuedAnimation));
        }

        @Test
        void unQueAnimationThrowsExceptionIfNoAnimationIsQueuedTest() {
            // Arrange, Act & Assert
            assertThrows(YaegerEngineException.class, () -> sut.unQueueAnimation());
        }

        @Test
        void unQueueAnimationUnQueuesTheQueuedAnimation() {
            // Arrange
            var nexAnimation = new FiniteAnimation(0, 0, 0, 2);
            var animation = new LinkedAnimation(0, 0, 0, 2, nexAnimation);
            var queuedAnimation = new FiniteAnimation(0, 0, 0, 3);
            sut.playAnimation(animation, false);
            sut.update(100000001);
            sut.queueAnimation(queuedAnimation);

            // Act
            sut.unQueueAnimation();
            sut.update(200000002);
            sut.update(300000003);

            // Assert
            assertEquals(nexAnimation, sut.getCurrentAnimation());
        }

        @Test
        void unQueueSpecificAnimationUnQueuesTheQueuedAnimation() {
            // Arrange
            var nexAnimation = new FiniteAnimation(0, 0, 0, 2);
            var animation = new LinkedAnimation(0, 0, 0, 2, nexAnimation);
            var queuedAnimation = new FiniteAnimation(0, 0, 0, 3);
            sut.playAnimation(animation, false);
            sut.update(100000001);
            sut.queueAnimation(queuedAnimation);

            // Act
            sut.unQueueAnimation(queuedAnimation);
            sut.update(200000002);
            sut.update(300000003);

            // Assert
            assertEquals(nexAnimation, sut.getCurrentAnimation());
        }
    }
}
