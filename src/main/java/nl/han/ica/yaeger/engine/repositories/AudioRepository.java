package nl.han.ica.yaeger.engine.repositories;

import nl.han.ica.yaeger.engine.resourceconsumer.audio.SoundClip;

import java.util.*;

/**
 * An {@code AudioRepository} provides a central repository for acquiring audio files.
 */
public class AudioRepository {

    private static final String CYCLECOUNT = "-cyclecount-";

    private Map<String, SoundClip> audioMap;

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
     * Return a {@link SoundClip} for the given [@code audiofile].
     *
     * @param audiofile the filename of the {@link SoundClip}
     * @return the {@link SoundClip} that was requested
     */
    public SoundClip get(String audiofile) {
        if (!audioMap.containsKey(audiofile)) {
            audioMap.put(audiofile, new SoundClip(audiofile));
        }
        return audioMap.get(audiofile);
    }

    /**
     * Return a {@link SoundClip} for the given [@code audiofile] and {@code cycleCount}.
     *
     * @param audiofile  the filename of the {@link SoundClip}
     * @param cycleCount the number of times the {@link SoundClip} should be repeated. To
     *                   set this value to Indefinite, use {@code SoundClip.INDEFINITE}
     * @return the {@link SoundClip} that was requested
     */
    public SoundClip get(String audiofile, int cycleCount) {
        return get(audiofile + CYCLECOUNT + cycleCount);
    }
}
