package com.github.hanyaeger.core.scenes.delegates;

import com.github.hanyaeger.core.Destroyable;
import com.github.hanyaeger.core.ResourceConsumer;
import com.github.hanyaeger.core.media.BackgroundAudioMediaPlayer;
import com.github.hanyaeger.core.repositories.ImageRepository;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.core.factories.BackgroundFactory;
import com.google.inject.Inject;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * A {@link BackgroundDelegate} follows the Delegate pattern and embraces Composition over Inheritance.
 * It can be used to deal with both background audio and background images for a {@link YaegerScene}.
 */
public class BackgroundDelegate implements ResourceConsumer, Destroyable {

    private Pane pane;

    private ImageRepository imageRepository;
    private BackgroundFactory backgroundFactory;

    private BackgroundAudioMediaPlayer backgroundAudioMediaPlayer;

    /**
     * Set up the {@link Pane} belonging to this  {@link BackgroundDelegate}.
     *
     * @param pane The {@link Pane} that should be used when setup.
     */
    public void setup(final Pane pane) {
        this.pane = pane;
    }

    /**
     * Set the background audio. The audio will loop indefinite while the {@link Scene} is active.
     *
     * @param backgroundAudioUrl the url of the audio file
     */
    public void setBackgroundAudio(final String backgroundAudioUrl) {
        backgroundAudioMediaPlayer.playBackgroundAudio(backgroundAudioUrl);
    }

    /**
     * Set the background color of the {@link YaegerScene}.
     *
     * @param color The {@link Color} of the background.
     */
    public void setBackgroundColor(final Color color) {
        if (pane != null) {
            pane.setBackground(backgroundFactory.createFillBackground(color));
        }
    }

    /**
     * Set the background image. The image will be set as the full background for the
     * {@link Scene}.
     *
     * @param backgroundImageUrl The url of the image file. This is relative to the resource/
     *                           folder.
     * @param fullscreen         a {@code boolean} that states whether the image should be fullscreen. If {@code false}
     *                           the image will be horizontally and vertically tiled.
     */
    public void setBackgroundImage(final String backgroundImageUrl, final boolean fullscreen) {
        if (backgroundImageUrl != null && pane != null) {
            var image = imageRepository.get(backgroundImageUrl);
            pane.setBackground(backgroundFactory.createImageBackground(image, fullscreen));
        }
    }

    /**
     * Stop playing the background audio.
     */
    public void stopBackgroundAudio() {
        if (backgroundAudioMediaPlayer != null) {
            backgroundAudioMediaPlayer.stopBackgroundAudio();
        }
    }

    /**
     * Set the volume of the background audio.
     *
     * @param volume the volume
     */
    public void setVolume(final double volume) {
        if (backgroundAudioMediaPlayer != null) {
            backgroundAudioMediaPlayer.setVolume(volume);
        }
    }

    /**
     * Retrieves the background audio playback volume.
     *
     * @return the audio volume
     */
    public double getVolume() {
        if (backgroundAudioMediaPlayer != null) {
            return backgroundAudioMediaPlayer.getVolume();
        } else {
            return 0D;
        }
    }

    @Override
    public void destroy() {
        backgroundAudioMediaPlayer.destroy();

        backgroundAudioMediaPlayer = null;

        pane.setBackground(null);
        pane = null;
    }

    /**
     * Set the {@link ImageRepository} to be used for this {@code BackgroundDelegate}.
     *
     * @param imageRepository the {@link ImageRepository} to be used
     */
    @Inject
    public void setImageRepository(final ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Set the {@link BackgroundFactory} to be used for this {@code BackgroundDelegate}.
     *
     * @param backgroundFactory the {@link BackgroundFactory} to be used
     */
    @Inject
    public void setBackgroundFactory(final BackgroundFactory backgroundFactory) {
        this.backgroundFactory = backgroundFactory;
    }

    /**
     * TSet the {@link BackgroundAudioMediaPlayer} to be used for this {@code BackgroundDelegate}.
     *
     * @param backgroundAudioMediaPlayer the {@link BackgroundAudioMediaPlayer} to be used
     */
    @Inject
    public void setBackgroundAudioMediaPlayer(final BackgroundAudioMediaPlayer backgroundAudioMediaPlayer) {
        this.backgroundAudioMediaPlayer = backgroundAudioMediaPlayer;
    }
}
