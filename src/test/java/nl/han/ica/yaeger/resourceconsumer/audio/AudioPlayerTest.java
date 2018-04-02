package nl.han.ica.yaeger.resourceconsumer.audio;

import javafx.scene.Scene;
import nl.han.ica.yaeger.resourceconsumer.audio.exceptions.AudioFileIsNullException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AudioPlayerTest {

    private Scene scene;
    private AudioPlayer audioPlayer;

    @BeforeEach
    public void setup() {
        scene = Mockito.mock(Scene.class);
        audioPlayer = new AudioPlayer(scene);
    }

    @Test
    public void playAudioFileWithNullTrowsException() {
        Assertions.assertThrows(AudioFileIsNullException.class, () -> {
            this.audioPlayer.playAudio(null);
        });
    }

    @Test
    public void playAudioFileWithNullFilePathTrowsException() {
        var audioFile = new AudioFile(null);

        Assertions.assertThrows(AudioFileIsNullException.class, () -> {
            this.audioPlayer.playAudio(audioFile);
        });
    }
}
