package nl.han.ica.yaeger.engine.scenes.delegates;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.ImagePattern;
import nl.han.ica.yaeger.engine.media.audio.SoundClip;
import nl.han.ica.yaeger.engine.media.repositories.AudioRepository;
import nl.han.ica.yaeger.engine.media.repositories.ImageRepository;
import nl.han.ica.yaeger.javafx.factories.ImagePatternFactory;
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
        // Setup

        // Test
        backgroundDelegate.destroy();
        // Verify
        verify(scene).setFill(null);
    }

    @Test
    void setBackgroundAudioPlaysAudioFile() {
        // Setup
        var audioClip = mock(AudioClip.class);

        var audioRepository = mock(AudioRepository.class);
        backgroundDelegate.setAudioRepository(audioRepository);
        when(audioRepository.get(audioFile, SoundClip.INDEFINITE)).thenReturn(audioClip);

        // Test
        backgroundDelegate.setBackgroundAudio(audioFile);

        // Verify
        verify(audioClip).play();
    }

    @Test
    void setBackgroundImageSetImageOnScene() {
        // Setup
        var image = mock(Image.class);
        var imagePattern = mock(ImagePattern.class);

        var imageRepository = mock(ImageRepository.class);
        backgroundDelegate.setImageRepository(imageRepository);
        when(imageRepository.get(imageFile)).thenReturn(image);
        when(imagePatternFactory.create(image)).thenReturn(imagePattern);

        // Test
        backgroundDelegate.setBackgroundImage(imageFile);

        // Verify
        verify(scene).setFill(imagePattern);
    }

    @Test
    void destroyStopsAudioFile() {
        // Setup
        var audioClip = mock(AudioClip.class);

        var audioRepository = mock(AudioRepository.class);
        backgroundDelegate.setAudioRepository(audioRepository);
        when(audioRepository.get(audioFile, SoundClip.INDEFINITE)).thenReturn(audioClip);
        backgroundDelegate.setBackgroundAudio(audioFile);

        // Test
        backgroundDelegate.destroy();

        // Verify
        verify(audioClip).stop();
    }
}
