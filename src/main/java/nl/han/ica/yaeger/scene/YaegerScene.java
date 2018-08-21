package nl.han.ica.yaeger.scene;

/**
 * Een {@code YaegerScene} moet gebruikt worden om een apart scherm van een Yaeger spel te encapsuleren.
 * Een spel kan uit meerdere {@code YaegerScene}s bestaan.
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
}
