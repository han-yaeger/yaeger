package nl.han.ica.yaeger.engine.scenes;

import javafx.scene.Scene;
import nl.han.ica.yaeger.engine.Destructable;
import nl.han.ica.yaeger.engine.entities.entity.Entity;

/**
 * Een {@code YaegerScene} moet gebruikt worden om een apart scherm van een Yaeger game te encapsuleren.
 * Een game kan uit meerdere {@code YaegerScene}s bestaan.
 */
public interface YaegerScene extends Destructable {

    /**
     * Initializeer de {@link YaegerScene}. Deze methode wordt aangeroepen voordat het scherm wordt opgebouwd en
     * de {@link YaegerScene} zichtbaar wordt. Gebruik deze methode voor het zetten van een achtergrondplaatje of
     * achtergrondmuziek.
     */
    void initializeScene();

    /**
     * Deze methode wordt aangeroepen om een scherm op te bouwen. Om het geheugengebruik te minimaliseren moet in
     * deze methode de {@link Entity}s worden aangemaakt.
     */
    void setupScene();

    /**
     * Return the {@link Scene} that is encapsulated by this {@link YaegerScene}
     *
     * @return The {@link Scene} that is encapsulated bu this {@link YaegerScene}
     */
    Scene getScene();
}
