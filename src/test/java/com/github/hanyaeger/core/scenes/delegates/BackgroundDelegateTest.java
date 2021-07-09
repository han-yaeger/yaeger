package com.github.hanyaeger.core.scenes.delegates;

import com.github.hanyaeger.core.media.BackgroundAudioMediaPlayer;
import com.github.hanyaeger.core.repositories.ImageRepository;
import com.github.hanyaeger.core.factories.BackgroundFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

class BackgroundDelegateTest {

    private static final String URL_AUDIO = "audio/testaudio.mp3";
    private static final String URL_AUDIO_2 = "audio/testaudio2.mp3";
    private static final String URL_IMAGE = "testImage.png";
    private BackgroundDelegate sut;
    private BackgroundFactory backgroundFactory;
    private BackgroundAudioMediaPlayer backgroundAudioMediaPlayer;
    private Pane pane;

    @BeforeEach
    void setup() {
        pane = mock(Pane.class);
        backgroundFactory = mock(BackgroundFactory.class);
        backgroundAudioMediaPlayer = mock(BackgroundAudioMediaPlayer.class);
    }

    @Nested
    class UninitializedAudioHandling {

        @BeforeEach
        void setup() {
            sut = new BackgroundDelegate();
            sut.setBackgroundFactory(backgroundFactory);
        }

        @Test
        void setVolumeDoeNotBreak() {
            // Arrange
            var volume = 0.37D;

            // Act
            sut.setVolume(volume);

            // Assert
            verifyNoInteractions(backgroundAudioMediaPlayer);
        }

        @Test
        void getVolumeReturns0() {
            // Arrange
            var expected = 0D;

            // Act
            var actual = sut.getVolume();

            // Assert
            Assertions.assertEquals(expected, actual);
        }
    }

    @Nested
    class InitializedAudioHandling {

        @BeforeEach
        void setup() {
            sut = new BackgroundDelegate();
            sut.setBackgroundFactory(backgroundFactory);
            sut.setBackgroundAudioMediaPlayer(backgroundAudioMediaPlayer);
        }

        @Test
        void setBackgroundAudioCallsBackgroundAudioPlayer() {
            // Arrange

            // Act
            sut.setBackgroundAudio(URL_AUDIO);

            // Assert
            verify(backgroundAudioMediaPlayer).playBackgroundAudio(URL_AUDIO);
        }

        @Test
        void stopBackgroundAudioCallsBackgroundAudioPlayer() {
            // Arrange

            // Act
            sut.stopBackgroundAudio();

            // Assert
            verify(backgroundAudioMediaPlayer).stopBackgroundAudio();
        }

        @Test
        void destroyStopsAudioFile() {
            // Arrange
            sut.setup(pane);
            sut.setBackgroundAudio(URL_AUDIO);

            // Act
            sut.destroy();

            // Assert
            verify(backgroundAudioMediaPlayer).destroy();
        }

        @Test
        void setVolumeDelegatesToBackgroundAudioPlayer() {
            // Arrange
            var volume = 0.37D;

            // Act
            sut.setVolume(volume);

            // Assert
            verify(backgroundAudioMediaPlayer).setVolume(volume);
        }

        @Test
        void getVolumeDelegatesToBackgroundAudioPlayer() {
            // Arrange
            var expected = 0.37D;
            when(backgroundAudioMediaPlayer.getVolume()).thenReturn(expected);

            // Act
            var actual = sut.getVolume();

            // Assert
            Assertions.assertEquals(expected, actual);
        }
    }

    @Nested
    class ImageHandling {

        @BeforeEach
        void setup() {
            sut = new BackgroundDelegate();
            sut.setBackgroundFactory(backgroundFactory);
            sut.setBackgroundAudioMediaPlayer(backgroundAudioMediaPlayer);
            sut.setup(pane);
        }

        @Test
        void destroyClearsBackgroundFill() {
            // Arrange

            // Act
            sut.destroy();

            // Assert
            verify(pane).setBackground(null);
        }

        @Test
        void setBackgroundColorWithNullPaneDoesNotBreak() {
            // Arrange
            var paneIsNullSut = new BackgroundDelegate();
            var color = Color.YELLOW;

            // Act & Assert
            Assertions.assertAll(() -> paneIsNullSut.setBackgroundColor(color));
        }

        @Test
        void setBackgroundColorDelegatesToScene() {
            // Arrange
            var color = Color.YELLOW;

            var background = mock(Background.class);
            when(backgroundFactory.createFillBackground(color)).thenReturn(background);

            // Act
            sut.setBackgroundColor(color);

            // Assert
            verify(pane).setBackground(background);
        }

        @Test
        void setBackgroundImageTiledSetsTiledImageOnScene() {
            // Arrange
            var image = mock(Image.class);

            var imageRepository = mock(ImageRepository.class);
            var background = mock(Background.class);
            sut.setImageRepository(imageRepository);
            when(imageRepository.get(URL_IMAGE)).thenReturn(image);
            when(backgroundFactory.createImageBackground(image, false)).thenReturn(background);

            // Act
            sut.setBackgroundImage(URL_IMAGE, false);

            // Assert
            verify(pane).setBackground(background);
        }

        @Test
        void setBackgroundImageFullscreenSetsFullscreenImageOnScene() {
            // Arrange
            var image = mock(Image.class);

            var imageRepository = mock(ImageRepository.class);
            var background = mock(Background.class);
            sut.setImageRepository(imageRepository);
            when(imageRepository.get(URL_IMAGE)).thenReturn(image);
            when(backgroundFactory.createImageBackground(image, true)).thenReturn(background);

            // Act
            sut.setBackgroundImage(URL_IMAGE, true);

            // Assert
            verify(pane).setBackground(background);
        }
    }
}
