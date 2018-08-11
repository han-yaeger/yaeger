package nl.han.ica.yaeger.exceptions;

/**
 * Een {@code YaegerResourceNotAvailableException} wordt gegooit waneer een {@code Resource} wordt opgevraagd die niet beschikbaar
 * is op het klasse-pad.
 */
public class YaegerResourceNotAvailableException extends RuntimeException {

    /**
     * Maak een nieuwe {@code YaegerResourceNotAvailableException} met de gegeven {@code Resource}.
     *
     * @param resource De {@code Resource}
     */
    public YaegerResourceNotAvailableException(String resource) {

        super("Resource " + resource + " kan niet worden gevonden. Zorg ervoor dat dit bestand beschikbaar is in de resource/ directory.");
    }
}
