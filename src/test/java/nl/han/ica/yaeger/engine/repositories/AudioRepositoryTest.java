package nl.han.ica.yaeger.engine.repositories;

import nl.han.ica.yaeger.engine.resourceconsumer.audio.SoundClip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AudioRepositoryTest {

    private AudioRepository audioRepository;

    @BeforeEach
    void setup() {
        audioRepository = AudioRepository.getInstance();
    }

    @Test
    void getCreatesAndReturnsASound() {
        // Setup

        // Test
        SoundClip soundClip = audioRepository.get("Waterworld.mp3");

        // Verify
        Assertions.assertNotNull(soundClip);
    }

    @Test
    void callingGetTwiceCreatesCreatesOnlyOneSound() {
        // Setup

        // Test
        SoundClip soundClip1 = audioRepository.get("Waterworld.mp3");
        SoundClip soundClip2 = audioRepository.get("Waterworld.mp3");

        // Verify
        Assertions.assertSame(soundClip1, soundClip2);
    }

    @Test
    void getWithAGivenCycleCountsReturnsASound() {
        // Setup

        // Test
        SoundClip soundClip = audioRepository.get("Waterworld.mp3", 2);

        // Verify
        Assertions.assertNotNull(soundClip);
    }

    @Test
    void callingGetWithDifferentCycleCountsCreatesCreatesDifferentSoundClips() {
        // Setup

        // Test
        SoundClip soundClip1 = audioRepository.get("Waterworld.mp3");
        SoundClip soundClip2 = audioRepository.get("Waterworld.mp3", 3);

        // Verify
        Assertions.assertNotSame(soundClip1, soundClip2);
    }
}
