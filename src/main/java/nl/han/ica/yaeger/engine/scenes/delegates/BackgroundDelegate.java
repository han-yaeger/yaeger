package nl.han.ica.yaeger.engine.scenes.delegates;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import nl.han.ica.yaeger.engine.Destroyable;
import nl.han.ica.yaeger.engine.media.ResourceConsumer;
import nl.han.ica.yaeger.engine.media.audio.SoundClip;

/**
 * A {@link BackgroundDelegate} follows the Delegate pattern and embraces Composition over Inheritence.
 * It can be used to deal with both background audio and background images for a {@link nl.han.ica.yaeger.engine.scenes.YaegerScene}.
 */
public class BackgroundDelegate implements ResourceConsumer, Destroyable {

    private Scene scene;
    SoundClip backgroundAudio;

    /**
     * Setup the {@link Scene} belonging to this  {@link BackgroundDelegate}.
     *
     * @param scene The {@link Scene} that should be used when setup.
     */
    public void setup(Scene scene) {
        this.scene = scene;
    }

    public void setBackgroundAudio(String backgroundAudioUrl) {
        if (backgroundAudioUrl != null) {

            backgroundAudio = new SoundClip(backgroundAudioUrl, SoundClip.INDEFINITE);
            backgroundAudio.play();
        }
    }

    public void setBackgroundImage(String backgroundImageUrl) {
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

    @Override
    public void destroy() {
        stopBackgroundAudio();
        scene.setFill(null);
        scene = null;
    }
}
