package com.github.hanyaeger.api.engine.media.repositories;

import com.github.hanyaeger.api.engine.Destroyable;
import com.github.hanyaeger.api.engine.media.ResourceConsumer;
import com.google.inject.Singleton;
import javafx.scene.media.AudioClip;

import java.util.*;

/**
 * An {@code AudioRepository} provides a central repository for acquiring audio files.
 */
@Singleton
public class AudioRepository implements ResourceConsumer, Destroyable {

    private static final String CYCLECOUNT = "-cyclecount-";

    private final Map<String, AudioClip> audioMap = new WeakHashMap<>();

    private static AudioRepository audioRepository;

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
            key = audiofile + CYCLECOUNT + cycleCount;
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
