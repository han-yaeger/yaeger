package nl.han.ica.yaeger.engine.exceptions;

/**
 * Een {@code YaegerLifecycleException} wordt gegooit waneer een methode wordt gebruikt waarmee de lifecycle van Yaeger
 * wordt gebroken.
 */
public class YaegerLifecycleException extends RuntimeException {

    /**
     * Maak een nieuwe {@code YaegerLifecycleException} met de gegeven bericht.
     *
     * @param message Het bericht dat getoond moet worden wanneer de exceptie gegooit wordt.
     */
    public YaegerLifecycleException(String message) {
        super(message);
    }
}
