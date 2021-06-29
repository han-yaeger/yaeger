package com.github.hanyaeger.core.media;

import com.github.hanyaeger.core.factories.MediaFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class BackgroundAudioMediaPlayerTest {

    private static final String URL = "audio/testaudio.mp3";
    private static final String URL2 = "audio/testaudio2.mp3";

    private BackgroundAudioMediaPlayer sut;
    private Media media;
    private MediaPlayer mediaPlayer;

    @BeforeEach
    void setup() {
        media = mock(Media.class);
        mediaPlayer = mock(MediaPlayer.class);

        sut = new BackgroundAudioMediaPlayer();
    }

    @Test
    void playBackgroundAudioCreatesMediaMediaPlayerAndCallsPlay() {
        // Arrange
        try (MockedStatic<MediaFactory> factory = Mockito.mockStatic(MediaFactory.class)) {
            factory.when(() -> MediaFactory.createMedia(anyString())).thenReturn(media);
            factory.when(() -> MediaFactory.createMediaPlayer(any(Media.class))).thenReturn(mediaPlayer);

            // Act
            sut.playBackgroundAudio(URL);

            // Assert
            verify(mediaPlayer).play();
        }
    }

    @Test
    void playBackgroundAudioOnAlreadyPlayingAudioCallsStop() {
        // Arrange
        try (MockedStatic<MediaFactory> factory = Mockito.mockStatic(MediaFactory.class)) {
            factory.when(() -> MediaFactory.createMedia(anyString())).thenReturn(media);
            factory.when(() -> MediaFactory.createMediaPlayer(any(Media.class))).thenReturn(mediaPlayer);
            sut.playBackgroundAudio(URL);

            // Act
            sut.playBackgroundAudio(URL2);

            // Assert
            verify(mediaPlayer).stop();
        }
    }

    @Test
    void playBackgroundAudioSetsVolumeTo1() {
        // Arrange
        try (MockedStatic<MediaFactory> factory = Mockito.mockStatic(MediaFactory.class)) {
            factory.when(() -> MediaFactory.createMedia(anyString())).thenReturn(media);
            factory.when(() -> MediaFactory.createMediaPlayer(any(Media.class))).thenReturn(mediaPlayer);

            // Act
            sut.playBackgroundAudio(URL);

            // Assert
            verify(mediaPlayer).setVolume(1D);
        }
    }

    @Test
    void playBackgroundAudioSetsCycleCountToIndefinite() {
        // Arrange
        try (MockedStatic<MediaFactory> factory = Mockito.mockStatic(MediaFactory.class)) {
            factory.when(() -> MediaFactory.createMedia(anyString())).thenReturn(media);
            factory.when(() -> MediaFactory.createMediaPlayer(any(Media.class))).thenReturn(mediaPlayer);

            // Act
            sut.playBackgroundAudio(URL);

            // Assert
            verify(mediaPlayer).setCycleCount(MediaPlayer.INDEFINITE);
        }
    }

    @Test
    void stopBackgroundAudioCallsStopOnMediaPlayer() {
        // Arrange
        try (MockedStatic<MediaFactory> factory = Mockito.mockStatic(MediaFactory.class)) {
            factory.when(() -> MediaFactory.createMedia(anyString())).thenReturn(media);
            factory.when(() -> MediaFactory.createMediaPlayer(any(Media.class))).thenReturn(mediaPlayer);
            sut.playBackgroundAudio(URL);

            // Act
            sut.stopBackgroundAudio();

            // Assert
            verify(mediaPlayer).stop();
        }
    }

    @Test
    void destroyCallsStopOnMediaPlayer() {
        // Arrange
        try (MockedStatic<MediaFactory> factory = Mockito.mockStatic(MediaFactory.class)) {
            factory.when(() -> MediaFactory.createMedia(anyString())).thenReturn(media);
            factory.when(() -> MediaFactory.createMediaPlayer(any(Media.class))).thenReturn(mediaPlayer);
            sut.playBackgroundAudio(URL);

            // Act
            sut.destroy();

            // Assert
            verify(mediaPlayer).stop();
        }
    }

    @Test
    void setVolumeSetsVolumeOnMediaPlayer() {
        // Arrange
        try (MockedStatic<MediaFactory> factory = Mockito.mockStatic(MediaFactory.class)) {
            factory.when(() -> MediaFactory.createMedia(anyString())).thenReturn(media);
            factory.when(() -> MediaFactory.createMediaPlayer(any(Media.class))).thenReturn(mediaPlayer);
            sut.playBackgroundAudio(URL);

            var expected = 3.7D;

            // Act
            sut.setVolume(expected);

            // Assert
            verify(mediaPlayer).setVolume(expected);
        }
    }

    @Test
    void setVolumeOnUninitializedMediaPlayerSetsVolumeOnPlay() {
        // Arrange
        try (MockedStatic<MediaFactory> factory = Mockito.mockStatic(MediaFactory.class)) {
            factory.when(() -> MediaFactory.createMedia(anyString())).thenReturn(media);
            factory.when(() -> MediaFactory.createMediaPlayer(any(Media.class))).thenReturn(mediaPlayer);

            var expected = 3.7D;
            sut.setVolume(expected);

            // Act
            sut.playBackgroundAudio(URL);

            // Assert
            verify(mediaPlayer).setVolume(expected);
        }
    }

    @Test
    void getVolumeOnUninitializedMediaPlayerReturnsSetValue() {
        // Arrange
        try (MockedStatic<MediaFactory> factory = Mockito.mockStatic(MediaFactory.class)) {
            factory.when(() -> MediaFactory.createMedia(anyString())).thenReturn(media);
            factory.when(() -> MediaFactory.createMediaPlayer(any(Media.class))).thenReturn(mediaPlayer);

            var expected = 3.7D;
            sut.setVolume(expected);

            // Act
            var actual = sut.getVolume();

            // Assert
            Assertions.assertEquals(expected, actual);
        }
    }

    @Test
    void getVolumeReturnsValueFromMediaPlayer() {
        // Arrange
        try (MockedStatic<MediaFactory> factory = Mockito.mockStatic(MediaFactory.class)) {
            factory.when(() -> MediaFactory.createMedia(anyString())).thenReturn(media);
            factory.when(() -> MediaFactory.createMediaPlayer(any(Media.class))).thenReturn(mediaPlayer);

            var expected = 3.7D;

            when(mediaPlayer.getVolume()).thenReturn(expected);
            sut.playBackgroundAudio(URL);

            // Act
            var actual = sut.getVolume();

            // Assert
            Assertions.assertEquals(expected, actual);
        }
    }
}
