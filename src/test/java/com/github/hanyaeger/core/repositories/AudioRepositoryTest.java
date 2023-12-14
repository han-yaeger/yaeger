package com.github.hanyaeger.core.repositories;

import javafx.scene.media.AudioClip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudioRepositoryTest {

    private AudioRepository audioRepository;
    @BeforeEach
    void setup() {
        audioRepository = AudioRepository.getInstance();
    }

    @Test
    void afterDestroyRepositoryIsEmpty() {
        // Arrange
        audioRepository.destroy();

        // Act
        int size = audioRepository.size();

        // Assert
        assertEquals(0, size);
    }

    @Test
    void getCreatesAndReturnsAnAudioClip() {
        // Arrange
        audioRepository.destroy();

        // Act
        AudioClip audioClip = audioRepository.get("audio/testaudio2.mp3");

        // Assert
        assertNotNull(audioClip);
        assertEquals(1, audioRepository.size());
    }

    @Test
    void callingGetTwiceCreatesCreatesOnlyOneAudioClip() {
        // Arrange
        audioRepository.destroy();

        // Act
        AudioClip audioClip1 = audioRepository.get("audio/testaudio2.mp3");
        AudioClip audioClip2 = audioRepository.get("audio/testaudio2.mp3");

        // Assert
        assertSame(audioClip1, audioClip2);
        assertEquals(1, audioRepository.size());
    }

    @Test
    void callingGetWithDifferentUrlsCreatesDifferentAudioClips() {
        // Arrange
        audioRepository.destroy();

        // Act
        AudioClip audioClip1 = audioRepository.get("audio/testaudio.mp3");
        AudioClip audioClip2 = audioRepository.get("audio/testaudio2.mp3");

        // Assert
        assertNotSame(audioClip1, audioClip2);
        assertEquals(2, audioRepository.size());
    }

    @Test
    void getWithAGivenCycleCountsReturnsAnAudioClip() {
        // Arrange
        audioRepository.destroy();

        // Act
        AudioClip audioClip = audioRepository.get("audio/testaudio2.mp3", 2);

        // Assert
        assertNotNull(audioClip);
        assertEquals(1, audioRepository.size());
    }

    @Test
    void callingGetWithDifferentCycleCountsCreatesCreatesDifferentAudioClips() {
        // Arrange
        audioRepository.destroy();

        // Act
        AudioClip audioClip1 = audioRepository.get("audio/testaudio2.mp3");
        AudioClip audioClip2 = audioRepository.get("audio/testaudio2.mp3", 3);

        // Assert
        assertNotSame(audioClip1, audioClip2);
        assertEquals(2, audioRepository.size());
    }
}
