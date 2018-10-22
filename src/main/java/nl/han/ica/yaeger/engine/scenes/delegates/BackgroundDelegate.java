package nl.han.ica.yaeger.engine.scenes.delegates;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import nl.han.ica.yaeger.engine.media.ResourceConsumer;
import nl.han.ica.yaeger.engine.media.audio.SoundClip;

/**
 * A {@link BackgroundDelegate} follows the Delegate pattern and embraces Composition over Inheritence.
 * It can be used to deal with both background audio and background images for a {@link nl.han.ica.yaeger.engine.scenes.YaegerScene}.
 */
public class BackgroundDelegate implements ResourceConsumer {

    SoundClip backgroundAudio;
    String backgroundAudioUrl;
    String backgroundImageUrl;

    /**
     * Set the name of the background image file.
     *
     * @param file The name of the image file, including extention. Although many different file types are supported,
     *              the following types are preferred:
     *              <ul>
     *              <li>jpg, jpeg</li>
     *              <li>png</li>
     *              </ul>
     */
    public void setBackgroundImageUrl(String file) {
        backgroundImageUrl = file;
    }

    /**
     * Set the name of the background audio file. Currently only *.mp3 files are supported.
     *
     * @param file The name of the audio file, including extention.
     */
    public void setBackgroundAudio(String file) {
        backgroundAudioUrl = file;
    }

    /**
     * Setup the content of this {@link BackgroundDelegate}.
     *
     * @param scene The {@link Scene} that should be used when setup.
     */
    public void setup(Scene scene) {
        setupBackgroundImage(scene);
        setupBackgroundAudio();
    }

    /**
     * Tear down the content of this {@link BackgroundDelegate}.
     */
    public void tearDown(Scene scene) {
        stopBackgroundAudio();
        unsetBackgroundImage(scene);
    }

    private void unsetBackgroundImage(Scene scene) {
        scene.setFill(null);
    }

    private void setupBackgroundAudio() {
        if (backgroundAudioUrl != null) {
            backgroundAudio = new SoundClip(backgroundAudioUrl, SoundClip.INDEFINITE);
            backgroundAudio.play();
        }
    }

    private void setupBackgroundImage(Scene scene) {
        if (backgroundImageUrl != null && scene != null) {
            var stringUrl = createPathForResource(backgroundImageUrl);
            var pattern = new ImagePattern(new Image(stringUrl));
            scene.setFill(pattern);
        }
    }

    private void stopBackgroundAudio() {
        if (backgroundAudio != null) {
            backgroundAudio.stop();
            backgroundAudio = null;
        }
    }
}
