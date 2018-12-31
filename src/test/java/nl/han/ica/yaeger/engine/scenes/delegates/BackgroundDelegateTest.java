package nl.han.ica.yaeger.engine.scenes.delegates;

import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import nl.han.ica.yaeger.engine.media.audio.SoundClip;
import nl.han.ica.yaeger.engine.media.repositories.AudioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class BackgroundDelegateTest {

    private String audioFile = "testAudio.mp3";
    private String image = "testImage.png";
    private BackgroundDelegate backgroundDelegate;
    private Scene scene;

    @BeforeEach
    void setup() {
        scene = mock(Scene.class);

        backgroundDelegate = new BackgroundDelegate();
        backgroundDelegate.setup(scene);
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
        AudioClip audioClip = mock(AudioClip.class);

        AudioRepository audioRepository = mock(AudioRepository.class);
        backgroundDelegate.setAudioRepository(audioRepository);
        when(audioRepository.get(audioFile, SoundClip.INDEFINITE)).thenReturn(audioClip);

        // Test
        backgroundDelegate.setBackgroundAudio(audioFile);

        // Verify
        verify(audioClip).play();
    }

    @Test
    void destroyStopsAudioFile() {
        // Setup
        AudioClip audioClip = mock(AudioClip.class);

        AudioRepository audioRepository = mock(AudioRepository.class);
        backgroundDelegate.setAudioRepository(audioRepository);
        when(audioRepository.get(audioFile, SoundClip.INDEFINITE)).thenReturn(audioClip);
        backgroundDelegate.setBackgroundAudio(audioFile);

        // Test
        backgroundDelegate.destroy();

        // Verify
        verify(audioClip).stop();
    }
}
