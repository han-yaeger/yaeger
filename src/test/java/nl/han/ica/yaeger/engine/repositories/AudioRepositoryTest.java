package nl.han.ica.yaeger.engine.repositories;

import javafx.scene.media.AudioClip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AudioRepositoryTest {

    private AudioRepository audioRepository;

    @BeforeEach
    void setup() {
        audioRepository = AudioRepository.getInstance();
    }

    @Test
    void getCreatesAndReturnsAnAudioClip() {
        // Setup
        audioRepository.destroy();

        // Test
        AudioClip audioClip = audioRepository.get("audio/waterworld.mp3");

        // Verify
        Assertions.assertNotNull(audioClip);
        Assertions.assertEquals(1, audioRepository.size());
    }

    @Test
    void callingGetTwiceCreatesCreatesOnlyOneAudioClip() {
        // Setup
        audioRepository.destroy();

        // Test
        AudioClip audioClip1 = audioRepository.get("audio/waterworld.mp3");
        AudioClip audioClip2 = audioRepository.get("audio/waterworld.mp3");

        // Verify
        Assertions.assertSame(audioClip1, audioClip2);
        Assertions.assertEquals(1, audioRepository.size());
    }

    @Test
    void callingGetWithDifferentUrlsCreatesDifferentAudioClips() {
        // Setup
        audioRepository.destroy();

        // Test
        AudioClip audioClip1 = audioRepository.get("audio/ocean.mp3");
        AudioClip audioClip2 = audioRepository.get("audio/waterworld.mp3");

        // Verify
        Assertions.assertNotSame(audioClip1, audioClip2);
        Assertions.assertEquals(2, audioRepository.size());
    }

    @Test
    void getWithAGivenCycleCountsReturnsAnAudioClip() {
        // Setup
        audioRepository.destroy();

        // Test
        AudioClip audioClip = audioRepository.get("audio/waterworld.mp3", 2);

        // Verify
        Assertions.assertNotNull(audioClip);
        Assertions.assertEquals(1, audioRepository.size());
    }

    @Test
    void callingGetWithDifferentCycleCountsCreatesCreatesDifferentAudioClips() {
        // Setup
        audioRepository.destroy();

        // Test
        AudioClip audioClip1 = audioRepository.get("audio/waterworld.mp3");
        AudioClip audioClip2 = audioRepository.get("audio/waterworld.mp3", 3);

        // Verify
        Assertions.assertNotSame(audioClip1, audioClip2);
        Assertions.assertEquals(2, audioRepository.size());
    }
}
