package nl.han.ica.yaeger.engine.scenes.delegates;

import com.google.inject.Inject;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import nl.han.ica.yaeger.engine.Destroyable;
import nl.han.ica.yaeger.engine.media.ResourceConsumer;
import nl.han.ica.yaeger.engine.media.audio.SoundClip;
import nl.han.ica.yaeger.engine.media.repositories.AudioRepository;
import nl.han.ica.yaeger.engine.media.repositories.ImageRepository;
import nl.han.ica.yaeger.javafx.factories.ImagePatternFactory;

/**
 * A {@link BackgroundDelegate} follows the Delegate pattern and embraces Composition over Inheritence.
 * It can be used to deal with both background audio and background images for a {@link nl.han.ica.yaeger.engine.scenes.YaegerScene}.
 */
public class BackgroundDelegate implements ResourceConsumer, Destroyable {

    private Scene scene;

    private ImageRepository imageRepository;
    private AudioRepository audioRepository;
    private ImagePatternFactory imagePatternFactory;

    private AudioClip backgroundAudio;

    /**
     * Setup the {@link Scene} belonging to this  {@link BackgroundDelegate}.
     *
     * @param scene The {@link Scene} that should be used when setup.
     */
    public void setup(Scene scene) {
        this.scene = scene;
    }

    /**
     * Set the background audio. The audio will loop indefinite while the {@link Scene} is active.
     *
     * @param backgroundAudioUrl the url of the audio file
     */
    public void setBackgroundAudio(String backgroundAudioUrl) {
        if (backgroundAudioUrl != null) {

            backgroundAudio = audioRepository.get(backgroundAudioUrl, SoundClip.INDEFINITE);
            backgroundAudio.play();
        }
    }

    /**
     * Set the background image. The image will be set as the full background for the
     * {@link Scene}.
     *
     * @param backgroundImageUrl the url of the image file
     */
    public void setBackgroundImage(String backgroundImageUrl) {
        if (backgroundImageUrl != null && scene != null) {
            var image = imageRepository.get(backgroundImageUrl);
            var pattern = imagePatternFactory.create(image);
            scene.setFill(pattern);
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
        scene.setFill(null);
        scene = null;
    }

    @Inject
    public void setImageRepository(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Inject
    public void setAudioRepository(AudioRepository audioRepository) {
        this.audioRepository = audioRepository;
    }

    @Inject
    public void setImagePatternFactory(ImagePatternFactory imagePatternFactory) {
        this.imagePatternFactory = imagePatternFactory;
    }
}
