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
    void getCreatesAndReturnsASound() {
        // Setup

        // Test
        AudioClip audioClip = audioRepository.get("Waterworld.mp3");

        // Verify
        Assertions.assertNotNull(audioClip);
    }

    @Test
    void callingGetTwiceCreatesCreatesOnlyOneSound() {
        // Setup

        // Test
        AudioClip audioClip1 = audioRepository.get("Waterworld.mp3");
        AudioClip audioClip2 = audioRepository.get("Waterworld.mp3");

        // Verify
        Assertions.assertSame(audioClip1, audioClip2);
    }

    @Test
    void getWithAGivenCycleCountsReturnsASound() {
        // Setup

        // Test
        AudioClip audioClip = audioRepository.get("Waterworld.mp3", 2);

        // Verify
        Assertions.assertNotNull(audioClip);
    }

    @Test
    void callingGetWithDifferentCycleCountsCreatesCreatesDifferentSoundClips() {
        // Setup

        // Test
        AudioClip audioClip1 = audioRepository.get("Waterworld.mp3");
        AudioClip audioClip2 = audioRepository.get("Waterworld.mp3", 3);

        // Verify
        Assertions.assertNotSame(audioClip1, audioClip2);
    }
}
