package com.github.hanyaeger.core.scenes.delegates;

import com.github.hanyaeger.core.Destroyable;
import com.github.hanyaeger.core.ResourceConsumer;
import com.github.hanyaeger.api.media.SoundClip;
import com.github.hanyaeger.core.repositories.AudioRepository;
import com.github.hanyaeger.core.repositories.ImageRepository;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.core.factories.BackgroundFactory;
import com.google.inject.Inject;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

/**
 * A {@link BackgroundDelegate} follows the Delegate pattern and embraces Composition over Inheritance.
 * It can be used to deal with both background audio and background images for a {@link YaegerScene}.
 */
public class BackgroundDelegate implements ResourceConsumer, Destroyable {

    private Pane pane;

    private ImageRepository imageRepository;
    private AudioRepository audioRepository;
    private BackgroundFactory backgroundFactory;

    private AudioClip backgroundAudio;

    /**
     * Setup the {@link Pane} belonging to this  {@link BackgroundDelegate}.
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
        if (backgroundAudioUrl != null) {

            if (backgroundAudio != null) {
                backgroundAudio.stop();
            }

            backgroundAudio = audioRepository.get(backgroundAudioUrl, SoundClip.INDEFINITE);
            backgroundAudio.play();
        }
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
     */
    public void setBackgroundImage(final String backgroundImageUrl) {
        if (backgroundImageUrl != null && pane != null) {
            var image = imageRepository.get(backgroundImageUrl);
            pane.setBackground(backgroundFactory.createImageBackground(image));
        }
    }

    private void stopBackgroundAudio() {
        if (backgroundAudio != null) {
            backgroundAudio.stop();
            backgroundAudio = null;
        }
    }

    @Override
    public void destroy() {
        stopBackgroundAudio();
        pane.setBackground(null);
        pane = null;
    }

    @Inject
    public void setImageRepository(final ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Inject
    public void setAudioRepository(final AudioRepository audioRepository) {
        this.audioRepository = audioRepository;
    }

    @Inject
    public void setBackgroundFactory(final BackgroundFactory backgroundFactory) {
        this.backgroundFactory = backgroundFactory;
    }
}
