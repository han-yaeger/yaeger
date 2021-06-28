package com.github.hanyaeger.api.media;

import com.github.hanyaeger.core.repositories.AudioRepository;
import javafx.scene.media.AudioClip;

/**
 * A {@link SoundClip} encapsulates a mp3 audio file. This file can be played once, or looped for a given amount
 * of times, or indefinite. The path of the mp3 file should be passed to the constructor and the file should be
 * available on the class path.
 */
public class SoundClip {

    /**
     * The default volume is 1.0.
     */
    private double volume = 1D;

    private final String path;
    private final int cycleCount;
    private AudioClip audioClip;
    private final AudioRepository audioRepository;

    /**
     * When instantiating a {@link SoundClip}, the value of the constructor parameter {@code cycleCount} can
     * be used to set the number of times the audio file should be played. When the constant {#link #INDEFINITE}
     * is used, the file be played in ann infinite loop.
     */
    public static final int INDEFINITE = AudioClip.INDEFINITE;

    /**
     * Instantiate a new {@link SoundClip} for the given file, which should be played only once.
     *
     * @param path the path of the mp3 file. Note that this font file should be available on the Class
     *             Path and should be opened through the module descriptor.
     */
    public SoundClip(final String path) {
        this(path, 1);
    }

    /**
     * Instantiate a new {@link SoundClip} for the given file, which should be played for the given amount
     * provided of {@code cycleCount}.
     *
     * @param path       the path of the mp3 file.  Note that this font file should be available on the Class Path
     *                   and should be opened through the module descriptor.
     * @param cycleCount the number of times the audio file should be played. To loop a file indefinitely, use
     *                   a cycleCount of {@link #INDEFINITE}
     */

    public SoundClip(final String path, final int cycleCount) {
        this.path = path;
        this.cycleCount = cycleCount;

        this.audioRepository = AudioRepository.getInstance();
    }

    /**
     * Play the file. It will be played for the given {@code cycleCount}, which is 1 by default. If the file should
     * be looped indefinably, the cycleCount should be set to the constant value {@link #INDEFINITE}.
     */
    public void play() {
        audioClip = audioRepository.get(path);
        audioClip.setCycleCount(cycleCount);
        audioClip.setVolume(volume);
        audioClip.play();
    }

    /**
     * Stop playing the file.
     */
    public void stop() {
        if (audioClip != null) {
            audioClip.stop();
        }
    }

    /**
     * Set the default volume level. The new setting will only take effect on subsequent plays.
     *
     * @param volume new default volume level for this clip
     */
    public void setVolume(final double volume) {
        if (audioClip != null) {
            audioClip.setVolume(volume);
        } else {
            this.volume = volume;
        }
    }

    /**
     * Get the default volume level.
     *
     * @return the default volume level for this clip
     */
    public double getVolume() {
        if (audioClip != null) {
            return audioClip.getVolume();
        } else {
            return 0D;
        }
    }
}
