package nl.han.ica.yaeger.resourceconsumer;

import nl.han.ica.yaeger.exceptions.YaegerResourceNotAvailableException;

/**
 * Een {@code ResourceConsumer} bevat een defaut methode die gebruikt kan worden voor het volledige pad naar een
 * {@code Resource} op het klasse-pad.
 */
public interface ResourceConsumer {

    /**
     * Retourneer het volledige pad voor een gegeven {@code Resource}.  De {@code Resource} moet beschikbaar zijn
     * op het klasse-pad. Indien de {@code Resource} niet gevonden wordt zal er een
     * {@link YaegerResourceNotAvailableException} worden gegooit.
     *
     * @param resource De {@code Resource} op het klasse-pad.
     * @return De volledige pad van de {@code Resource} .
     * @throws YaegerResourceNotAvailableException
     */
    default String createPathForResource(String resource) {

        if (resource == null || resource.isEmpty()) {
            return "";
        }

        var url = getClass().getClassLoader().getResource(resource);

        if (url == null) {
            throw new YaegerResourceNotAvailableException(resource);
        }
        return url.toString();
    }
}
