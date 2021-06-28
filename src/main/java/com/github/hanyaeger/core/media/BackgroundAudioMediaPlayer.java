package com.github.hanyaeger.core.media;

import com.github.hanyaeger.core.ResourceConsumer;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 */
public class YaegerMediaPlayer implements ResourceConsumer {

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

            var media = new Media(createPathForResource(backgroundAudioUrl));

            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(volume);
            mediaPlayer.play();
        }
    }

    /**
     * Set the
     * @param volume
     */
    public void setVolume(final double volume) {
        if (mediaPlayer == null) {
            this.volume = volume;
        } else {
            mediaPlayer.setVolume(volume);
        }
    }

    public void stopBackgroundAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }
}
