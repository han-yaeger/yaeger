package com.github.hanyaeger.api.engine.scenes.delegates;

import com.github.hanyaeger.api.engine.media.audio.SoundClip;
import com.github.hanyaeger.api.engine.media.repositories.AudioRepository;
import com.github.hanyaeger.api.engine.media.repositories.ImageRepository;
import com.github.hanyaeger.api.javafx.image.ImagePatternFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

class BackgroundDelegateTest {

    private String audioFile = "testAudio.mp3";
    private String imageFile = "testImage.png";
    private BackgroundDelegate backgroundDelegate;
    private ImagePatternFactory imagePatternFactory;
    private Scene scene;

    @BeforeEach
    void setup() {
        scene = mock(Scene.class);
        imagePatternFactory = mock(ImagePatternFactory.class);

        backgroundDelegate = new BackgroundDelegate();
        backgroundDelegate.setup(scene);
        backgroundDelegate.setImagePatternFactory(imagePatternFactory);
    }

    @Test
    void destroyClearsBackgroundFill() {
        // Arrange

        // Act
        backgroundDelegate.destroy();
        // Verify
        verify(scene).setFill(null);
    }

    @Test
    void setBackgroundAudioPlaysAudioFile() {
        // Arrange
        var audioClip = mock(AudioClip.class);

        var audioRepository = mock(AudioRepository.class);
        backgroundDelegate.setAudioRepository(audioRepository);
        when(audioRepository.get(audioFile, SoundClip.INDEFINITE)).thenReturn(audioClip);

        // Act
        backgroundDelegate.setBackgroundAudio(audioFile);

        // Verify
        verify(audioClip).play();
    }

    @Test
    void setBackgroundColorDelegatesToScene() {
        // Arrange
        var color = Color.YELLOW;

        // Act
        backgroundDelegate.setBackgroundColor(color);

        // Assert
        verify(scene).setFill(color);
    }

    @Test
    void setBackgroundImageSetImageOnScene() {
        // Arrange
        var image = mock(Image.class);
        var imagePattern = mock(ImagePattern.class);

        var imageRepository = mock(ImageRepository.class);
        backgroundDelegate.setImageRepository(imageRepository);
        when(imageRepository.get(imageFile)).thenReturn(image);
        when(imagePatternFactory.create(image)).thenReturn(imagePattern);

        // Act
        backgroundDelegate.setBackgroundImage(imageFile);

        // Verify
        verify(scene).setFill(imagePattern);
    }

    @Test
    void destroyStopsAudioFile() {
        // Arrange
        var audioClip = mock(AudioClip.class);

        var audioRepository = mock(AudioRepository.class);
        backgroundDelegate.setAudioRepository(audioRepository);
        when(audioRepository.get(audioFile, SoundClip.INDEFINITE)).thenReturn(audioClip);
        backgroundDelegate.setBackgroundAudio(audioFile);

        // Act
        backgroundDelegate.destroy();

        // Verify
        verify(audioClip).stop();
    }
}
