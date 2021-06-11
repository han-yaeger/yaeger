package com.github.hanyaeger.core.scenes.delegates;

import com.github.hanyaeger.api.media.SoundClip;
import com.github.hanyaeger.core.repositories.AudioRepository;
import com.github.hanyaeger.core.repositories.ImageRepository;
import com.github.hanyaeger.core.factories.BackgroundFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;


import static org.mockito.Mockito.*;

class BackgroundDelegateTest {

    private final String audioFile = "audio/testaudio.mp3";
    private final String secondAudioFile = "audio/testaudio2.mp3";
    private final String imageFile = "testImage.png";
    private BackgroundDelegate sut;
    private BackgroundFactory backgroundFactory;
    private Pane pane;

    @BeforeEach
    void setup() {
        pane = mock(Pane.class);
        backgroundFactory = mock(BackgroundFactory.class);
    }

    @Nested
    class AudioHandling {

        @BeforeEach
        void setup() {
            sut = new BackgroundDelegate();
            sut.setBackgroundFactory(backgroundFactory);
        }

        @Test
        void setNullBackgroundAudioDoesNotBreak() {
            var audioRepository = mock(AudioRepository.class);

            try (MockedStatic<AudioRepository> utilities = mockStatic(AudioRepository.class)) {
                utilities.when(AudioRepository::getInstance).thenReturn(audioRepository);

                // Arrange
                sut.setup(pane);

                // Act
                sut.setBackgroundAudio(null);

                // Assert
                verifyNoInteractions(audioRepository);
            }
        }

        @Test
        void setBackgroundAudioPlaysAudioFile() {
            // Arrange
            var audioClip = mock(AudioClip.class);
            var audioRepository = mock(AudioRepository.class);
            when(audioRepository.get(audioFile, SoundClip.INDEFINITE)).thenReturn(audioClip);

            try (MockedStatic<AudioRepository> utilities = mockStatic(AudioRepository.class)) {
                utilities.when(AudioRepository::getInstance).thenReturn(audioRepository);

                // Arrange
                sut.setup(pane);

                // Act
                sut.setBackgroundAudio(audioFile);

                // Assert
                verify(audioClip).play();
            }
        }

        @Test
        void setBackgroundAudioStopsPreviousChoseAudioFile() {
            // Arrange
            var audioClip = mock(AudioClip.class);
            var secondAudioClip = mock(AudioClip.class);

            var audioRepository = mock(AudioRepository.class);
            when(audioRepository.get(audioFile, SoundClip.INDEFINITE)).thenReturn(audioClip);
            when(audioRepository.get(secondAudioFile, SoundClip.INDEFINITE)).thenReturn(secondAudioClip);

            try (MockedStatic<AudioRepository> utilities = mockStatic(AudioRepository.class)) {
                utilities.when(AudioRepository::getInstance).thenReturn(audioRepository);

                // Arrange
                sut.setup(pane);
                sut.setBackgroundAudio(audioFile);

                // Act
                sut.setBackgroundAudio(secondAudioFile);

                // Assert
                verify(audioClip).stop();
            }
        }

        @Test
        void destroyStopsAudioFile() {
            // Arrange
            var audioClip = mock(AudioClip.class);
            var audioRepository = mock(AudioRepository.class);
            when(audioRepository.get(audioFile, SoundClip.INDEFINITE)).thenReturn(audioClip);

            try (MockedStatic<AudioRepository> utilities = mockStatic(AudioRepository.class)) {
                utilities.when(AudioRepository::getInstance).thenReturn(audioRepository);

                // Arrange
                sut.setup(pane);
                sut.setBackgroundAudio(audioFile);

                // Act
                sut.destroy();

                // Assert
                verify(audioClip).stop();
            }
        }
    }

    @Nested
    class ImageHandling {

        @BeforeEach
        void setup() {
            sut = new BackgroundDelegate();
            sut.setBackgroundFactory(backgroundFactory);
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
        void setBackgroundImageSetImageOnScene() {
            // Arrange
            var image = mock(Image.class);

            var imageRepository = mock(ImageRepository.class);
            var background = mock(Background.class);
            sut.setImageRepository(imageRepository);
            when(imageRepository.get(imageFile)).thenReturn(image);
            when(backgroundFactory.createImageBackground(image)).thenReturn(background);

            // Act
            sut.setBackgroundImage(imageFile);

            // Assert
            verify(pane).setBackground(background);
        }
    }
}
