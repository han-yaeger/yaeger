package nl.han.ica.yaeger.engine.resourceconsumer.audio;

import javafx.scene.media.AudioClip;
import nl.han.ica.yaeger.engine.resourceconsumer.ResourceConsumer;

/**
 * Een {@code Sound} encapsuleert een mp3 audio-bestand. Een {@code Sound} kan worden afgespeeld, waarbij het
 * mogelijk is om die herhaaldelijk te doen.
 */
public class Sound implements ResourceConsumer {

    private final String fileName;
    private final int cycleCount;
    private AudioClip audioClip;

    /**
     * Bij het instantieren van een nieuwe {@code Sound} kan de waarde van de parameter {@code cycleCount}
     * op {@code INDEFINITE} worden gezet. In dat geval zal het audiobestand continue worden afgespeeld.
     */
    public static final int INDEFINITE = AudioClip.INDEFINITE;

    /**
     * Creëer een nieuwe {@code Sound} voorhet gegeven bestand.
     *
     * @param fileName De bestandsnaam van het mp3-bestand. Het bestand moet beschikbaar staan op het klasse-pad.
     */
    public Sound(String fileName) {
        this(fileName, 1);
    }

    /**
     * Creëer een nieuwe {@code Sound} voorhet gegeven bestand.
     *
     * @param fileName   De bestandsnaam van het mp3-bestand. Het bestand moet beschikbaar staan op het klasse-pad.
     * @param cycleCount Het aantal keren dat het bestand moet worden afgespeeld.
     */

    public Sound(String fileName, int cycleCount) {
        this.fileName = fileName;
        this.cycleCount = cycleCount;
    }

    /**
     * Speel het audiobestand af.
     */
    public void play() {
        var url = createPathForResource(fileName);
        audioClip = new AudioClip(url);
        audioClip.setCycleCount(cycleCount);
        audioClip.play();
    }

    /**
     * Stop het afspelen van het audiobestand.
     */
    public void stop() {
        audioClip.stop();
    }
}
