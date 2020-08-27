package com.github.hanyaeger.api.engine.scenes.delegates;

import com.github.hanyaeger.api.engine.media.audio.SoundClip;
import com.github.hanyaeger.api.engine.media.repositories.AudioRepository;
import com.github.hanyaeger.api.engine.media.repositories.ImageRepository;
import com.github.hanyaeger.api.javafx.image.BackgroundFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

class BackgroundDelegateTest {

    private String audioFile = "testAudio.mp3";
    private String imageFile = "testImage.png";
    private BackgroundDelegate sut;
    private BackgroundFactory backgroundFactory;
    private Pane pane;

    @BeforeEach
    void setup() {
        pane = mock(Pane.class);
        backgroundFactory = mock(BackgroundFactory.class);

        sut = new BackgroundDelegate();
        sut.setBackgroundFactory(backgroundFactory);
        sut.setup(pane);
    }

    @Test
    void destroyClearsBackgroundFill() {
        // Arrange

        // Act
        sut.destroy();
        // Verify
        verify(pane).setBackground(null);
    }

    @Test
    void setNullBackgroundAudioDoesNotBreak() {
        // Arrange
        var audioRepository = mock(AudioRepository.class);
        sut.setAudioRepository(audioRepository);

        // Act
        sut.setBackgroundAudio(null);

        // Verify
        verifyNoInteractions(audioRepository);
    }

    @Test
    void setBackgroundAudioPlaysAudioFile() {
        // Arrange
        var audioClip = mock(AudioClip.class);

        var audioRepository = mock(AudioRepository.class);
        sut.setAudioRepository(audioRepository);
        when(audioRepository.get(audioFile, SoundClip.INDEFINITE)).thenReturn(audioClip);

        // Act
        sut.setBackgroundAudio(audioFile);

        // Verify
        verify(audioClip).play();
    }

    @Test
    void setBackgroundColorWithNullPaneDoesNotBreak() {
        // Arrange
        var paneIsNullSut = new BackgroundDelegate();
        var color = Color.YELLOW;

        // Assert & Act
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

        // Verify
        verify(pane).setBackground(background);
    }

    @Test
    void destroyStopsAudioFile() {
        // Arrange
        var audioClip = mock(AudioClip.class);

        var audioRepository = mock(AudioRepository.class);
        sut.setAudioRepository(audioRepository);
        when(audioRepository.get(audioFile, SoundClip.INDEFINITE)).thenReturn(audioClip);
        sut.setBackgroundAudio(audioFile);

        // Act
        sut.destroy();

        // Verify
        verify(audioClip).stop();
    }
}
