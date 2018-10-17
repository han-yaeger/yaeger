package nl.han.ica.yaeger.engine.resourceconsumer.audio;

import javafx.scene.media.AudioClip;
import nl.han.ica.yaeger.engine.repositories.AudioRepository;

/**
 * A {@code SoundClip} encapsulates a mp3 audio file.
 */
public class SoundClip {

    private final String fileName;
    private final int cycleCount;
    private AudioClip audioClip;
    private AudioRepository audioRepository;

    /**
     * Bij het instantieren van een nieuwe {@code SoundClip} kan de waarde van de parameter {@code cycleCount}
     * op {@code INDEFINITE} worden gezet. In dat geval zal het audiobestand continue worden afgespeeld.
     */
    public static final int INDEFINITE = AudioClip.INDEFINITE;

    /**
     * Creëer een nieuwe {@code SoundClip} voorhet gegeven bestand.
     *
     * @param fileName De bestandsnaam van het mp3-bestand. Het bestand moet beschikbaar staan op het klasse-pad.
     */
    public SoundClip(String fileName) {
        this(fileName, 1);
    }

    /**
     * Creëer een nieuwe {@code SoundClip} voorhet gegeven bestand.
     *
     * @param fileName   De bestandsnaam van het mp3-bestand. Het bestand moet beschikbaar staan op het klasse-pad.
     * @param cycleCount Het aantal keren dat het bestand moet worden afgespeeld.
     */

    public SoundClip(String fileName, int cycleCount) {
        this.fileName = fileName;
        this.cycleCount = cycleCount;

        this.audioRepository = AudioRepository.getInstance();
    }

    /**
     * Play the file.
     */
    public void play() {
        audioClip = audioRepository.get(fileName);
        audioClip.setCycleCount(cycleCount);
        audioClip.play();
    }

    /**
     * Stop playing the file.
     */
    public void stop() {
        audioClip.stop();
    }
}
