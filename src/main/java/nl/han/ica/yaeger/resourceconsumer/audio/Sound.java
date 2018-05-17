package nl.han.ica.yaeger.resourceconsumer.audio;

import javafx.scene.media.AudioClip;
import nl.han.ica.yaeger.resourceconsumer.ResourceConsumer;

/**
 * An Sound encapsulates an actual mp3 audio file that is available on the classpath.
 */
public class Sound extends ResourceConsumer {

    private final String fileName;
    private final int cycleCount;

    /**
     * Create a new Sound for the given file.
     *
     * @param fileName The Fully Qualified Path of the mp3 file that should be used
     */
    public Sound(String fileName) {
        this(fileName, 1);
    }

    /**
     * @param fileName   The Fully Quaified Path of this Sound
     * @param cycleCount The number of times the file should be played
     */

    public Sound(String fileName, int cycleCount) {
        this.fileName = fileName;
        this.cycleCount = cycleCount;
    }

    /**
     * Play the audio file.
     */
    public void play() {
        var url = createPathForResource(fileName);
        var audioClip = new AudioClip(url);
        audioClip.setCycleCount(cycleCount);
        audioClip.play();
    }
}
