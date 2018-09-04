package nl.han.ica.yaeger.scene;

import javafx.scene.Scene;

/**
 * Een {@code YaegerScene} moet gebruikt worden om een apart scherm van een Yaeger game te encapsuleren.
 * Een game kan uit meerdere {@code YaegerScene}s bestaan.
 */
public interface YaegerScene {

    /**
     * Deze methode wordt aangeroepen om een scherm op te bouwen. Om het geheugengebruik te minimaliseren moet in
     * deze methode de {@link nl.han.ica.yaeger.entities.Entity}s worden aangemaakt.
     */
    void setupScene();

    /**
     * Deze methode wordt aangeroepen om een scherm weer af te breken en daarmee geheugen vrij te geven.
     */
    void tearDownScene();

    /**
     * Retourneer de {@link Scene} die geëncapsuleert wordt door deze {@code YaegerScene}
     *
     * @return De {@link Scene} die geëncapsuleert wordt door deze {@code YaegerScene}
     */
    Scene getScene();
}
