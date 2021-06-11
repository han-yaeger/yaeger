package com.github.hanyaeger.api.media;

import com.github.hanyaeger.core.repositories.AudioRepository;
import javafx.scene.media.AudioClip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;

class SoundClipTest {

    private final String DEFAULT_PATH = "audio/testaudio.mp3";
    private final int DEFAULT_CYCLE_COUNT = 1;
    private final int CUSTOM_CYCLE_COUNT = 37;

    private AudioRepository audioRepository;
    private AudioClip audioClip;
    private SoundClip sut;

    @BeforeEach
    void setup() {
        audioRepository = mock(AudioRepository.class);

        audioClip = mock(AudioClip.class);
    }


    @Test
    void playCallsPlayOnClipFromAudioRepository() {
        try (MockedStatic<AudioRepository> audioRepositoryMockedStatic = mockStatic(AudioRepository.class)) {
            audioRepositoryMockedStatic.when(AudioRepository::getInstance).thenReturn(audioRepository);

            // Arrange
            sut = new SoundClip(DEFAULT_PATH);
            when(audioRepository.get(DEFAULT_PATH)).thenReturn(audioClip);

            // Act
            sut.play();

            // Assert
            verify(audioClip).play();
        }
    }

    @Test
    void playAfterDefaultConstructorSetsDefaultCycleCountOnAudioClip() {
        try (MockedStatic<AudioRepository> audioRepositoryMockedStatic = mockStatic(AudioRepository.class)) {
            audioRepositoryMockedStatic.when(AudioRepository::getInstance).thenReturn(audioRepository);

            // Arrange
            sut = new SoundClip(DEFAULT_PATH);
            when(audioRepository.get(DEFAULT_PATH)).thenReturn(audioClip);

            // Act
            sut.play();

            // Assert
            verify(audioClip).setCycleCount(DEFAULT_CYCLE_COUNT);
        }
    }

    @Test
    void playAfterNonDefaultConstructorSetsCycleCountOnAudioClip() {
        try (MockedStatic<AudioRepository> audioRepositoryMockedStatic = mockStatic(AudioRepository.class)) {
            audioRepositoryMockedStatic.when(AudioRepository::getInstance).thenReturn(audioRepository);

            // Arrange
            sut = new SoundClip(DEFAULT_PATH, CUSTOM_CYCLE_COUNT);
            when(audioRepository.get(DEFAULT_PATH)).thenReturn(audioClip);

            // Act
            sut.play();

            // Assert
            verify(audioClip).setCycleCount(CUSTOM_CYCLE_COUNT);
        }
    }

    @Test
    void stopCallsStopOnAudioClip() {
        try (MockedStatic<AudioRepository> audioRepositoryMockedStatic = mockStatic(AudioRepository.class)) {
            audioRepositoryMockedStatic.when(AudioRepository::getInstance).thenReturn(audioRepository);

            // Arrange
            sut = new SoundClip(DEFAULT_PATH);
            when(audioRepository.get(DEFAULT_PATH)).thenReturn(audioClip);

            sut.play();

            // Act
            sut.stop();

            // Assert
            verify(audioClip).stop();
        }
    }

    @Test
    void stopDoesntFailIfPlayHasNotBeenCalled() {
        try (MockedStatic<AudioRepository> audioRepositoryMockedStatic = mockStatic(AudioRepository.class)) {
            audioRepositoryMockedStatic.when(AudioRepository::getInstance).thenReturn(audioRepository);

            // Arrange
            sut = new SoundClip(DEFAULT_PATH);
            when(audioRepository.get(DEFAULT_PATH)).thenReturn(audioClip);

            // Act
            sut.stop();

            // Assert
            verifyNoInteractions(audioClip);
        }
    }
}
