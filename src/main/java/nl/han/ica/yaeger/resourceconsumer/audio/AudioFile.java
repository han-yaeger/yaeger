package nl.han.ica.yaeger.resourceconsumer.audio;

/**
 *
 */
public class AudioFile {

    private final String file;
    private final AudioPlayerMode mode;

    /**
     * @param file The Fully Quaified Path of this AudioFile.
     */
    public AudioFile(String file) {
        this(file, AudioPlayerMode.ONCE);
    }

    /**
     * @param file The Fully Quaified Path of this AudioFile.
     * @param mode The AudioPlayerMode that should be used.
     */

    public AudioFile(String file, AudioPlayerMode mode) {
        this.file = file;
        this.mode = mode;
    }

    /**
     * @return The File of this AudioFile.
     */
    public String getFile() {
        return file;
    }

    /**
     * @return The AudioPlayerMode of this AudioFile.
     */
    public AudioPlayerMode getMode() {
        return mode;
    }
}
