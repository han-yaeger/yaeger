package com.github.hanyaeger.core.repositories;

import com.github.hanyaeger.core.Destroyable;
import com.github.hanyaeger.core.ResourceConsumer;
import com.google.inject.Singleton;
import javafx.scene.media.AudioClip;

import java.util.*;

/**
 * An {@code AudioRepository} provides a central repository for acquiring audio files.
 */
@Singleton
public class AudioRepository implements ResourceConsumer, Destroyable {

    private static final String CYCLE_COUNT = "-cyclecount-";

    private final Map<String, AudioClip> audioMap = new WeakHashMap<>();

    private static AudioRepository audioRepository;

    /**
     * private constructor to prevent instantiation
     */
    private AudioRepository() {
    }

    /**
     * Return a singleton instance of this {@code AudioRepository}.
     *
     * @return a singleton instance of this {@code AudioRepository}
     */
    public static AudioRepository getInstance() {
        if (audioRepository == null) {
            audioRepository = new AudioRepository();
        }
        return audioRepository;
    }

    /**
     * Return a {@link AudioClip} for the given [@code audiofile].
     *
     * @param audiofile the filename of the {@link AudioClip}
     * @return the {@link AudioClip} that was requested
     */
    public AudioClip get(final String audiofile) {
        return get(audiofile, 0);
    }

    /**
     * Return a {@link AudioClip} for the given [@code audiofile] and {@code cycleCount}.
     *
     * @param audiofile  the filename of the {@link AudioClip}
     * @param cycleCount the number of times the {@link AudioClip} should be repeated. To
     *                   set this value to Indefinite, use {@code SoundClip.INDEFINITE}
     * @return the {@link AudioClip} that was requested
     */
    public AudioClip get(final String audiofile, int cycleCount) {
        var key = audiofile;
        if (cycleCount != 0) {
            key = audiofile + CYCLE_COUNT + cycleCount;
        }
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
        return audioMap.size();
    }

    @Override
    public void destroy() {
        audioMap.clear();
    }
}
