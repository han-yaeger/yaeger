package nl.meron.yaeger.engine.media.repositories;

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
    void afterDestroyRepositoryIsEmpty() {
        // Setup
        audioRepository.destroy();

        // Test
        int size = audioRepository.size();

        // Verify
        Assertions.assertEquals(0, size);
    }

    @Test
    void getCreatesAndReturnsAnAudioClip() {
        // Setup
        audioRepository.destroy();

        // Test
        AudioClip audioClip = audioRepository.get("waterworld/audio/waterworld.mp3");

        // Verify
        Assertions.assertNotNull(audioClip);
        Assertions.assertEquals(1, audioRepository.size());
    }

    @Test
    void callingGetTwiceCreatesCreatesOnlyOneAudioClip() {
        // Setup
        audioRepository.destroy();

        // Test
        AudioClip audioClip1 = audioRepository.get("waterworld/audio/waterworld.mp3");
        AudioClip audioClip2 = audioRepository.get("waterworld/audio/waterworld.mp3");

        // Verify
        Assertions.assertSame(audioClip1, audioClip2);
        Assertions.assertEquals(1, audioRepository.size());
    }

    @Test
    void callingGetWithDifferentUrlsCreatesDifferentAudioClips() {
        // Setup
        audioRepository.destroy();

        // Test
        AudioClip audioClip1 = audioRepository.get("waterworld/audio/ocean.mp3");
        AudioClip audioClip2 = audioRepository.get("waterworld/audio/waterworld.mp3");

        // Verify
        Assertions.assertNotSame(audioClip1, audioClip2);
        Assertions.assertEquals(2, audioRepository.size());
    }

    @Test
    void getWithAGivenCycleCountsReturnsAnAudioClip() {
        // Setup
        audioRepository.destroy();

        // Test
        AudioClip audioClip = audioRepository.get("waterworld/audio/waterworld.mp3", 2);

        // Verify
        Assertions.assertNotNull(audioClip);
        Assertions.assertEquals(1, audioRepository.size());
    }

    @Test
    void callingGetWithDifferentCycleCountsCreatesCreatesDifferentAudioClips() {
        // Setup
        audioRepository.destroy();

        // Test
        AudioClip audioClip1 = audioRepository.get("waterworld/audio/waterworld.mp3");
        AudioClip audioClip2 = audioRepository.get("waterworld/audio/waterworld.mp3", 3);

        // Verify
        Assertions.assertNotSame(audioClip1, audioClip2);
        Assertions.assertEquals(2, audioRepository.size());
    }
}
