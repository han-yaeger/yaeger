package com.github.hanyaeger.core.factories;

import com.google.inject.Singleton;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * An {@code MediaFactory} should be used for creating instance of {@link javafx.scene.media.Media}
 * and {@link javafx.scene.media.MediaPlayer}.
 */
@Singleton
public class MediaFactory {

    /**
     * Constructs {@link javafx.scene.media.Media} with content loaded from the specified
     * url.
     *
     * @param url the string representing the URL to use as a media file
     * @return an instance of {@link javafx.scene.media.Media}
     * @throws NullPointerException     if URL is null
     * @throws IllegalArgumentException if URL is invalid or unsupported
     */
    public static Media createMedia(String url) {
        return new Media(url);
    }

    /**
     * Construct a new {@link Image} with the specified parameters.
     *
     * @param media the {@link Media} for which a {@link MediaPlayer} should be created
     * @return The requested {@link MediaPlayer}.
     * @throws NullPointerException     if URL is null
     * @throws IllegalArgumentException if URL is invalid or unsupported
     */
    public static MediaPlayer createMediaPlayer(final Media media) {
        return new MediaPlayer(media);
    }
}
