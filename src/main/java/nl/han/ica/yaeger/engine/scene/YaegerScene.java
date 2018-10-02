package nl.han.ica.yaeger.engine.scene;

import javafx.scene.Scene;

/**
 * Een {@code YaegerScene} moet gebruikt worden om een apart scherm van een Yaeger game te encapsuleren.
 * Een game kan uit meerdere {@code YaegerScene}s bestaan.
 */
public interface YaegerScene {

    /**
     * Initializeer de {@link YaegerScene}. Deze methode wordt aangeroepen voordat het scherm wordt opgebouwd en
     * de {@link YaegerScene} zichtbaar wordt. Gebruik deze methode voor het zetten van een achtergrondplaatje of
     * achtergrondmuziek.
     */
    void initializeScene();

    /**
     * Deze methode wordt aangeroepen om een scherm op te bouwen. Om het geheugengebruik te minimaliseren moet in
     * deze methode de {@link nl.han.ica.yaeger.engine.entities.Entity}s worden aangemaakt.
     */
    void setupScene();

    /**
     * Handles all behaviour regarding the tear down of a {@link YaegerScene} to maximize the number of
     * Objects that are eligable for Garbage Collection.
     */
    void tearDownScene();

    /**
     * Return the {@link Scene} that is encapsulated by this {@link YaegerScene}
     *
     * @return The {@link Scene} that is encapsulated bu this {@link YaegerScene}
     */
    Scene getScene();
}
