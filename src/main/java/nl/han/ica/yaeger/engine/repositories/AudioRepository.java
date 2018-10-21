package nl.han.ica.yaeger.engine.repositories;

import javafx.scene.media.AudioClip;
import nl.han.ica.yaeger.engine.Destructable;
import nl.han.ica.yaeger.engine.resourceconsumer.ResourceConsumer;

import java.util.*;

/**
 * An {@code AudioRepository} provides a central repository for acquiring audio files.
 */
public class AudioRepository implements ResourceConsumer, Destructable {

    private static final String CYCLECOUNT = "-cyclecount-";

    private Map<String, AudioClip> audioMap;

    private static AudioRepository audioRepository;

    public static AudioRepository getInstance() {
        if (audioRepository == null) {
            audioRepository = new AudioRepository();
        }
        return audioRepository;
    }

    private AudioRepository() {
        audioMap = new WeakHashMap<>();
    }

    /**
     * Return a {@link AudioClip} for the given [@code audiofile].
     *
     * @param audiofile the filename of the {@link AudioClip}
     * @return the {@link AudioClip} that was requested
     */
    public AudioClip get(String audiofile) {
        if (!audioMap.containsKey(audiofile)) {
            audioMap.put(audiofile, new AudioClip(createPathForResource(audiofile)));
        }
        return audioMap.get(audiofile);
    }

    /**
     * Return a {@link AudioClip} for the given [@code audiofile] and {@code cycleCount}.
     *
     * @param audiofile  the filename of the {@link AudioClip}
     * @param cycleCount the number of times the {@link AudioClip} should be repeated. To
     *                   set this value to Indefinite, use {@code SoundClip.INDEFINITE}
     * @return the {@link AudioClip} that was requested
     */
    public AudioClip get(String audiofile, int cycleCount) {
        var key = audiofile + CYCLECOUNT + cycleCount;
        if (audioMap.containsKey(key)) {
            return audioMap.get(key);
        } else {
            var audioClip = new AudioClip(createPathForResource(audiofile));
            audioMap.put(key, audioClip);
            return audioClip;
        }
    }

    /**
     * Returns the number of key-value mappings in this map.  If the
     * map contains more than {@code Integer.MAX_VALUE} elements, returns
     * {@code Integer.MAX_VALUE}.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        if (audioMap == null) {
            return 0;
        }
        return audioMap.size();
    }

    @Override
    public void destroy() {
        audioMap.clear();
    }
}
