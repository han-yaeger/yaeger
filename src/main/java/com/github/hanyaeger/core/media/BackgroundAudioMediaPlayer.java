package com.github.hanyaeger.core.media;

import com.github.hanyaeger.core.Destroyable;
import com.github.hanyaeger.core.ResourceConsumer;
import com.github.hanyaeger.core.factories.MediaFactory;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;

/**
 * A MediaPlayer for playing background audio.
 */
public class BackgroundAudioMediaPlayer implements ResourceConsumer, Destroyable {

    private MediaPlayer mediaPlayer;
    private double volume = 1D;

    /**
     * Set the background audio. The audio will loop indefinite while the {@link Scene} is active.
     *
     * @param backgroundAudioUrl the url of the audio file
     */
    public void playBackgroundAudio(final String backgroundAudioUrl) {
        if (backgroundAudioUrl != null) {

            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }

            var media = MediaFactory.createMedia(createPathForResource(backgroundAudioUrl));

            mediaPlayer = MediaFactory.createMediaPlayer(media);
            mediaPlayer.setVolume(volume);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }

    /**
     * Sets the audio playback volume. Its effect will be clamped to the range [0.0, 1.0].
     *
     * @param volume the volume
     */
    public void setVolume(final double volume) {
        if (mediaPlayer == null) {
            this.volume = volume;
        } else {
            mediaPlayer.setVolume(volume);
        }
    }

    /**
     * Retrieves the audio playback volume. The default value is 1.0.
     *
     * @return the audio volume
     */
    public double getVolume() {
        if (mediaPlayer == null) {
            return volume;
        } else {
            return mediaPlayer.getVolume();
        }
    }

    /**
     * Stop playing the background audio.
     */
    public void stopBackgroundAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @Override
    public void destroy() {
        stopBackgroundAudio();
        mediaPlayer = null;
    }
}
